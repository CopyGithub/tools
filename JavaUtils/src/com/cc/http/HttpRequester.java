package com.cc.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONObject;

import com.cc.common.JavaCommon;
import com.cc.json.Json;

public abstract class HttpRequester {
    private String mMethod;
    private String mHost;
    private String mApi;
    private JSONObject mHeaders;
    protected String mParams = "";
    protected byte[] mBody = null;
    private HttpURLConnection mConnection;

    /**
     * 使用{@link URL}初始化请求
     * 
     * @param url
     * @throws MalformedURLException
     * @throws IOException
     */
    public HttpRequester(String url) throws MalformedURLException, IOException {
        mMethod = "GET";
        mHeaders = new JSONObject();
        mConnection = (HttpURLConnection) new URL(url).openConnection();
    }

    /**
     * 使用{@link JSONObject}初始化请求
     * 
     * @param script
     *            参数
     * @param config
     *            配置文件，用户替换参数中的通用变量或其它配置项，{@code Null}表示没有配置文件
     * @throws Exception
     */
    public HttpRequester(JSONObject script, JSONObject config) throws Exception {
        script = new JSONObject(JavaCommon.replaceScriptParam(script.toString(), config));
        mMethod = Json.getString(script, "method").toUpperCase();
        mHost = Json.getString(script, "host");
        mApi = Json.getString(script, "api");
        mHeaders = Json.getJSONObject(script, "headers");
        mParams = parseParams(Json.getJSONObject(script, "params"));
        mBody = parseBody(Json.getJSONObject(script, "body"));
        initProjectParams(script, config);
        if (mParams.length() != 0) {
            mParams = "?" + mParams;
        }
        mConnection = (HttpURLConnection) new URL(mHost + mApi + mParams).openConnection();
    }

    /**
     * 获取通用的param值
     * 
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String parseParams(JSONObject params) throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        }
        Iterator<String> keys = params.keys();
        String param = "";
        while (keys.hasNext()) {
            String key = keys.next();
            String decode = URLDecoder.decode(Json.getString(params, key), "utf-8");
            param += key + "=" + URLEncoder.encode(decode, "utf-8");
            if (keys.hasNext()) {
                param += "&";
            }
        }
        return param;
    }

    /**
     * 获取通用的body值
     * 
     * @param bodys
     * @return
     * @throws Exception
     */
    private byte[] parseBody(JSONObject bodys) {
        if (bodys == null) {
            return null;
        }
        Iterator<String> keys = bodys.keys();
        String body = "";
        while (keys.hasNext()) {
            String key = keys.next();
            body += key + "=" + Json.getString(bodys, key);
            if (keys.hasNext()) {
                body += "&";
            }
        }
        return body.getBytes();
    }

    /**
     * 初始化工程参数，由子类继承重写
     * 
     * @param script
     */
    public abstract void initProjectParams(JSONObject script, JSONObject config) throws Exception;

    /**
     * Merger URL参数
     * 
     * @param params_1
     * @param params_2
     * @return
     */
    protected String paramsMerger(String params_1, String params_2) {
        if (params_1.length() == 0) {
            return params_2;
        }
        if (params_2.length() == 0) {
            return params_1;
        }
        return params_1 + "&" + params_2;
    }

    /**
     * Merger Body参数
     * 
     * @param byte_1
     * @param byte_2
     * @return
     */
    protected byte[] bodyMerger(byte[] byte_1, byte[] byte_2) {
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
     * 执行请求并返回响应结果
     * 
     * @param script
     * @return
     * @throws Exception
     */
    public HttpResponser exec() throws Exception {
        setHeaders();
        switch (mMethod) {
        case "GET":
            mConnection.connect();
            break;
        case "POST":
            mConnection.setDoOutput(true);
            mConnection.setRequestMethod(mMethod);
            if (mBody != null) {
                mConnection.getOutputStream().write(mBody);
            }
            break;
        default:
            throw new Exception("不支持请求的方法：" + mMethod);
        }
        HttpResponser responser = new HttpResponser(mConnection);
        return responser;
    }

    /**
     * 设置请求的headers
     */
    private void setHeaders() {
        if (mHeaders == null) {
            return;
        }
        Iterator<String> keys = mHeaders.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            mConnection.setRequestProperty(key, mHeaders.getString(key));
        }
    }

    /**
     * 设置请求方法
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
     */
    public void addHeader(String key, String value) {
        mHeaders.putOpt(key, value);
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
     * 获取实际发送的URL
     * 
     * @return
     */
    public String getUrl() {
        return mConnection.getURL().toString();
    }

    /**
     * 获取实际发送的body
     * 
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getBody() throws UnsupportedEncodingException {
        return mBody == null ? null : new String(mBody, "utf-8");
    }

    public String getHeader() {
        return mHeaders == null ? null : Json.sortJs(mHeaders.toString());
    }
}
