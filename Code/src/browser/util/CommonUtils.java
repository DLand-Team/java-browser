package browser.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import browser.common.Cache;
import browser.common.Static;
import com.alibaba.fastjson.JSON;


public class CommonUtils {

    public static Icon getIcon(String path) {
        return new ImageIcon(Objects.requireNonNull(CommonUtils.class.getResource("/browser/assets/img" + path)));
    }

    /**
     * 复制文字到剪贴板
     */
    public static void setClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);
    }

    public static String readFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        br.close();
        reader.close();
        return sb.toString();
    }

    public static void saveSetting() {
        try {
            CommonUtils.saveStringToFile(JSON.toJSONString(Cache.setting), Static.SETTING_JSON_PATH);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static void saveStringToFile(String content, String path) throws IOException {
        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public static void saveObjToFile(Object obj, String path) throws IOException {
        File file = new File(path);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.flush();
        oos.close();
        fos.flush();
        fos.close();
    }

    public static boolean isMac() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("MAC OS");
    }

    public static boolean isWindows() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    public static boolean isWindows10() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS 10");
    }

    public static boolean isWindows11() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS 11");
    }

}
