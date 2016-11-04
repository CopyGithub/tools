package com.cc.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.params.convert.ParamEncodeAndDecode;

public class HttpRequester {
    private String mMethod = "GET";
    private String mHost = "";
    private String mApi = "";
    private String mParam = "";
    private int mResponseType = 0;// 1: 字符串, default: InputStream

    public HttpRequester(String scriptContent) {
        JSONObject scriptJs = new JSONObject(scriptContent);
        mMethod = ((String) setParam(scriptJs, "method")).toUpperCase();
        mResponseType = (int) setParam(scriptJs, "response_type");
        mHost = (String) setParam(scriptJs, "host");
        mApi = (String) setParam(scriptJs, "api");
        mParam = getParam(scriptJs);
    }

    private Object setParam(JSONObject scriptJs, String key) throws JSONException {
        Object value = scriptJs.get(key);
        if (value instanceof String) {
            String param = String.valueOf(value);
            if (param.contains("{{") && param.contains("}}")) {
                int start = param.indexOf("{{");
                int end = param.indexOf("}}");
                value = param.substring(0, start).trim()
                        + MainFrame.configJs.get(param.substring(start + 2, end)).toString().trim()
                        + param.substring(end + 2).trim();
                System.out.println(value);
            }
        }
        return value;
    }

    private String getParam(JSONObject scriptJs) {
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

    public String getUrl() throws UnsupportedEncodingException {
        String fullUrl = mHost + mApi;
        if (mMethod.equals("GET")) {
            fullUrl += "?" + mParam;
        }
        return fullUrl;
    }

    public class ResponseResult {
        public int statusCode = 0;
        public String responseMessage = "";
        public Object responseBody;
    }

    public ResponseResult exec() {
        ResponseResult responseResult = new ResponseResult();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(getUrl()).openConnection();
            switch (mMethod) {
            case "GET":
                urlConnection.connect();
                break;
            case "POST":
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod(mMethod);
                urlConnection.getOutputStream().write(mParam.getBytes());
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
