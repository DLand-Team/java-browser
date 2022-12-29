package browser.assets.entity;

import browser.common.Static;
import lombok.Data;

@Data
public class Setting {

	private String mainPageAddress;
	private Integer defaultWidth;
	private Integer defaultHeight;
	private Boolean isMaxWindow;

	public Setting() {

	}

	public Setting(String type) {
		switch (type) {
		case "default":
			this.mainPageAddress = Static.DEFAULT_MAIN_PAGE_ADDRESS;
			this.defaultWidth = Static.DEFAULT_WIDTH;
			this.defaultHeight = Static.DEFAULT_HEIGHT;
			this.isMaxWindow = Static.DEFAULT_IS_MAX_WINDOW;
			break;
		default:
			break;
		}
	}

}
