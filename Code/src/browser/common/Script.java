package browser.common;

public class Script {

    public static String getIconHref(int browserId) {
        return "" +
                "{" +
                "let sendData = 'get_favicon_href__';" +
                "let links = document.getElementsByTagName('link');" +
                "if (links != null) {" +
                "for (let i = 0; i < links.length; i++) {" +
                "let has = false;" +
                "let names = links[0].getAttributeNames();" +
                "for (let j = 0; j < names.length; j++) {" +
                "if (links[0].getAttribute(names[j]) == 'icon' || links[0].getAttribute(names[j]) == 'shortcut icon' ) {" +
                "sendData += links[0].getAttribute('href');" +
                "has = true;" +
                "break;" +
                "}" +
                "}" +
                "if (has) { break; }" +
                "}" +
                "}" +
                "window.cef_java_" + browserId + "({" +
                "request: sendData," +
                "persistent:false" +
                "});" +
                "}" +
                "";
    }

    public static String getTitle(int browserId) {
        return "" +
                "{" +
                "let sendData = 'get_title__';" +
                "let titles = document.getElementsByTagName('title');" +
                "if (titles != null) {" +
                "sendData += titles[0].innerText;" +
                "}" +
                "window.cef_java_" + browserId + "({" +
                "request: sendData," +
                "persistent:false" +
                "});" +
                "}" +
                "";
    }

}
