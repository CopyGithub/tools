package com.apitest;

import org.json.JSONObject;

import com.cashreward.CashReward;
import com.cashreward.ParamEncodeAndDecode;
import com.cc.http.HttpRequester;
import com.cc.json.Json;

public class ApiRequester extends HttpRequester {
    public ApiRequester(JSONObject script, JSONObject config) throws Exception {
        super(script, config);
    }

    public enum ProjectName {
        CASHREWARD, LUCKYSHOP
    }

    @Override
    public void initProjectParams(JSONObject script, JSONObject config) throws Exception {
        JSONObject project = Json.getJSONObject(script, "project");
        if (project == null) {
            return;
        }
        String projectName = Json.getString(config, "project_name");
        if (ProjectName.CASHREWARD.toString().equalsIgnoreCase(projectName)) {
            ParamEncodeAndDecode.key = Json.getString(config, "key");
            mParams = paramsMerger(mParams, CashReward.getParams(project));
            mBody = bodyMerger(mBody, CashReward.getBody(project));
        } else if (ProjectName.LUCKYSHOP.toString().equalsIgnoreCase(projectName)) {
            mBody = bodyMerger(mBody, Json.getJSONObject(project, "body").toString().getBytes());
        } else {
            throw new Exception("不支持项目名为【" + projectName + "】的项目");
        }
    }
}
