package com.pear.commons.tools.http.url;


import com.pear.commons.tools.charactor.StringUtils;
import com.pear.commons.tools.object.ObjectUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by Administrator on 2016/4/8.
 */
public class ParamGetterValue<P> {

    private ParamGetterValue paramGetterValue;

    private String getterUrlValue = "";

    private P paramValue;

    List<GetterValueItem> getterValueItems;

    public ParamGetterValue getParamGetterValue() {
        return paramGetterValue;
    }

    public void setParamGetterValue(ParamGetterValue paramGetterValue) {
        this.paramGetterValue = paramGetterValue;
    }

    public void addParamGetterValueItem(GetterValueItem getterValueItem) {
        if (getterValueItems == null) {
            getterValueItems = new ArrayList();
        }
        getterValueItems.add(getterValueItem);
    }

    public String getGetterUrlValue() {
        if (getterUrlValue == null || getterUrlValue.equals("")) {
            getterUrlValue = itemToUrlValue();
            if (getterUrlValue != null) {
                getterUrlValue = getterUrlValue.replaceFirst("&", "?");
            }
        }
        return getterUrlValue;
    }

    public ParamGetterValue(P object) {
        StringBuffer apendValue = new StringBuffer("");
        if (this.getterValueItems == null) {
            this.getterValueItems = new ArrayList();
        }
        if (object instanceof Map) {
            Set<String> keySets = ((Map) object).keySet();
            if (keySets != null && keySets.size() > 0) {
                for (String key : keySets) {
                    Object keyValue = ((Map) object).get(key);
                    if (keyValue != null) {
                        if (keyValue instanceof Map) {
                            this.getterValueItems.add(new GetterValueMapItem(key, keyValue));
                        } else if (keyValue instanceof List) {
                            this.getterValueItems.add(new GetterValueListItem(key, keyValue));
                        } else if (ObjectUtils.isCollection(keyValue)) {
                            Collection<?> collectionResult = (Collection<?>)keyValue;
                            List<Object> list = new ArrayList();
                            Collection collection = null;
                            try {
                                collection = (Collection) keyValue.getClass().newInstance();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            for (Object o : collectionResult) {
                                if(ObjectUtils.isValueType(o) || o == null){
                                    list.add(o);
                                }
                            }
                            this.getterValueItems.add(new GetterValueListItem(key, collection));
                        } else if (keyValue.getClass().isArray()) {
                            Object[] arrayObjects = (Object[]) keyValue;
                            List<Object> listObjects = new ArrayList();
                            for (Object o : arrayObjects) {
                                listObjects.add(o);
                            }
                            this.getterValueItems.add(new GetterValueListItem(key, listObjects));
                        } else {
                            this.getterValueItems.add(new GetterValueItem(key, keyValue));
                        }
                    }
                }
            }
        } else if(object instanceof List) {
            List<?> objects = (List<?>)object;
            this.getterValueItems.add(new GetterValueListItem("", objects));
        } else {
            if (StringUtils.isNotEmpty(object + "")) {
                Field[] fields = object.getClass().getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        Object filedValue = ObjectMethodUtil.getObjectFiledValue(object, field);
                        if (filedValue instanceof List) {
                            this.getterValueItems.add(new GetterValueListItem(field.getName(), filedValue));
                        } else if (filedValue instanceof Map) {
                            this.getterValueItems.add(new GetterValueMapItem(field.getName(), filedValue));
                        } else {
                            this.getterValueItems.add(new GetterValueItem(field.getName(), filedValue));
                        }
                    }
                }
            }
        }
        this.paramValue = object;
        getterUrlValue = apendValue.toString();
    }

    private String itemToUrlValue() {
        StringBuffer urlValueBuffer = new StringBuffer("");
        if (this.getterValueItems != null && this.getterValueItems.size() > 0) {
            for (GetterValueItem getterValueItem : this.getterValueItems) {
                urlValueBuffer.append(getterValueItem.itemToUrlValue());
            }
        }
        return urlValueBuffer.toString();
    }

}
