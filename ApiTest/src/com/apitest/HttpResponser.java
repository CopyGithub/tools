package com.apitest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class HttpResponser {
    private Map<String, List<String>> mHeaders;
    private Object mResponseContent;

    protected enum ResponseType {
        STRING
    }

    protected HttpResponser(HttpURLConnection connection) throws IOException {
        mHeaders = connection.getHeaderFields();
        InputStream inputStream = null;
        try {
            inputStream = connection.getInputStream();
        } catch (Exception e) {
            inputStream = connection.getErrorStream();
        }
        // TODO 从responser中获取响应类型
        mResponseContent = inputStream2String(inputStream);
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

    protected String getFormatResponse() {
        String content = "";
        Set<String> headers = mHeaders.keySet();
        for (String header : headers) {
            content += (header == null ? "" : header + " : ") + mHeaders.get(header).toString()
                    + "\n";
        }
        content += JsonOperation.sortJs(mResponseContent.toString());
        return content;
    }
}
