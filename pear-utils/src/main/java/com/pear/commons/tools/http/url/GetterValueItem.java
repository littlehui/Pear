package com.pear.commons.tools.http.url;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2016/4/8.
 */
public class GetterValueItem implements ItemToUrlValue {

    private Object value;

    private String name;

    public GetterValueItem(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String toParamValue() {
        try {
            return "&" + name + "=" + URLEncoder.encode(value + "", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String itemToUrlValue() {
        return toParamValue();
    }
}
