package browser.ui.component.tab;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

public class TabButton extends JButton {

	private static final long serialVersionUID = -5634035547632048385L;

	public TabButton(Icon icon, String toolTipText) {
        setIcon(icon);
        setToolTipText(toolTipText);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        setContentAreaFilled(false);
        setFocusable(false);
    }

}
