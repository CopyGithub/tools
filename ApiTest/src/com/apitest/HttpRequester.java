package com.apitest;

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
        mMethod = JsonOperation.getString(script, Const.SCRIPT_METHOD, config).toLowerCase();
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
        String body = JsonOperation.getString(script, Const.SCRIPT_BODY, config);
        if (mProject == null) {
            mParams = getParams(params, config);
            mBody = body == null ? null : body.getBytes();
        } else {
            String projectName = mProject.getString(Const.SCRIPT_PROJECT_NAME);
            // 根据项目初始化params和body
            if (ProjectName.CASHREWARD.toString().equalsIgnoreCase(projectName)) {
                mParams = CashReward.getParams(params, mProject, config);
                mBody = CashReward.getBody(body, mProject, config);
            } else {
                throw new Exception("不支持项目名为【" + projectName + "】的项目");
            }
        }
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
        String param = "?";
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
        case "get":
            mConnection.connect();
            break;
        case "post":
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

    protected String getBody() {
        return mBody == null ? null : mBody.toString();
    }
}
