package browser.core;

import browser.core.handler.*;
import com.jetbrains.cef.JCefAppConfig;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefRendering;

public class ChromiumEmbeddedCoreInst {

    private static volatile ChromiumEmbeddedCoreInst instance;

    private CefApp cefApp = null;

    public ChromiumEmbeddedCoreInst() {
        String[] args = JCefAppConfig.getInstance().getAppArgs();
        CefSettings settings = JCefAppConfig.getInstance().getCefSettings();
        settings.cache_path = System.getProperty("user.dir") + "/context/jcef/data";
        // 获取CefApp实例
        CefApp.startup(args);
        this.cefApp = CefApp.getInstance(args, settings);
    }

    public CefClient createClient(int browserId) {
        CefClient cefClient = this.cefApp.createClient();
        // 基础监听事件
        cefClient.addLifeSpanHandler(new LifeSpanHandler(browserId));
        cefClient.addContextMenuHandler(new MenuHandler(browserId));
        cefClient.addLoadHandler(new LoadHandler(browserId));
        // 配置一个查询路由,html页面可使用 window.java({}) 和 window.javaCancel({}) 来调用此方法
        CefMessageRouter.CefMessageRouterConfig cmrConfig = new CefMessageRouter.CefMessageRouterConfig("cef_java_" + browserId, "cef_java_" + browserId + "_cancel");
        // 创建查询路由
        CefMessageRouter cmr = CefMessageRouter.create(cmrConfig);
        cmr.addHandler(new MessageRouterHandler(browserId), true);
        cefClient.addMessageRouter(cmr);
        return cefClient;
    }

    public CefBrowser createBrowser(CefClient client, String url) {
        return client.createBrowser(url, CefRendering.DEFAULT, true);
    }

    public String getVersion() {
        return "Chromium Embedded Framework (CEF), " + "ChromeVersion: " + cefApp.getVersion().getChromeVersion();
    }

    public void dispose() {
        cefApp.dispose();
    }

    public static ChromiumEmbeddedCoreInst getInstance() {
        if (instance == null) {
            synchronized (ChromiumEmbeddedCoreInst.class) {
                if (instance == null) {
                    instance = new ChromiumEmbeddedCoreInst();
                }
            }
        }
        return instance;
    }

}
