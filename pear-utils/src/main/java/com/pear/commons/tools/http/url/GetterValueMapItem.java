package com.pear.commons.tools.http.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/8.
 */
public class GetterValueMapItem extends GetterValueItem {

    public GetterValueMapItem(String name, Object value) {
        super(name, value);
    }

    @Override
    public String itemToUrlValue() {
        StringBuilder mapValueBuffer = new StringBuilder("");
        String mName = this.getName();
        Object value = getValue();
        if (value != null && value instanceof Map) {
            Map<String, String> mapValue = (Map<String, String>) value;
            for (Map.Entry<String,String> entry : mapValue.entrySet()) {
                mapValueBuffer.append("&");
                mapValueBuffer.append(this.getMapItemName(entry.getKey(), mName));
                mapValueBuffer.append("=");
                try {
                    mapValueBuffer.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            return mapValueBuffer.toString();
        }
        return "";
    }

    private String getMapItemName(String key, String mName) {
        StringBuffer mapItemName = new StringBuffer(mName);
        mapItemName.append("[");
        mapItemName.append(key);
        mapItemName.append("]");
        return mapItemName.toString();
    }
}
