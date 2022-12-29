package browser.core.handler;

import browser.rx.TOPIC;
import browser.rx.service.InnerMqService;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLifeSpanHandlerAdapter;

public class LifeSpanHandler extends CefLifeSpanHandlerAdapter {

    private final InnerMqService innerMqService = InnerMqService.getInstance();

    public LifeSpanHandler(int browserId) {

    }

    @Override
    public boolean onBeforePopup(CefBrowser browser, CefFrame frame, String targetUrl, String targetFrameName) {
        // 打开新窗口
        innerMqService.pub(TOPIC.OPEN_NEW_PAGE, targetUrl);
        // 返回true表示取消弹出窗口
        return true;
    }

}
