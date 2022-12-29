package browser.ui.component.menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import browser.assets.entity.History;
import browser.common.Static;
import browser.ui.MainFrame;
import browser.ui.component.menu.item.HistoryMenuItem;
import browser.ui.factory.HistoryFactory;
import browser.ui.factory.TabFactory;
import browser.util.CommonUtils;

public class HistoryMenu extends JPopupMenu {

    private JPopupMenu menu = this;
    private LinkedHashMap<String, HistoryMenuItem> itemMap = new LinkedHashMap<String, HistoryMenuItem>();

    public HistoryMenu() {
        JMenuItem historyItem = new JMenuItem("所有历史记录...");
        historyItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    File historyFile = new File(Static.HISTORY_JSON_PATH);
                    String js = "let historyJsonText = {}";
                    if (historyFile.exists() && historyFile.isFile()) {
                        String json = CommonUtils.readFile(historyFile.getAbsolutePath());
                        js = "let historyJsonText = '" + json + "'";
                    }
                    CommonUtils.saveStringToFile(js,
                            System.getProperty("user.dir") + "\\resource\\history\\js\\data\\HistoryJsonText.js");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                TabFactory.insertTabAsync(MainFrame.getInstance().tabbedPane,
                        "file:///" + System.getProperty("user.dir") + "\\resource\\history\\index.html");
            }
        });
        JMenuItem historyCleanItem = new JMenuItem("清空历史记录");
        historyCleanItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == 1) {
                    int n = JOptionPane.showConfirmDialog(MainFrame.getInstance(), "是否删除所有历史记录？");
                    if (n == JOptionPane.YES_OPTION) {
                        HistoryFactory.removeAllHistory();
                        itemMap.keySet().forEach((key) -> menu.remove(itemMap.get(key)));
                        itemMap.clear();
                        JOptionPane.showMessageDialog(MainFrame.getInstance(), "已删除历史记录");
                    }
                }
            }
        });
        this.add(historyItem);
        this.add(historyCleanItem);
    }

    public void addHistoryItem(History history) {
        HistoryMenuItem item = null;
        String pageIconPath = System.getProperty("user.dir") + "\\cache\\" + history.getHost() + "\\favicon.png";
        if (itemMap.get(history.getTime().toString()) == null) {
            String pageTitle = history.getTitle();
            item = new HistoryMenuItem(pageIconPath, pageTitle, history.getTime());
            this.add(item, 1);
        } else {
            item = itemMap.get(history.getTime().toString());
            item.setPageIcon(pageIconPath);
            item.setPageText(history.getTitle());
        }
        item.setInnerUrl(history.getFullUrl());
        itemMap.put(history.getTime().toString(), item);
        if (itemMap.size() > 15) {
            String key = itemMap.entrySet().iterator().next().getKey();
            this.remove(itemMap.get(key));
            itemMap.remove(key);
        }
        item.itemResize(item, 500);
    }

    public void removeHistoryItem(String timeValue) {
        this.remove(itemMap.get(timeValue));
        itemMap.remove(timeValue);
        this.setVisible(false);
    }

}
