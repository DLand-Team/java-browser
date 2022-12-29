package browser.ui.component.menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ToolMenu extends JPopupMenu {

	public ToolMenu() {

		JMenuItem htmlItem = new JMenuItem("查看html代码");
		htmlItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
			}
		});
		this.add(htmlItem);

	}

}
