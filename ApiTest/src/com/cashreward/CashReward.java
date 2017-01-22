package com.cashreward;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.apitest.JsonOperation;
import com.apitest.Const;

public class CashReward {
    public static String getParams(JSONObject project, JSONObject config)
            throws JSONException, Exception {
        JSONObject params = JsonOperation.getJSONObject(project, Const.SCRIPT_PARAMS, config);
        if (params == null || params.length() == 0) {
            return "";
        }
        return getCashrewardEncode(params, project.getInt(Const.SCRIPT_REQUEST_TYPE), config);
    }

    public static byte[] getBody(JSONObject project, JSONObject config) throws Exception {
        JSONObject body = JsonOperation.getJSONObject(project, Const.SCRIPT_BODY, config);
        if (body != null) {
            int type = Integer
                    .valueOf(JsonOperation.getString(project, Const.SCRIPT_REQUEST_TYPE, config));
            return getCashrewardEncode(body, type, config).getBytes();
        }
        return null;
    }

    private static String getCashrewardEncode(JSONObject params, int type, JSONObject config)
            throws Exception {
        Iterator<String> paramKeys = params.keys();
        while (paramKeys.hasNext()) {
            String paramKey = paramKeys.next();
            params.put(paramKey, JsonOperation.getString(params, paramKey, config));
        }
        return "p=" + ParamEncodeAndDecode.encode(params, type);
    }
}
