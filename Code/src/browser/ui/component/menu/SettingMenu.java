package browser.ui.component.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class SettingMenu extends JPopupMenu {

	public SettingMenu() {
		
		JMenuItem settingItem = new JMenuItem("设置");
		this.add(settingItem);
		
		JMenuItem aboutItem = new JMenuItem("关于");
		this.add(aboutItem);
		
	}

}
