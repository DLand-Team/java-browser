package browser.core.handler;

import browser.common.Script;
import browser.rx.TOPIC;
import browser.rx.service.InnerMqService;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandler;
import org.cef.handler.CefLoadHandlerAdapter;
import org.cef.network.CefRequest;

public class LoadHandler extends CefLoadHandlerAdapter {

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final int browserId;

    public LoadHandler(int browserId) {
        this.browserId = browserId;
    }

    @Override
    public void onLoadStart(CefBrowser browser, CefFrame frame, CefRequest.TransitionType transitionType) {
        String url = browser.getURL();
        if (url.indexOf("devtools://") == 0) {
            return;
        }
        this.innerMqService.pub("*" + this.browserId, TOPIC.CHANGE_ICON, "loading");
    }

    @Override
    public void onLoadEnd(CefBrowser browser, CefFrame frame, int httpStatusCode) {
        this.change(browser);
    }

    @Override
    public void onLoadError(CefBrowser browser, CefFrame frame, CefLoadHandler.ErrorCode errorCode, String errorText, String failedUrl) {
        this.change(browser);
    }

    private void change(CefBrowser browser) {
        String url = browser.getURL();
        if (url.indexOf("devtools://") == 0) {
            return;
        }
        this.innerMqService.pub("*" + this.browserId, TOPIC.CHANGE_URL, url);
        browser.executeJavaScript(Script.getTitle(this.browserId), null, 0);
        browser.executeJavaScript(Script.getIconHref(this.browserId), null, 0);
    }

}
