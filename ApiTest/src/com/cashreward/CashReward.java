package com.cashreward;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.cc.json.Json;

public class CashReward {
    private static final String REQUEST_TYPE = "request_type";

    public static String getParams(JSONObject project) throws JSONException, Exception {
        JSONObject params = Json.getJSONObject(project, "params");
        if (params == null) {
            return "";
        }
        return getCashrewardEncode(params, project.getInt(REQUEST_TYPE));
    }

    public static byte[] getBody(JSONObject project) throws Exception {
        JSONObject body = Json.getJSONObject(project, "body");
        if (body != null) {
            int type = Integer.valueOf(Json.getString(project, REQUEST_TYPE));
            return getCashrewardEncode(body, type).getBytes();
        }
        return null;
    }

    private static String getCashrewardEncode(JSONObject params, int type) throws Exception {
        Iterator<String> paramKeys = params.keys();
        while (paramKeys.hasNext()) {
            String paramKey = paramKeys.next();
            params.put(paramKey, Json.getString(params, paramKey));
        }
        return "p=" + ParamEncodeAndDecode.encode(params, type);
    }
}
