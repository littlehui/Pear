package com.pear.commons.tools.http.url;

import com.pear.commons.tools.charactor.json.JsonUtils;

/**
 * Created by Administrator on 2016/4/14.
 */
public class XHttpSearchRequest<P, R> extends XHttpRequest<P> {

    public R responseEntity;

    public Class<R> responseEntityClass;

    public R getResponseEntity() {
        return responseEntity;
    }

    public void setResponseEntityClass(Class<R> responseEntityClass) {
        this.responseEntityClass = responseEntityClass;
    }

    public XHttpSearchRequest(String url, P param, String charSet, Class rClass) {
        super(url, param, charSet);
        this.setResponseEntityClass(rClass);
    }

    public R doRequestWithResponseEntity() {
        String result = super.doRequest();
        responseEntity = JsonUtils.toObject(result, responseEntityClass);
        //responseEntity = JsonMapper.buildNonDefaultMapper().fromJson(result, responseEntityClass);
        return responseEntity;
    }
}
