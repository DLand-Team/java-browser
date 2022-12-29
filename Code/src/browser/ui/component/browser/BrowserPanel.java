package browser.ui.component.browser;

import browser.core.ChromiumEmbeddedCoreInst;
import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.service.InnerMqService;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BrowserPanel extends JPanel {

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final InnerMqClient mq;
    private String compId;

    private CefClient client;
    private CefBrowser browser;
    private JSplitPane splitPane;
    private JPanel framePanel;
    private JPanel devToolPanel;

    private boolean devToolOpen = false;

    public BrowserPanel(int browserId, String url) {

        this.compId = "BrowserPanel-" + browserId;
        this.mq = this.innerMqService.createConnect(this.compId);

        this.client = ChromiumEmbeddedCoreInst.getInstance().createClient(browserId);
        this.browser = ChromiumEmbeddedCoreInst.getInstance().createBrowser(this.client, url);
        this.browser.setFocus(true);
        this.browser.getUIComponent().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                browser.setFocus(true);
                innerMqService.pub(TOPIC.BROWSER_FOCUS, true);
            }
        });

        this.setLayout(new BorderLayout());
        this.setFocusable(true);

        this.splitPane = new JSplitPane();
        this.splitPane.setBorder(null);
        this.splitPane.setDividerSize(0);
        this.splitPane.setOneTouchExpandable(false);
        this.splitPane.setContinuousLayout(false);
        this.splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.add(this.splitPane, BorderLayout.CENTER);

        // 浏览器界面
        this.framePanel = new JPanel();
        this.framePanel.setLayout(new BorderLayout());
        this.framePanel.add(this.browser.getUIComponent(), BorderLayout.CENTER);
        this.splitPane.setLeftComponent(this.framePanel);

        // 开发者工具
        this.devToolPanel = new JPanel();
        this.devToolPanel.setLayout(new BorderLayout());
        this.splitPane.setRightComponent(null);

        this.subInnerMqMessage();

    }

    private void subInnerMqMessage() {
        this.mq.sub(TOPIC.ADDRESS_FOCUS, (res) -> {
            this.browser.setFocus(false);
        });
        this.mq.sub(TOPIC.OPEN_DEV_TOOL, (res) -> {
            this.toggleDevTools();
        });
        this.mq.sub(TOPIC.RELOAD, (res) -> {
            this.browser.reload();
        });
        this.mq.sub(TOPIC.LOAD_URL, (res) -> {
            this.browser.loadURL((String) res);
        });
    }

    public void dispose() {
        this.innerMqService.destroyClient(this.mq);
    }

    public void toggleDevTools() {
        if (this.devToolOpen) {
            this.closeDevTools();
            this.devToolOpen = false;
        } else {
            this.openDevTools();
            this.devToolOpen = true;
        }
    }

    private void openDevTools() {
        this.devToolPanel.add(this.browser.getDevTools().getUIComponent(), BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> {
            this.splitPane.setDividerSize(5);
            this.splitPane.setRightComponent(devToolPanel);
            this.splitPane.setContinuousLayout(true);
            this.splitPane.setDividerLocation(this.getSize().width - 500);
        });
    }

    private void closeDevTools() {
        this.devToolPanel.removeAll();
        SwingUtilities.invokeLater(() -> {
            this.splitPane.remove(devToolPanel);
            this.splitPane.setDividerSize(0);
            this.splitPane.setRightComponent(null);
            this.splitPane.setContinuousLayout(false);
            this.splitPane.setDividerLocation(this.getSize().width);
        });
    }

}
