package browser.ui;

import browser.assets.entity.History;
import browser.common.Cache;
import browser.common.Static;
import browser.rx.TOPIC;
import browser.rx.client.InnerMqClient;
import browser.rx.service.InnerMqService;
import browser.ui.component.menu.HistoryMenu;
import browser.ui.component.menu.SettingMenu;
import browser.ui.component.menu.ToolMenu;
import browser.ui.component.tab.TabbedPane;
import browser.ui.factory.TabFactory;
import browser.util.CommonUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;

public class MainFrame extends JFrame {

    private static volatile MainFrame instance;

    private final InnerMqService innerMqService = InnerMqService.getInstance();
    private final InnerMqClient client = this.innerMqService.createConnect("MainFrame");
    public TabbedPane tabbedPane = new TabbedPane();
    public HistoryMenu historyMenu = new HistoryMenu() {
        {
            if (Cache.historyMap.size() > 0) {
                ArrayList<History> lase15History = new ArrayList<>();
                ListIterator<Map.Entry<String, History>> iter = new ArrayList<Map.Entry<String, History>>(
                        Cache.historyMap.entrySet()).listIterator(Cache.historyMap.size());
                int count = 1;
                while (iter.hasPrevious()) {
                    Map.Entry<String, History> entry = iter.previous();
                    lase15History.add(entry.getValue());
                    count = count + 1;
                    if (count >= 15) {
                        break;
                    }
                }
                for (int i = lase15History.size() - 1; i >= 0; i--) {
                    this.addHistoryItem(lase15History.get(i));
                }
            }
        }
    };
    public ToolMenu toolMenu = new ToolMenu();
    public SettingMenu settingMenu = new SettingMenu();

    public MainFrame() {

        this.setIconImage(
                Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/browser/assets/img/icon/chrome_1.png")));
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        this.getContentPane().add(this.tabbedPane, BorderLayout.CENTER);

        /* 默认首页 */
        TabFactory.insertTabSync(this.tabbedPane, Cache.setting.getMainPageAddress());
        /* -END- 默认首页 */

        /* rxjava监听 */
        this.subInnerMqMessage();

        this.setTitle("Moderate浏览器 - " + Static.VERSION);
        if (Cache.setting.getIsMaxWindow()) {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setSize(new Dimension(Static.DEFAULT_WIDTH, Static.DEFAULT_HEIGHT));
        } else {
            this.setSize(new Dimension(Cache.setting.getDefaultWidth(), Cache.setting.getDefaultHeight()));
        }
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight()) / 2);
        this.setVisible(true);
        this.setResizable(true);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Cache.setting.setDefaultWidth(e.getComponent().getWidth());
                Cache.setting.setDefaultHeight(e.getComponent().getHeight());
                CommonUtils.saveSetting();
            }
        });
        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent state) {
                if (state.getNewState() == 1 || state.getNewState() == 7) {
                    Cache.setting.setIsMaxWindow(true);
                } else if (state.getNewState() == 0) {
                    Cache.setting.setIsMaxWindow(false);
                } else if (state.getNewState() == 6) {
                    Cache.setting.setIsMaxWindow(true);
                }
                CommonUtils.saveSetting();
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                MainFrame.getInstance().setVisible(false);
                innerMqService.destroyClient(client);
                tabbedPane.disposeAllTabs();
                System.exit(0);
            }
        });

    }

    private void subInnerMqMessage() {
        this.client.sub(TOPIC.OPEN_NEW_PAGE, (res) -> {
            TabFactory.insertTabAsync(this.tabbedPane, (String) res);
        });
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            synchronized (MainFrame.class) {
                if (instance == null) {
                    instance = new MainFrame();
                }
            }
        }
        return instance;
    }

}
