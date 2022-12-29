package browser.ui.component.menu.item;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import browser.ui.MainFrame;
import browser.ui.factory.TabFactory;
import browser.util.CommonUtils;
import browser.util.FontUtils;

public class HistoryMenuItem extends JMenuItem {

    private int height = 25;

    private String time;
    private String innerUrl;

    public HistoryMenuItem(String iconPath, String title, String time) {
        this.time = time;
        this.setLayout(new BorderLayout());
        this.addPageIcon(iconPath);
        this.addPageTitle(title);
        this.addCloseButton();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TabFactory.insertTabAsync(MainFrame.getInstance().tabbedPane, getInnerUrl());
            }
        });
    }

    /* set */
    public void setPageText(String text) {
        JLabel label = (JLabel) this.getComponent(1);
        label.setText(text);
    }

    public void setPageIcon(String iconPath) {
        JLabel label = (JLabel) this.getComponent(0);
        File img = new File(iconPath);
        ImageIcon iconImage;
        if (img.exists() && img.isFile()) {
            iconImage = new ImageIcon(img.getAbsolutePath());
            iconImage.setImage(iconImage.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        } else {
            iconImage = (ImageIcon) CommonUtils.getIcon("/browser/line/file.png");
        }
        label.setIcon(iconImage);
    }
    /* -END- set */

    /* add */
    private void addPageIcon(String iconPath) {
        JLabel iconLabel = new JLabel();
        iconLabel.setPreferredSize(new Dimension(height + 5, height));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        File img = new File(iconPath);
        ImageIcon iconImage;
        if (img.exists() && img.isFile()) {
            iconImage = new ImageIcon(img.getAbsolutePath());
            iconImage.setImage(iconImage.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        } else {
            iconImage = (ImageIcon) CommonUtils.getIcon("/img/browser/line/file.png");
        }
        iconLabel.setIcon(iconImage);
        this.add(iconLabel, BorderLayout.WEST);
    }

    private void addPageTitle(String title) {
        JLabel textLabel = new JLabel(title);
        textLabel.setPreferredSize(new Dimension((int) textLabel.getPreferredSize().getWidth(), height));
        textLabel.setFont(FontUtils.MicrosoftYahei(12));
        textLabel.setMaximumSize(new Dimension(500 - height * 2 - 10, 0));
        this.add(textLabel, BorderLayout.CENTER);
    }

    private void addCloseButton() {
        JLabel closeLabel = new JLabel();
        closeLabel.setPreferredSize(new Dimension(height + 10, height));
        closeLabel.setHorizontalAlignment(JLabel.CENTER);
        closeLabel.setIcon(CommonUtils.getIcon("/browser/line/close_1.png"));
        closeLabel.setToolTipText("删除该记录");
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    MainFrame.getInstance().historyMenu.removeHistoryItem(time);
                }
            }
        });
        this.add(closeLabel, BorderLayout.EAST);
    }
    /* -END- add */

    public void itemResize(JMenuItem item, int maxWidth) {
        Component[] comp = item.getComponents();
        double width = 0;
        for (int i = 0; i < comp.length; i++) {
            width = width + comp[i].getPreferredSize().getWidth();
        }
        item.setPreferredSize(new Dimension((int) (width > maxWidth ? maxWidth : width), height));
    }

    public String getTime() {
        return time;
    }

    public String getInnerUrl() {
        return innerUrl;
    }

    public void setInnerUrl(String innerUrl) {
        this.innerUrl = innerUrl;
    }

}
