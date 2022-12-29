package browser.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DomUtils {

    public static String getTitle(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.getElementsByTag("title");
        for (Element element : elements) {
            return element.text();
        }
        return null;
    }

}
