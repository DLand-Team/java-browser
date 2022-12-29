package browser.ui.factory;

import browser.ui.component.tab.Tab;
import browser.ui.component.tab.TabCaption;
import browser.ui.component.tab.TabContent;
import browser.ui.component.tab.TabbedPane;

import javax.swing.SwingUtilities;
import java.util.Random;

public final class TabFactory {

    /* 异步添加Tab */
    public static void insertTabAsync(TabbedPane tabbedPane, String url) {
        SwingUtilities.invokeLater(() -> {
            insertTab(tabbedPane, url);
        });
    }

    /* 同步添加Tab */
    public static void insertTabSync(TabbedPane tabbedPane, String url) {
        insertTab(tabbedPane, url);
    }

    /* insertTab */
    private static void insertTab(TabbedPane tabbedPane, String url) {
        Tab tab = createTab(tabbedPane, url);
        tabbedPane.addTab(tab);
        tabbedPane.selectTab(tab);
    }

    /* createTab */
    private static Tab createTab(TabbedPane tabbedPane, String url) {
        int browserId = new Random().nextInt(Integer.MAX_VALUE);
        // Tab标头
        TabCaption tabCaption = new TabCaption(browserId, url);
        // Tab容器
        TabContent tabContent = new TabContent(browserId, url);
        return new Tab(tabCaption, tabContent);
    }

}
