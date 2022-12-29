package browser.ui.component.bar;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.service.InnerMqService;
import browser.util.FontUtils;

public class ToolBar extends JPanel {

    private static final long serialVersionUID = -8081016013938632494L;
    private final String BASE_IMG_PATH = "/browser/assets/img/browser/colorful/";

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final InnerMqClient client;
    private String compId;

    private JTextField addressTextField;

    public ToolBar(int browserId, String url) {

        this.compId = "ToolBar-" + browserId;
        this.client = innerMqService.createConnect(this.compId);

        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(169, 169, 169)));

        JButton backButton = new ToolBarButton(BASE_IMG_PATH + "left.png", "返回");
        JButton forwardButton = new ToolBarButton(BASE_IMG_PATH + "right.png", "前进");
        JButton refreshButton = new ToolBarButton(BASE_IMG_PATH + "refresh.png", "刷新");
        JButton homeButton = new ToolBarButton(BASE_IMG_PATH + "home.png", "主页");
        JButton bookmarkButton = new ToolBarButton(BASE_IMG_PATH + "bookmark.png", "书签");
        JButton goButton = new ToolBarButton(BASE_IMG_PATH + "href.png", "转到");
        JButton reOpenButton = new ToolBarButton(BASE_IMG_PATH + "history.png", "历史记录");
        JButton toolButton = new ToolBarButton(BASE_IMG_PATH + "tool.png", "工具");
        JButton settingButton = new ToolBarButton(BASE_IMG_PATH + "setting.png", "设置");

        this.addressTextField = new JTextField(url);
        this.addressTextField.setFocusable(false);
        this.addressTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addressTextField.setFocusable(true);
                addressTextField.requestFocus();
                innerMqService.pub(TOPIC.ADDRESS_FOCUS, true);
            }
        });

        backButton.setEnabled(false);
        forwardButton.setEnabled(false);

        // 后退
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (backButton.isEnabled() && e.getButton() == 1) {
                }
            }
        });
        // 前进
        forwardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (forwardButton.isEnabled() && e.getButton() == 1) {
                }
            }
        });
        // 刷新
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    innerMqService.pub("*" + browserId, TOPIC.RELOAD, true);
                }
            }
        });
        // 主页
        homeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                }
            }
        });
        // 书签
        // 地址
        addressTextField.setFont(FontUtils.MicrosoftYahei(15));
        addressTextField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        addressTextField.setColumns(10);
        addressTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10 && !"".equals(addressTextField.getText())) {
                    innerMqService.pub("*" + browserId, TOPIC.LOAD_URL, addressTextField.getText());
                }
            }
        });
        // 转到
        goButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1 && !"".equals(addressTextField.getText())) {
                    innerMqService.pub("*" + browserId, TOPIC.LOAD_URL, addressTextField.getText());
                }
            }
        });
        // 历史记录
        reOpenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                }
            }
        });
        // 工具
        toolButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                }
            }
        });
        // 设置
        settingButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                }
            }
        });

        GroupLayout gl_panel_1 = new GroupLayout(this);
        gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_1
                .createSequentialGroup()
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(forwardButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(homeButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(bookmarkButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(10)
                .addComponent(addressTextField, GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE).addGap(10)
                .addComponent(goButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(reOpenButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(toolButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)
                .addComponent(settingButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(0)));
        gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
                gl_panel_1.createSequentialGroup().addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                .addComponent(addressTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35,
                                        Short.MAX_VALUE)
                                .addGroup(Alignment.LEADING,
                                        gl_panel_1.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(backButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(forwardButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(refreshButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(homeButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(bookmarkButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(goButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(reOpenButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(toolButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                                .addComponent(settingButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
                        .addGap(0)));
        this.setLayout(gl_panel_1);

        this.subInnerMqMessage();

    }

    private void subInnerMqMessage() {
        this.client.sub(TOPIC.BROWSER_FOCUS, (res) -> {
            this.addressTextField.setFocusable(false);
        });
        this.client.sub(TOPIC.CHANGE_URL, (res) -> {
            this.addressTextField.setText((String) res);
        });
    }

    public void dispose() {
        innerMqService.destroyClient(this.client);
    }

    static class ToolBarButton extends JButton {

        public ToolBarButton(String iconPath, String tipText) {
            setIcon(new ImageIcon(Objects.requireNonNull(ToolBar.class.getResource(iconPath))));
            setToolTipText(tipText);
            setFocusable(false);
        }

    }

}