package browser.core.handler;

import browser.rx.TOPIC;
import browser.rx.service.InnerMqService;
import browser.util.IconFinder;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

import javax.swing.*;

public class MessageRouterHandler extends CefMessageRouterHandlerAdapter {

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final int browserId;

    public MessageRouterHandler(int browserId) {
        this.browserId = browserId;
    }

    public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
        if (request.indexOf("get_title__") == 0) {
            String title = request.replaceAll("get_title__", "");
            this.innerMqService.pub("*" + this.browserId, TOPIC.CHANGE_TITLE, title);
        } else if (request.indexOf("get_favicon_href__") == 0) {
            String iconHref = request.replaceAll("get_favicon_href__", "");
            SwingUtilities.invokeLater(() -> {
                String faviconPath = IconFinder.saveAndGetIcon(browser.getURL(), iconHref);
                this.innerMqService.pub("*" + this.browserId, TOPIC.CHANGE_ICON, faviconPath);
            });
        }
        return true;
    }

    public void onQueryCanceled(CefBrowser browser, CefFrame frame, long queryId) {
    }

}
