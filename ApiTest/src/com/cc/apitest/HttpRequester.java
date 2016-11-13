package com.cc.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.params.convert.ParamEncodeAndDecode;

class HttpRequester {
    private String mMethod = "GET";
    private Object mHeaders;
    private String mHost = "";
    private String mApi = "";
    private String mParams = "";
    private int mResponseType = 0;// 1: 字符串, default: InputStream
    private HttpURLConnection urlConnection;

    protected HttpRequester(String scriptContent) throws Exception {
        JSONObject scriptJs = new JSONObject(scriptContent);
        mMethod = ((String) setParam(scriptJs, "method")).toUpperCase();
        mResponseType = (int) setParam(scriptJs, "response_type");
        mHost = (String) setParam(scriptJs, "host");
        mApi = (String) setParam(scriptJs, "api");
        mHeaders = setParam(scriptJs, "headers");
        mParams = setParam(scriptJs);
    }

    private Object setParam(JSONObject scriptJs, String key) {
        try {
            Object value = scriptJs.get(key);
            if (value instanceof String) {
                String param = String.valueOf(value).trim();
                if (param.contains("{{") && param.contains("}}")) {
                    int start = param.indexOf("{{");
                    int end = param.indexOf("}}");
                    value = param.substring(0, start).trim() + MainFrame.configJs
                            .get(param.substring(start + 2, end)).toString().trim()
                            + param.substring(end + 2).trim();
                }
            }
            return value;
        } catch (JSONException e) {
            return null;
        }
    }

    private String setParam(JSONObject scriptJs) {
        Object body = scriptJs.get("body");
        if (body instanceof JSONObject) {
            JSONObject bodyJs = (JSONObject) body;
            Iterator<String> keys = bodyJs.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = setParam(bodyJs, key);
                bodyJs.put(key, value);
            }
            return "p="
                    + ParamEncodeAndDecode.encode(bodyJs, (int) setParam(scriptJs, "request_type"));
        }
        return String.valueOf(body);
    }

    private void setHeaders() {
        if (mHeaders != null && mHeaders instanceof JSONObject) {
            JSONObject headers = (JSONObject) mHeaders;
            if (headers.length() > 0) {
                Iterator<String> keys = headers.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    urlConnection.setRequestProperty(key, headers.getString(key));
                }
            }
        }
    }

    protected String getUrl() {
        String fullUrl = mHost + mApi;
        if (mMethod.equals("GET")) {
            fullUrl += "?" + mParams;
        }
        return fullUrl;
    }

    protected String getParams() {
        return mParams;
    }

    protected boolean isGet() {
        return mMethod.equals("GET") ? true : false;
    }

    public class ResponseResult {
        public int statusCode = 0;
        public String responseMessage = "";
        public Object responseBody;
    }

    protected ResponseResult exec() {
        ResponseResult responseResult = new ResponseResult();
        try {
            urlConnection = (HttpURLConnection) new URL(getUrl()).openConnection();
            switch (mMethod) {
            case "GET":
                urlConnection.connect();
                break;
            case "POST":
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod(mMethod);
                setHeaders();
                urlConnection.getOutputStream().write(mParams.getBytes());
                break;
            default:
                System.err.println("请求方法不支持");
            }
            setReponseResult(responseResult, urlConnection, mResponseType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseResult;
    }

    private void setReponseResult(ResponseResult responseResult, HttpURLConnection urlConnection,
            int responseType) throws IOException {
        responseResult.statusCode = urlConnection.getResponseCode();
        responseResult.responseMessage = urlConnection.getResponseMessage();
        InputStream inputStream = null;
        if (responseResult.statusCode == HttpURLConnection.HTTP_OK) {
            inputStream = urlConnection.getInputStream();
        } else {
            inputStream = urlConnection.getErrorStream();
        }
        switch (responseType) {
        case 1:
            responseResult.responseBody = inputStream2String(inputStream);
            break;

        default:
            responseResult.responseBody = inputStream;
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
}
