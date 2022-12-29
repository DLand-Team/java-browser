package browser.common;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import browser.assets.entity.History;
import browser.assets.entity.Setting;
import browser.util.BeanUtils;
import browser.util.CommonUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;

public class Cache {

    // 加载历史记录
    public static LinkedHashMap<String, History> historyMap = new LinkedHashMap<String, History>() {
        {
            try {
                File dir = new File(Static.HISTORY_JSON_DIR);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                File file = new File(Static.HISTORY_JSON_PATH);
                if (!file.exists() || !file.isFile()) {
                    CommonUtils.saveStringToFile("{}", Static.HISTORY_JSON_PATH);
                }
                String str = CommonUtils.readFile(Static.HISTORY_JSON_PATH);
                LinkedHashMap<String, History> map = JSONArray.parseObject(str,
                        new TypeReference<LinkedHashMap<String, History>>() {
                        }, Feature.OrderedField);
                Iterator<Map.Entry<String, History>> iter = map.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, History> entry = iter.next();
                    this.put(entry.getKey(), entry.getValue());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    // 加载设置
    public static Setting setting = new Setting() {
        {
            Setting st;
            try {
                File dir = new File(Static.SETTING_JSON_DIR);
                if (!dir.exists() || !dir.isDirectory()) {
                    dir.mkdirs();
                }
                File file = new File(Static.SETTING_JSON_PATH);
                if (!file.exists() || !file.isFile()) {
                    CommonUtils.saveStringToFile(JSON.toJSONString(new Setting("default")), Static.SETTING_JSON_PATH);
                }
                String str = CommonUtils.readFile(Static.SETTING_JSON_PATH);
                st = JSON.parseObject(str, Setting.class);
            } catch (IOException e) {
                st = new Setting("default");
                e.printStackTrace();
            }
            try {
                BeanUtils.copy(this, st);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}