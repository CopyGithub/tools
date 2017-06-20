package com.apitest;

import org.json.JSONObject;

import com.cashreward.CashReward;
import com.cashreward.ParamEncodeAndDecode;
import com.cc.common.JavaCommon;
import com.cc.http.HttpRequester;
import com.cc.json.Json;

public class ApiRequester extends HttpRequester {
    private HttpRequester requester = null;

    private enum ProjectName {
        CASHREWARD, LUCKYSHOP
    }

    public void initRequester(JSONObject script, JSONObject config) throws Exception {
        script = JavaCommon.replaceScriptParam(script, config);
        requester = new HttpRequester(script);
        JSONObject project = Json.getJSONObject(script, "project");
        if (project == null) {
            return;
        }
        String projectName = Json.getString(config, "project_name").toUpperCase();
        if (ProjectName.CASHREWARD.toString().equals(projectName)) {
            ParamEncodeAndDecode.key = Json.getString(config, "key");
            requester.addParam(CashReward.getParams(project));
            requester.addBody(CashReward.getBody(project));
        } else if (ProjectName.LUCKYSHOP.toString().equals(projectName)) {
            requester.addBody(Json.getJSONObject(project, "body").toString().getBytes());
        } else {
            throw new Exception("不支持项目名为【" + projectName + "】的项目");
        }
    }
}
