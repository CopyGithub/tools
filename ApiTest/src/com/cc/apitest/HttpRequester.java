package com.cc.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.params.convert.ParamEncodeAndDecode;

public class HttpRequester {
    private String mMethod = "GET";
    private String mHost = "";
    private String mApi = "";
    private Map<String, String> mHeaders = new HashMap<>();
    private String mParam = "";
    private byte[] body = null;
    private int mResponseType = 0;// 1: 字符串, default: InputStream

    public HttpRequester(String scriptContent) {
        JSONObject jsonObject = new JSONObject(scriptContent);
        JSONObject bodyJson = jsonObject.getJSONObject("body");
        mMethod = jsonObject.getString("method").toUpperCase();
        mResponseType = jsonObject.getInt("response_type");
        mHost = jsonObject.getString("host");
        mApi = jsonObject.getString("api");
        mParam = ParamEncodeAndDecode.encode(bodyJson, jsonObject.getInt("request_type"));
    }

    public String getUrl() throws UnsupportedEncodingException {
        String fullUrl = mHost + mApi;
        if (mMethod.equals("GET")) {
            fullUrl += "?p=" + mParam;
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
                urlConnection.getOutputStream().write(("p=" + mParam).getBytes());
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = bufferedReader.readLine()) != null) {
            text += line;
        }
        return text;
    }
}
