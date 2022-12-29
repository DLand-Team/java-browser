package browser.ui.component.tab;

import java.awt.Color;
import java.io.Serial;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class TabCaptions extends JPanel {

	@Serial
    private static final long serialVersionUID = 7549971752593265070L;

	private TabCaption selectedTab;

    private JPanel tabsPane;
    private JPanel buttonsPane;

    public TabCaptions() {
        createUI();
    }

    private void createUI() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(Color.DARK_GRAY);
        add(createItemsPane());
        add(createButtonsPane());
        add(Box.createHorizontalGlue());
    }

    private JComponent createItemsPane() {
        tabsPane = new JPanel();
        tabsPane.setOpaque(false);
        tabsPane.setLayout(new BoxLayout(tabsPane, BoxLayout.X_AXIS));
        return tabsPane;
    }

    private JComponent createButtonsPane() {
        buttonsPane = new JPanel();
        buttonsPane.setOpaque(false);
        buttonsPane.setLayout(new BoxLayout(buttonsPane, BoxLayout.X_AXIS));
        return buttonsPane;
    }

    public void addTab(TabCaption item) {
        tabsPane.add(item);
    }

    public void removeTab(TabCaption item) {
        tabsPane.remove(item);
    }

    public void addTabButton(TabButton button) {
        buttonsPane.add(button);
    }

    public TabCaption getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(TabCaption selectedTab) {
        this.selectedTab = selectedTab;
        this.selectedTab.setSelected(true);
    }
}
