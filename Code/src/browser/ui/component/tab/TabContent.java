package browser.ui.component.tab;

import browser.ui.component.browser.BrowserPanel;
import browser.ui.component.bar.ToolBar;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class TabContent extends JPanel {

    private String compId;
    private ToolBar toolBar;
    private BrowserPanel browserPanel;

    public TabContent(int browserId, String url) {

        this.compId = "TabContent-" + browserId;

        toolBar = new ToolBar(browserId, url);
        browserPanel = new BrowserPanel(browserId, url);

        this.setLayout(new BorderLayout());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(browserPanel, BorderLayout.CENTER);

    }

    /* 设置加载图标 */
    private void setLoadingIcon() {
        firePropertyChange("PageIconChanged", null, "loading");
    }

    /* 设置图标和标题 */
    private void setIconAndTitle() {
    }

    public void dispose() {
        this.toolBar.dispose();
        this.browserPanel.dispose();
    }

}
