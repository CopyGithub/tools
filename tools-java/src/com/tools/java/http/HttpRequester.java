package com.tools.java.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.tools.java.json.Json;

public class HttpRequester {
    private String mMethod = "get";
    private String mUrl;
    private JSONObject mHeaders;
    protected String mParams = "";
    protected byte[] mBody = null;
    private HttpURLConnection mConnection;

    public HttpRequester() {
        // Nothing
    }

    /**
     * 使用{@link URL}初始化请求
     * 
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     */
    public HttpRequester(String url) {
        mHeaders = new JSONObject();
        int index = url.indexOf('?');
        if (index != -1) {
            mUrl = url.substring(0, index);
            mParams = url.substring(index);
        } else {
            mUrl = url;
        }
    }

    /**
     * 使用{@link JSONObject}参数初始化一个请求<br>
     * 示例格式为：{@code {"host":"","api":"","method":"","headers":{},"params":{},"body":{}}}
     * 
     * @param script
     * @throws Exception
     */
    public HttpRequester(JSONObject script) throws Exception {
        mUrl = Json.getString(script, "host") + Json.getString(script, "api");
        mMethod = Json.getString(script, "method").toUpperCase();
        mHeaders = Json.getJSONObject(script, "headers");
        addJson(Json.getJSONObject(script, "params"), true);
        addJson(Json.getJSONObject(script, "body"), false);
    }

    /**
     * 执行请求并返回响应结果
     * 
     * @return
     * @throws Exception
     */
    public HttpResponser exec() throws Exception {
        mConnection = (HttpURLConnection) new URL(mUrl + mParams).openConnection();
        setHeaders();
        switch (mMethod) {
        case "get":
            mConnection.connect();
            break;
        case "post":
            mConnection.setDoOutput(true);
            mConnection.setRequestMethod(mMethod);
            if (mBody != null) {
                mConnection.getOutputStream().write(mBody);
            }
            break;
        default:
            throw new Exception("不支持请求的方法：" + mMethod);
        }
        return new HttpResponser(mConnection);
    }

    /**
     * 设置参数
     * 
     * @param key
     * @param value
     * @param encode
     * @throws Exception
     */
    public void setParam(String key, String value, boolean encode) throws Exception {
        if (key == null || value == null || key.isEmpty() || value.isEmpty()) {
            throw new Exception("key或value不能为空");
        }
        value = encode ? URLEncoder.encode(value, "utf-8") : value;
        String addParam = key + "=" + value;
        mParams = mParams.isEmpty() ? "?" + addParam : mParams + "&" + addParam;
    }

    /**
     * 设置请求方式
     * 
     * @param method
     */
    public void setMethod(String method) {
        mMethod = method.toLowerCase();
    }

    /**
     * 添加Header值
     * 
     * @param key
     * @param value
     * @throws JSONException
     */
    public void addHeader(String key, String value) throws JSONException {
        mHeaders.putOpt(key, value);
    }

    /**
     * 设置请求的headers
     */
    private void setHeaders() {
        Iterator<String> keys = mHeaders.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            mConnection.setRequestProperty(key, mHeaders.getString(key));
        }
    }

    /**
     * 添加Body值
     * 
     * @param body
     */
    public void addBody(byte[] body) {
        mBody = bodyMerger(mBody, body);
    }

    /**
     * Merger Body参数
     * 
     * @param byte_1
     * @param byte_2
     * @return
     */
    private byte[] bodyMerger(byte[] byte_1, byte[] byte_2) {
        if (byte_1 == null) {
            return byte_2;
        }
        if (byte_2 == null) {
            return byte_1;
        }
        byte[] separator = "&".getBytes();
        byte[] byte_3 = new byte[byte_1.length + byte_2.length + separator.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(separator, 0, byte_3, byte_1.length, separator.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length + separator.length, byte_2.length);
        return byte_3;
    }

    /**
     * 添加{@link JSONObject}格式的参数
     * 
     * @param json
     * @throws Exception
     */
    private void addJson(JSONObject json, boolean type) throws Exception {
        if (json != null && json.length() > 0) {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = Json.getString(json, key);
                if (type) {
                    setParam(key, value, false);
                } else {
                    addBody((key + "=" + value).getBytes());
                }
            }
        }
    }

    /**
     * 获取URL
     * 
     * @return
     */
    public String getUrl() {
        return mUrl + mParams;
    }

    /**
     * 获取headers
     * 
     * @return
     */
    public String getHeaders() {
        return mHeaders == null ? null : Json.sortJs(mHeaders.toString());
    }

    /**
     * 获取body
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getBody() throws UnsupportedEncodingException {
        return mBody == null ? null : new String(mBody, "utf-8");
    }
}