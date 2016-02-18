package com.xiaotao.acmerhome.util;

import com.xiaotao.acmerhome.test.Entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 *
 * @author xiaoTao
 * @date 2016-02-18  13:05
 */
public class JSONUtil{

    //  心跳检测
    public static JSONObject connectCheck() {
        //  创建 JSONObject 对象
        JSONObject testJSON = new JSONObject();
        try {
            testJSON.put(AppUtil.connectType.type, AppUtil.connectType.check);
            testJSON.put(AppUtil.connectType.connectCheck, AppUtil.connectType.checkMSG);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return testJSON;
    }
}
