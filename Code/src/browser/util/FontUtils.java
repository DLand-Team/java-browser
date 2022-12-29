package browser.util;


import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FontUtils {

    // 微软雅黑
    public static Font MicrosoftYahei(float size) {
        return getLocalFont("MicrosoftYahei.ttf", Font.PLAIN, size);
    }

    // 小米兰亭
    public static Font MiLT(float size) {
        return getLocalFont("MiLT.ttf", Font.PLAIN, size);
    }

    /**
     * 获取本地字体
     */
    private static Font getLocalFont(String name, int style, float size) {
        Font definedFont = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            is = FontUtils.class.getResourceAsStream("/browser/assets/font/" + name);
            bis = new BufferedInputStream(is);
            // createFont返回一个使用指定字体类型和输入数据的新 Font。<br>
            // 新 Font磅值为 1，样式为 PLAIN,注意 此方法不会关闭 InputStream
            definedFont = Font.createFont(Font.TRUETYPE_FONT, bis);
            // 复制此 Font对象并应用新样式，创建一个指定磅值的新 Font对象。
            definedFont = definedFont.deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bis) {
                    bis.close();
                }
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return definedFont;
    }

}
