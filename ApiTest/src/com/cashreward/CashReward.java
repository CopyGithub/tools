package com.cashreward;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.apitest.JsonOperation;
import com.apitest.Const;

public class CashReward {
    public static String getParams(JSONObject params, JSONObject project, JSONObject config)
            throws JSONException, Exception {
        if (params == null || params.length() == 0) {
            return "";
        }
        return "?" + getCashrewardEncode(params, project.getInt(Const.SCRIPT_REQUEST_TYPE), config);
    }

    public static byte[] getBody(String body, JSONObject project, JSONObject config)
            throws Exception {
        if (body == null) {
            return null;
        } else {
            try {
                JSONObject bodyJS = new JSONObject(body);
                int type = Integer.valueOf(
                        JsonOperation.getString(project, Const.SCRIPT_REQUEST_TYPE, config));
                return getCashrewardEncode(bodyJS, type, config).getBytes();
            } catch (JSONException e) {
                return null;
            }
        }
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
