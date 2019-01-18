package com.cc.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.cc.json.Json;

public class HttpRequester {
    private String mMethod;
    private String mUrl;
    private JSONObject mHeaders;
    protected String mParams = "";
    protected byte[] mBody = null;
    private HttpURLConnection mConnection;

    private final static String ENC = "utf-8";

    private enum Method {
        GET, POST
    }

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
        mMethod = "GET";
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
     * @throws UnsupportedEncodingException
     */
    public HttpRequester(JSONObject script) throws UnsupportedEncodingException {
        mUrl = Json.getString(script, "host") + Json.getString(script, "api");
        mMethod = Json.getString(script, "method").toUpperCase();
        mHeaders = Json.getJSONObject(script, "headers");
        addJson(Json.getJSONObject(script, "params"), true);
        addJson(Json.getJSONObject(script, "body"), false);
    }

    /**
     * 执行请求并返回响应结果
     * 
     * @param script
     * @return
     * @throws Exception
     */
    public HttpResponser exec() throws Exception {
        mConnection = (HttpURLConnection) new URL(mUrl + mParams).openConnection();
        setHeaders();
        if (mMethod.equals(Method.GET.toString())) {
            mConnection.connect();
        } else if (mMethod.equals(Method.POST.toString())) {
            mConnection.setDoOutput(true);
            mConnection.setRequestMethod(mMethod);
            if (mBody != null) {
                mConnection.getOutputStream().write(mBody);
            }
        } else {
            throw new Exception("不支持请求的方法：" + mMethod);
        }
        return new HttpResponser(mConnection);
    }

    /**
     * 添加URL参数
     * 
     * @param key
     * @param value
     * @throws UnsupportedEncodingException
     */
    public void addParam(String param) throws UnsupportedEncodingException {
        param = URLEncoder.encode(param, ENC);
        mParams = mParams.isEmpty() ? "?" + param : paramsMerger(mParams, param);
    }

    /**
     * Merger URL参数
     * 
     * @param params_1
     * @param params_2
     * @return
     */
    private String paramsMerger(String params_1, String params_2) {
        if (params_1.length() == 0) {
            return params_2;
        }
        if (params_2.length() == 0) {
            return params_1;
        }
        return params_1 + "&" + params_2;
    }

    /**
     * 设置请求方式
     * 
     * @param method
     */
    public void setMethod(String method) {
        mMethod = method.toUpperCase();
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
     * @throws UnsupportedEncodingException
     */
    private void addJson(JSONObject json, boolean type) throws UnsupportedEncodingException {
        if (json != null && json.length() > 0) {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = Json.getString(json, key);
                if (type) {
                    addParam(key + "=" + value);
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