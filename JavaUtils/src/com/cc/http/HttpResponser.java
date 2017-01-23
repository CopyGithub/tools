package com.cc.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cc.json.Json;

public class HttpResponser {
    private Map<String, List<String>> mHeaders;
    private Object mResponseContent;

    /**
     * 获取请求结果
     * 
     * @param connection
     * @throws IOException
     */
    public HttpResponser(HttpURLConnection connection) throws IOException {
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

    /**
     * 将{@link InputStream}的结果转化为{@link String}
     * 
     * @param inputStream
     * @return
     * @throws IOException
     */
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

    /**
     * 输出格式化过后的返回</br>
     * 主要格式话header和Json内容
     * 
     * @return
     */
    public String getFormatResponse() {
        String content = "";
        Set<String> headers = mHeaders.keySet();
        for (String header : headers) {
            content += (header == null ? "" : header + " : ") + mHeaders.get(header).toString()
                    + "\n";
        }
        for (int i = 0; i < 80; i++) {
            content += "-";
        }
        content += "\n";
        content += Json.sortJs(mResponseContent.toString());
        return content;
    }
}
