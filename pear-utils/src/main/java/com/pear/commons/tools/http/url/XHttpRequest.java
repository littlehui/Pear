package com.pear.commons.tools.http.url;

import com.pear.commons.tools.charactor.json.JsonUtils;
import com.pear.commons.tools.io.IOUtils;
import org.apache.log4j.Logger;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/8.
 */
public class XHttpRequest<P> {

    Logger logger = Logger.getLogger(XHttpRequest.class);

    protected Map<String, Object> headers = new HashMap<String, Object>();

    protected String charSet = "utf-8";

    protected String urlParams = "";

    protected P param;

    protected URL url;

    protected HttpURLConnection httpConnection;

    protected String getUrl;

    private ParamGetterValue<P> pParamGetterValue;

    protected String method = "GET";

    private String postJson = "";

    public P getParam() {
        return param;
    }

    public String getMethod() {
        return method;
    }

    public String getCharSet() {
        return charSet;
    }

    public HttpURLConnection getHttpConnection() {
        return httpConnection;
    }

    public String getPostJson() {
        return postJson;
    }

    public void setPostJson(String postJson) {
        this.postJson = postJson;
    }

    public String getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(String urlParams) {
        this.urlParams = urlParams;
    }

    public String getGetUrl() {
        return getUrl;
    }

    public void setGetUrl(String getUrl) {
        this.getUrl = getUrl;
    }

    protected Map<String, Object> getDefaultHeaders() {
        Map<String, Object> defaultHeaders = new HashMap();
        defaultHeaders.put("Accept", "*/*");
        defaultHeaders.put("Connection", "Keep-Alive");
        defaultHeaders.put("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        defaultHeaders.put("Accept-Charset", "utf-8");
        defaultHeaders.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers = defaultHeaders;
        return headers;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addHeader(String headerKey, String headerValue) {
        if (headers == null) {
            headers = getDefaultHeaders();
        }
        headers.put(headerKey, headerValue);
    }

    protected void initConnection() throws IOException {
        if ("POST".equals(method)) {
            this.url = new URL(getUrl);
            this.postJson = JsonUtils.toJson(param);
        } else {
            this.url = new URL(getUrl + urlParams);
        }
        httpConnection = (HttpURLConnection) url.openConnection();
        for (String keyset : headers.keySet()) {
            httpConnection.setRequestProperty(keyset, headers.get(keyset).toString());
        }
        /**
         * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
         * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
         */
        httpConnection.setRequestMethod(method);
        httpConnection.setUseCaches(false);
        if ("POST".equals(method)) {
            httpConnection.setDoOutput(true);
        } else {
            httpConnection.setDoOutput(true);
        }
        httpConnection.setDoInput(true);
    }

    public XHttpRequest(String url, P param, String charSet) {
        this.getUrl = url;
        this.param = param;
        pParamGetterValue = new ParamGetterValue(param);
        headers = getDefaultHeaders();
        if (!this.charSet.equals(charSet)) {
            headers.put("Content-Type", "application/x-www-form-urlencoded;charset=" + charSet);
            headers.put("Accept-Charset", charSet);
        }
        this.charSet = charSet;
    }

    /**
     * 编码
     *
     * @param source
     * @return
     */
    public static String urlEncode(String source, String encode) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "0";
        }
        return result;
    }

    public String doRequest() {
        this.toUrlParams();
        OutputStreamWriter out = null;
        try {
            this.initConnection();
            // 一旦发送成功，用以下方法就可以得到服务器的回应：
            String sTotalString;
            InputStream urlStream;
            out = new OutputStreamWriter(httpConnection.getOutputStream(), charSet);
            if (method.equals("POST")) {
                out.write(this.postJson); //向页面传递数据。post的关键所在！
            }
            // remember to clean up
            out.flush();
            urlStream = httpConnection.getInputStream();
            logger.debug("连接状态:" + urlStream.available());
            //new InputStreamReader(l_urlStream,)
            sTotalString = IOUtils.in2Str(urlStream, charSet);
            return sTotalString;
        } catch (Exception e) {
            e.printStackTrace();
            throw new HttpGetterException(e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new HttpGetterException(e);
                }
            }
            httpConnection.disconnect();
        }
    }

    protected void toUrlParams() {
        String urlValue = pParamGetterValue.getGetterUrlValue();
        if (urlValue != null) {
            this.urlParams = urlValue;
        }
    }

    public static void main1(String args[]) {
        Map<String, String> param = new HashMap();
        param.put("commodityCode", "151000000238");
        XHttpRequest<Map<String, String>> XHttpRequest = new XHttpRequest("http://mai.17173.com/commodity/materil/detail.html", param, "utf-8");
        String result = XHttpRequest.doRequest();
        System.out.println(result);
    }

    public static void main(String args[]) {
        Map<String, Object> param = new HashMap();
        try {
            FileImageInputStream imageInputStream = new FileImageInputStream(new File("C:\\Users\\Administrator\\Documents\\美图图库\\示例图片_03.jpg"));
            //imageInputStream.readBytes()

            XHttpRequest<Map<String, Object>> XHttpRequest = new XHttpRequest("http://netmine.vicp.net:24869/upload", param, "utf-8");
            XHttpRequest.addHeader("Content-Type","jpeg");
            XHttpRequest.setMethod("POST");
            String result = XHttpRequest.doRequest();

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
