package com.cc.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.params.convert.ParamEncodeAndDecode;

class HttpRequester {

    private String mMethod;
    private String mHost;
    private String mApi;
    private JSONObject mRequestHeaders;
    private ProjectType mProjectType;
    private String mParams = "";
    private RequestBodyType mRequestBodyType;
    private byte[] mBody;
    private HttpURLConnection mConnection;

    protected enum RequestBodyType {
        JSON, FORM
    }

    protected enum ResponseType {
        STRING
    }

    protected enum ProjectType {
        CASHREWARD
    }

    protected class HttpResponser {
        int mResponseCode;
        String mResponseMessage;
        String mContentType;
        JSONObject mResponseHeaders;
        Object mResponseContent;
    }

    private void initRequester(JSONObject script) {
        mMethod = getString(script, Const.SCRIPT_METHOD).toLowerCase();
        mHost = getString(script, Const.SCRIPT_HOST);
        mApi = getString(script, Const.SCRIPT_API);
        mProjectType = geProjectType(script);
        mRequestHeaders = getJSONObject(script, Const.SCRIPT_HEADERS);
        mParams = getParams(script);
        mRequestBodyType = getRequestBodyType(script);
        mBody = getBody(script);
    }

    private Object getObject(JSONObject script, String key) {
        return script.isNull(key) ? null : script.get(key);
    }

    private String getString(JSONObject script, String key) {
        Object value = getObject(script, key);
        return value == null ? null : replaceScriptParam(value.toString().trim());
    }

    private String replaceScriptParam(String value) {
        int start = value.indexOf(Const.SCRIPT_VARIATE[0]);
        int end = value.indexOf(Const.SCRIPT_VARIATE[1]);
        if (start != -1 && end != -1) {
            // TODO
            value = value.substring(0, start)
                    + MainFrame.configJs.get(value.substring(start + 2, end)).toString().trim()
                    + value.substring(end + 2).trim();
            replaceScriptParam(value);
        }
        return value;
    }

    private ProjectType geProjectType(JSONObject script) {
        String value = getString(script, Const.SCRIPT_PROJECT_TYPE);
        for (ProjectType pt : ProjectType.values()) {
            if (value != null && pt.toString().equals(value.toUpperCase())) {
                return pt;
            }
        }
        return null;
    }

    private JSONObject getJSONObject(JSONObject script, String key) {
        try {
            if (script.isNull(key)) {
                return null;
            }
            JSONObject value = script.getJSONObject(key);
            value = value.length() > 0 ? value : null;
            return value;
        } catch (JSONException e) {
            // TODO
            MainFrame.throwException(e);
            return null;
        }
    }

    private String getParams(JSONObject script) {
        JSONObject params = getJSONObject(script, Const.SCRIPT_PARAMS);
        if (params == null || params.length() == 0) {
            return "";
        }
        if (ProjectType.CASHREWARD.equals(mProjectType)) {
            return "?" + getCashrewardEncode(params,
                    Integer.valueOf(getString(script, Const.SCRIPT_REQUEST_TYPE)));
        } else {
            return getParamsNormal(params);
        }
    }

    private String getParamsNormal(JSONObject params) {
        Iterator<String> paramKeys = params.keys();
        String param = "?";
        while (paramKeys.hasNext()) {
            String paramKey = paramKeys.next();
            try {
                param += paramKey + "=" + URLEncoder.encode(getString(params, paramKey), "utf-8");
            } catch (UnsupportedEncodingException e) {
                // TODO
                MainFrame.throwException(e);
            }
            if (paramKeys.hasNext()) {
                param += "&";
            }
        }
        return param;
    }

    private String getCashrewardEncode(JSONObject params, int type) {
        Iterator<String> paramKeys = params.keys();
        while (paramKeys.hasNext()) {
            String paramKey = paramKeys.next();
            params.put(paramKey, getString(params, paramKey));
        }
        try {
            return "p=" + ParamEncodeAndDecode.encode(params, type);
        } catch (Exception e) {
            // TODO
            MainFrame.throwException(e);
            return "";
        }
    }

    private RequestBodyType getRequestBodyType(JSONObject script) {
        String value = getString(script, Const.SCRIPT_REQUEST_BODY_TYPE);
        for (RequestBodyType rbt : RequestBodyType.values()) {
            if (value != null && rbt.toString().equals(value.toUpperCase())) {
                return rbt;
            }
        }
        return null;
    }

    private byte[] getBody(JSONObject script) {
        Object body = getObject(script, Const.SCRIPT_BODY);
        if (body == null) {
            return null;
        } else if (body instanceof JSONObject) {
            JSONObject jsBody = getJSONObject(script, Const.SCRIPT_BODY);
            if (ProjectType.CASHREWARD.equals(mProjectType)) {
                return getCashrewardEncode(jsBody,
                        Integer.valueOf(getString(script, Const.SCRIPT_REQUEST_TYPE))).getBytes();
            }
            if (RequestBodyType.FORM.equals(mRequestBodyType)) {
                // TODO
            } else if (RequestBodyType.JSON.equals(mRequestBodyType)) {
                return jsBody.toString().getBytes();
            }
        }
        return body.toString().getBytes();
    }

    protected HttpResponser exec(JSONObject script) throws MalformedURLException, IOException {
        initRequester(script);
        HttpResponser responser = new HttpResponser();
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
            System.err.println("请求方法不支持");
        }
        setReponse(responser, script);
        return responser;
    }

    private void setHeaders() {
        if (mRequestHeaders == null) {
            return;
        }
        Iterator<String> keys = mRequestHeaders.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            mConnection.setRequestProperty(key, mRequestHeaders.getString(key));
        }
    }

    private ResponseType geResponseType(JSONObject script) {
        String value = getString(script, Const.SCRIPT_RESPONSE_TYPE);
        for (ResponseType rt : ResponseType.values()) {
            if (value != null && rt.toString().equals(value.toUpperCase())) {
                return rt;
            }
        }
        return null;
    }

    private void setReponse(HttpResponser responser, JSONObject script) throws IOException {
        responser.mResponseCode = mConnection.getResponseCode();
        responser.mResponseMessage = mConnection.getResponseMessage();
        responser.mContentType = mConnection.getContentType();
        responser.mResponseHeaders = getResponseHeaders();
        InputStream inputStream = null;
        if (responser.mResponseCode == HttpURLConnection.HTTP_OK) {
            inputStream = mConnection.getInputStream();
        } else {
            inputStream = mConnection.getErrorStream();
        }
        ResponseType responseType = geResponseType(script);
        if (responseType == null) {
            // TODO 从响应中获取响应类型
        }
        switch (responseType) {
        case STRING:
            responser.mResponseContent = inputStream2String(inputStream);
            break;

        default:
            responser.mResponseContent = inputStream;
            break;
        }
    }

    private String inputStream2String(InputStream inputStream) throws IOException {
        String text = "";
        String line = "";
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream, "utf-8"));
        while ((line = bufferedReader.readLine()) != null) {
            text += line;
        }
        return text;
    }

    private JSONObject getResponseHeaders() {
        JSONObject js = new JSONObject();
        Map<String, List<String>> maps = mConnection.getHeaderFields();
        Iterator<String> keys = maps.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            List<String> strings = maps.get(key);
            String value = "";
            for (String string : strings) {
                value += string + ";";
            }
            js.put(key == null ? "null_null" : key, value);
        }
        return js;
    }

    protected String getUrl() {
        return mConnection.getURL().toString();
    }

    protected String getBody() {
        return mBody == null ? null : mBody.toString();
    }
}
