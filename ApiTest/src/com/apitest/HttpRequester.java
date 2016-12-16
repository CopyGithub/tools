package com.apitest;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import org.json.JSONObject;

import com.cashreward.CashReward;

public class HttpRequester {

    private String mMethod;
    private String mHost;
    private String mApi;
    private JSONObject mHeaders;
    private JSONObject mProject;
    private String mParams = "";
    private byte[] mBody = null;
    private HttpURLConnection mConnection;

    public enum ProjectName {
        CASHREWARD
    }

    /**
     * 初始化请求参数
     * 
     * @param script
     * @param config
     * @throws NumberFormatException
     * @throws Exception
     */
    private void init(JSONObject script, JSONObject config)
            throws NumberFormatException, Exception {
        mMethod = JsonOperation.getString(script, Const.SCRIPT_METHOD, config).toUpperCase();
        mHost = JsonOperation.getString(script, Const.SCRIPT_HOST, config);
        mApi = JsonOperation.getString(script, Const.SCRIPT_API, config);
        mProject = JsonOperation.getJSONObject(script, Const.SCRIPT_PROJECT);
        mHeaders = JsonOperation.getJSONObject(script, Const.SCRIPT_HEADERS);
        initParamsAndBodyByProject(script, config);
    }

    /**
     * 根据项目初始化params和body参数
     * 
     * @param script
     * @param config
     * @throws Exception
     */
    private void initParamsAndBodyByProject(JSONObject script, JSONObject config) throws Exception {
        JSONObject params = JsonOperation.getJSONObject(script, Const.SCRIPT_PARAMS);
        JSONObject body = JsonOperation.getJSONObject(script, Const.SCRIPT_BODY);
        mParams = getParams(params, config);
        mBody = getBody(body, config);
        if (mProject != null) {
            String projectName = mProject.getString(Const.SCRIPT_PROJECT_NAME);
            // 根据项目初始化params和body
            if (ProjectName.CASHREWARD.toString().equalsIgnoreCase(projectName)) {
                mParams = paramsMerger(mParams, CashReward.getParams(mProject, config));
                mBody = bodyMerger(mBody, CashReward.getBody(mProject, config));
            } else {
                throw new Exception("不支持项目名为【" + projectName + "】的项目");
            }
        }
        if (mParams.length() != 0) {
            mParams = "?" + mParams;
        }
    }

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
     * 获取通用的params值
     * 
     * @param params
     * @param config
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    private String getParams(JSONObject params, JSONObject config)
            throws NumberFormatException, Exception {
        if (params == null || params.length() == 0) {
            return "";
        }
        Iterator<String> paramKeys = params.keys();
        String param = "";
        while (paramKeys.hasNext()) {
            String paramKey = paramKeys.next();
            param += paramKey + "="
                    + URLEncoder.encode(JsonOperation.getString(params, paramKey, config), "utf-8");
            if (paramKeys.hasNext()) {
                param += "&";
            }
        }
        return param;
    }

    private static byte[] getBody(JSONObject bodys, JSONObject config) throws Exception {
        if (bodys != null) {
            Iterator<String> bpdysKeys = bodys.keys();
            String body = "";
            while (bpdysKeys.hasNext()) {
                String bodyKey = bpdysKeys.next();
                body += bodyKey + "=" + JsonOperation.getString(bodys, bodyKey, config);
                if (bpdysKeys.hasNext()) {
                    body += "&";
                }
            }
            return body.getBytes();
        }
        return null;
    }

    /**
     * 执行请求并返回响应结果
     * 
     * @param script
     * @param config
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    protected HttpResponser exec(JSONObject script, JSONObject config)
            throws NumberFormatException, Exception {
        init(script, config);
        mConnection = (HttpURLConnection) new URL(mHost + mApi + mParams).openConnection();
        switch (mMethod) {
        case "GET":
            mConnection.connect();
            break;
        case "POST":
            mConnection.setDoOutput(true);
            mConnection.setRequestMethod(mMethod);
            setHeaders();
            mConnection.getOutputStream().write(mBody);
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

    protected String getUrl() {
        return mConnection.getURL().toString();
    }

    protected String getBody() throws UnsupportedEncodingException {
        return mBody == null ? null : new String(mBody, "utf-8");
    }
}
