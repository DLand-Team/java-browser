package browser.common;

import browser.util.CommonUtils;

public class Static {

	public static final boolean IS_Mac = CommonUtils.isMac();
	public static final boolean IS_Windows = CommonUtils.isWindows();
	public static final boolean IS_Windows_10 = CommonUtils.isWindows10();
	public static final boolean IS_Windows_11 = CommonUtils.isWindows11();
	public final static String VERSION = "0.0.1";

	public final static String DEFAULT_MAIN_PAGE_ADDRESS = "intelyes.club";

	public final static Integer DEFAULT_WIDTH = 1280;

	public final static Integer DEFAULT_HEIGHT = 720;

	public final static Boolean DEFAULT_IS_MAX_WINDOW = true;

	public final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36 Edg/107.0.1418.62";

	public final static String GIT_ADDRESS = "https://gitee.com/CrimsonHu/chromium_browser_based_on_java";

	public final static String HISTORY_JSON_DIR = System.getProperty("user.dir") + "/cache/";
	public final static String HISTORY_JSON_PATH = System.getProperty("user.dir") + "/cache/history.json";

	public final static String SETTING_JSON_DIR = System.getProperty("user.dir") + "/prefer/";
	public final static String SETTING_JSON_PATH = System.getProperty("user.dir") + "/prefer/setting.json";

}
