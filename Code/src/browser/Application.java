package browser;

import browser.rx.service.InnerMqService;
import browser.ui.MainFrame;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        // 设置主题
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                 | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // 界面相关配置
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        // 注册rxjava
        InnerMqService.getInstance();
        // 启动界面
        SwingUtilities.invokeLater(() -> {
            MainFrame.getInstance();
        });
    }

}
