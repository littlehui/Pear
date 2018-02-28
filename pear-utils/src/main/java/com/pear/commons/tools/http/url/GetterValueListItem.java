package com.pear.commons.tools.http.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2016/4/8.
 */
public class GetterValueListItem extends GetterValueItem {

    public GetterValueListItem(String name, Object value) {
        super(name, value);
    }

    @Override
    public String itemToUrlValue() {
        Object value = this.getValue();
        StringBuffer listBuffer = new StringBuffer("&");
        listBuffer.append(getName()).append("=");
        if (value != null && value instanceof List) {
            List<Object> values = (List<Object>) value;
            for (Object stringValue : values) {
                try {
                    listBuffer.append(URLEncoder.encode(stringValue + "", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                listBuffer.append(",");
            }
            listBuffer.deleteCharAt(listBuffer.length() - 1);
            return listBuffer.toString();
        }
        return "";
    }
}
