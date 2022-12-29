package browser.util;

import com.ctreber.aclib.image.ico.ICOFile;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class IconFinder {

    public static String saveAndGetIcon(String browserUrl, String iconHref) {
        try {
            if (iconHref == null || "".equals(iconHref)) {
                return "";
            }
            String iconUrl = getIconRealUrl(browserUrl, iconHref);
            String cacheDirPath = System.getProperty("user.dir") + "/cache/" + new URL(browserUrl).getHost();
            File iconCacheDir = new File(cacheDirPath);
            if (!iconCacheDir.exists() && !iconCacheDir.isFile()) {
                iconCacheDir.mkdirs();
            }
            String sub = iconUrl.substring(iconUrl.lastIndexOf("."));
            if (".ico".equals(sub)) {
                File pngFile = new File(cacheDirPath + "/favicon.png");
                if (!pngFile.exists() || !pngFile.isFile()) {
                    File icoFile = new File(cacheDirPath + "/favicon.ico");
                    FileUtils.copyURLToFile(new URL(iconUrl), icoFile);
                    ICOFile ico = new ICOFile(new FileInputStream(icoFile));
                    BufferedImage icoImg = (BufferedImage) ico.getImages().get(0);
                    ImageIO.write(icoImg, "png", pngFile);
                }
                return pngFile.getAbsolutePath();
            } else if (".jpg".equals(sub) || ".png".equals(sub)) {
                File iconFile = new File(cacheDirPath + "/favicon" + sub);
                if (!iconFile.exists() || !iconFile.isFile()) {
                    FileUtils.copyURLToFile(new URL(iconUrl), iconFile);
                }
                return iconFile.getAbsolutePath();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // 获取Icon真实地址
    private static String getIconRealUrl(String browserUrl, String iconHref) {
        if (iconHref.contains("http")) {
            return iconHref;
        }
        try {
            if (iconHref.charAt(0) == '/' && iconHref.charAt(1) == '/') {
                URL url = new URL(browserUrl);
                iconHref = url.getProtocol() + ":" + iconHref;
            } else if (iconHref.charAt(0) == '/') {// 判断是否为相对路径或根路径
                URL url = new URL(browserUrl);
                iconHref = url.getProtocol() + "://" + url.getHost() + iconHref;
            } else {
                String base = browserUrl.split("#")[0];
                iconHref = base + iconHref;
            }
            return iconHref;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
