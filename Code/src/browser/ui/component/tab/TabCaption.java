package browser.ui.component.tab;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.service.InnerMqService;
import browser.util.CommonUtils;
import browser.util.FontUtils;

public class TabCaption extends JPanel {

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final InnerMqClient client;
    private String compId;
    private boolean selected;
    private TabCaptionComponent component;

    public TabCaption(int browserId, String url) {

        this.compId = "TabCaption-" + browserId;
        this.client = innerMqService.createConnect(this.compId);

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        this.add(Box.createHorizontalStrut(1), BorderLayout.EAST);

        this.component = new TabCaptionComponent();
        this.component.addPropertyChangeListener("CloseButtonPressed", evt -> {
            firePropertyChange("CloseButtonPressed", evt.getOldValue(), evt.getNewValue());
        });
        this.component.addPropertyChangeListener("TabClicked", evt -> {
            this.setSelected(true);
        });
        this.add(component, BorderLayout.CENTER);
        this.component.setTitle(url);

        this.subInnerMqMessage();

    }

    private void subInnerMqMessage() {
        this.client.sub(TOPIC.CHANGE_ICON, (res) -> {
            this.setIcon((String) res);
        });
        this.client.sub(TOPIC.CHANGE_TITLE, (res) -> {
            this.setTitle((String) res);
        });
    }

    public void setIcon(String iconPath) {
        this.component.setIcon(iconPath);
    }

    public void setTitle(String title) {
        this.component.setTitle(title);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        boolean oldValue = this.selected;
        this.selected = selected;
        this.component.setSelected(selected);
        firePropertyChange("TabSelected", oldValue, selected);
    }

    public void dispose() {
        innerMqService.destroyClient(this.client);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(170, 30);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 30);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    private static class TabCaptionComponent extends JPanel {

        private final Color defaultBackground;
        private JLabel iconLabel;
        private JLabel textLabel;

        private TabCaptionComponent() {
            defaultBackground = getBackground();
            setLayout(new BorderLayout());
            setOpaque(false);
            add(createIcon(), BorderLayout.WEST);
            add(createLabel(), BorderLayout.CENTER);
            add(createCloseButton(), BorderLayout.EAST);
        }

        /* 图标 */
        private JComponent createIcon() {
            iconLabel = new JLabel();
            iconLabel.setPreferredSize(new Dimension(30, 30));
            iconLabel.setHorizontalAlignment(JLabel.CENTER);
            iconLabel.setOpaque(false);
            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        firePropertyChange("TabClicked", false, true);
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        firePropertyChange("CloseButtonPressed", false, true);
                    }
                }
            });
            return iconLabel;
        }

        /* 标签文字 */
        private JComponent createLabel() {
            textLabel = new JLabel();
            textLabel.setOpaque(false);
            textLabel.setFont(FontUtils.MicrosoftYahei(13));
            textLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
            textLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        firePropertyChange("TabClicked", false, true);
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        firePropertyChange("CloseButtonPressed", false, true);
                    }
                }
            });
            return textLabel;
        }

        /* 关闭按钮 */
        private JComponent createCloseButton() {
            JButton closeButton = new JButton();
            closeButton.setOpaque(false);
            closeButton.setToolTipText("Close");
            closeButton.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            closeButton.setPressedIcon(CommonUtils.getIcon("/browser/line/close_pressed.png"));
            closeButton.setIcon(CommonUtils.getIcon("/browser/line/close.png"));
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusable(false);
            closeButton.addActionListener(e -> firePropertyChange("CloseButtonPressed", false, true));
            return closeButton;
        }

        /* 设置图标 */
        public void setIcon(String iconPath) {
            SwingUtilities.invokeLater(() -> {
                ImageIcon image = null;
                switch (iconPath) {
                    case "" -> {
                        image = (ImageIcon) CommonUtils.getIcon("/browser/line/file.png");
                    }
                    case "loading" -> {
                        image = (ImageIcon) CommonUtils.getIcon("/browser/line/loading_1.gif");
                    }
                    default -> {
                        image = new ImageIcon(iconPath);
                        image.setImage(image.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
                    }
                }
                iconLabel.setIcon(image);
            });
        }

        /* 设置标签文字 */
        public void setTitle(String title) {
            if (title == null) {
                return;
            }
            SwingUtilities.invokeLater(() -> {
                textLabel.setText(title);
                textLabel.setToolTipText(title);
            });
        }

        public void setSelected(boolean selected) {
            setBackground(selected ? defaultBackground : new Color(150, 150, 150));
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setPaint(new GradientPaint(0, 0, Color.LIGHT_GRAY, 0, getHeight(), getBackground()));
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g2d.dispose();
            super.paint(g);
        }
    }
}
