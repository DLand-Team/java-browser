package browser.rx.service;

import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.client.impl.InnerMqClientImpl;

import java.util.HashMap;
import java.util.Map;

public class InnerMqService {

    private static volatile InnerMqService instance;

    public Map<String, InnerMqClient> clients = new HashMap<>(); // 客户端

    /* 建立连接 */
    public InnerMqClient createConnect(String id) {
        return clients.computeIfAbsent(id, k -> new InnerMqClientImpl(id));
    }

    /* 取消连接 */
    public void destroyClient(String id) {
        InnerMqClient client = this.clients.get(id);
        if (client != null) {
            client.destroy();
        }
        this.clients.remove(id);
    }

    /* 取消连接 */
    public void destroyClient(InnerMqClient client) {
        client.destroy();
        this.clients.remove(client.getId());
    }

    public void pub(TOPIC topic, Object msg) {
        for (InnerMqClient client : this.clients.values()) {
            client.pub(topic, msg);
        }
    }

    public void pub(String id, TOPIC topic, Object msg) {
        if (id.indexOf("*") == 0) {
            String innerId = id.replace("*", "");
            for (Map.Entry<String, InnerMqClient> entry : this.clients.entrySet()) {
                if (entry.getKey().contains(innerId)) {
                    entry.getValue().pub(topic, msg);
                }
            }
        } else {
            for (Map.Entry<String, InnerMqClient> entry : this.clients.entrySet()) {
                if (entry.getKey().equals(id)) {
                    entry.getValue().pub(topic, msg);
                    break;
                }
            }
        }
    }

    public static InnerMqService getInstance() {
        if (instance == null) {
            synchronized (InnerMqService.class) {
                if (instance == null) {
                    instance = new InnerMqService();
                }
            }
        }
        return instance;
    }

}
