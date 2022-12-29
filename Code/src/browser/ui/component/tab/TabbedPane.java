package browser.ui.component.tab;

import browser.common.Cache;
import browser.rx.TOPIC;
import browser.rx.service.InnerMqService;
import browser.util.CommonUtils;

import java.awt.BorderLayout;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TabbedPane extends JPanel {

    private final InnerMqService innerMqService = InnerMqService.getInstance();

    private final List<Tab> tabs;
    private final TabCaptions captions;
    private final JComponent contentContainer;

    public TabbedPane() {

        this.tabs = new ArrayList<Tab>();
        this.setLayout(new BorderLayout());

        this.captions = new TabCaptions();
        this.add(captions, BorderLayout.NORTH);

        this.contentContainer = new JPanel(new BorderLayout());
        this.add(contentContainer, BorderLayout.CENTER);

        TabButton button = new TabButton(CommonUtils.getIcon("/browser/line/new_tab.png"), "新建标签页");
        button.addActionListener((e) -> this.innerMqService.pub(TOPIC.OPEN_NEW_PAGE, Cache.setting.getMainPageAddress()));
        this.addTabButton(button);

    }

    public void disposeAllTabs() {
        getTabs().forEach(this::disposeTab);
    }

    private void disposeTab(Tab tab) {
        tab.getCaption().setSelected(false);
        tab.getCaption().dispose();
        tab.getContent().dispose();
        removeTab(tab);
        if (hasTabs()) {
            getLastTab().getCaption().setSelected(true);
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.setVisible(false);
                window.dispose();
            }
        }
    }

    private Tab findTab(TabCaption item) {
        for (Tab tab : getTabs()) {
            if (tab.getCaption().equals(item)) {
                return tab;
            }
        }
        return null;
    }

    public void addTab(final Tab tab) {

        TabCaption caption = tab.getCaption();
        caption.addPropertyChangeListener("CloseButtonPressed", new TabCaptionCloseTabListener());
        caption.addPropertyChangeListener("TabSelected", new SelectTabListener());

        TabContent content = tab.getContent();
        content.addPropertyChangeListener("TabClosed", new TabContentCloseTabListener());

        captions.addTab(caption);
        tabs.add(tab);
        validate();
        repaint();

    }

    private boolean hasTabs() {
        return !tabs.isEmpty();
    }

    private Tab getLastTab() {
        return tabs.get(tabs.size() - 1);
    }

    private List<Tab> getTabs() {
        return new ArrayList<Tab>(tabs);
    }

    public void removeTab(Tab tab) {
        TabCaption tabCaption = tab.getCaption();
        captions.removeTab(tabCaption);
        tabs.remove(tab);
        validate();
        repaint();
    }

    public void addTabButton(TabButton button) {
        captions.addTabButton(button);
    }

    public void selectTab(Tab tab) {
        TabCaption tabCaption = tab.getCaption();
        TabCaption selectedTab = captions.getSelectedTab();
        if (selectedTab != null && !selectedTab.equals(tabCaption)) {
            selectedTab.setSelected(false);
        }
        captions.setSelectedTab(tabCaption);
    }

    private class TabCaptionCloseTabListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            TabCaption caption = (TabCaption) evt.getSource();
            Tab tab = findTab(caption);
            disposeTab(tab);
        }
    }

    private class SelectTabListener implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            TabCaption caption = (TabCaption) evt.getSource();
            Tab tab = findTab(caption);
            if (caption.isSelected()) {
                selectTab(tab);
            }
            if (!caption.isSelected()) {
                TabContent content = tab.getContent();
                contentContainer.remove(content);
                contentContainer.validate();
                contentContainer.repaint();
            } else {
                final TabContent content = tab.getContent();
                contentContainer.add(content, BorderLayout.CENTER);
                contentContainer.validate();
                contentContainer.repaint();
            }
        }
    }

    private class TabContentCloseTabListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            TabContent content = (TabContent) evt.getSource();
            Tab tab = findTab(content);
            disposeTab(tab);
        }

        private Tab findTab(TabContent content) {
            for (Tab tab : getTabs()) {
                if (tab.getContent().equals(content)) {
                    return tab;
                }
            }
            return null;
        }

    }
}
