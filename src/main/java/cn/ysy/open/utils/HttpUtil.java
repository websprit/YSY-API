package cn.ysy.open.utils;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class HttpUtil {
    /**
     * 设置http headMap
     * @param host
     * @param content_type
     * @return
     */
    public Map<String,Object> setHeadMap(String host, String content_type) {
        Map<String,Object> headMap = new HashMap<String,Object>();
        headMap.put("Host",host);
        headMap.put("Content_Type",content_type);

        return headMap;
    }

    public Map<String,Object> setBodyMap(String accessToken,String deviceSerial,String validateCode) {
        Map<String,Object> bodyMap = new HashMap<String,Object>();
        if (accessToken != null) {
            bodyMap.put("accessToken",accessToken);
        }
        if (deviceSerial != null) {
            bodyMap.put("deviceSerial",deviceSerial);
        }
        if (validateCode != null) {
            bodyMap.put("validateCode",validateCode);
        }

        return bodyMap;
    }
}
