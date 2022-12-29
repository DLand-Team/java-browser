package browser.rx.client;

import browser.rx.TOPIC;
import io.reactivex.rxjava3.subjects.PublishSubject;

public interface InnerMqClient {

    PublishSubject<Object> sub(TOPIC topic, SubscribeCallback callback);

    String getId();

    void pub(TOPIC topic, Object msg);

    void destroy();

}
