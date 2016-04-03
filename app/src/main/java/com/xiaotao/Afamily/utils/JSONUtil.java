package com.xiaotao.Afamily.utils;

import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.test.TestEntity;

import org.json.JSONException;
import org.json.JSONObject;


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
        JSONObject checkJSON = new JSONObject();
        try {
            checkJSON.put(AppUtil.socket.type, AppUtil.socket.check);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return checkJSON;
    }

    //  test
    public static JSONObject test(TestEntity testEntity) {
        //  创建 JSONObject 对象
        JSONObject testJSON = new JSONObject();
        try {
            testJSON.put(AppUtil.socket.type, AppUtil.socket.test);
            testJSON.put("MSG", testEntity.getMsg());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return testJSON;
    }

    //  login
    public static JSONObject login(User user){
        JSONObject loginJSON = new JSONObject();
        try {
            loginJSON.put(AppUtil.socket.type, AppUtil.socket.login);
            loginJSON.put(AppUtil.login.account, user.getStuId());
            loginJSON.put(AppUtil.login.password, user.getPassword());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return loginJSON;
    }

    //  reLogin
    public static JSONObject reLogin(User user){
        JSONObject loginJSON = new JSONObject();
        try {
            loginJSON.put(AppUtil.socket.type, AppUtil.socket.reLogin);
            loginJSON.put(AppUtil.login.account, user.getStuId());
            loginJSON.put(AppUtil.login.password, user.getPassword());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return loginJSON;
    }

    //  logout
    public static JSONObject logout(){
        JSONObject logoutJSON = new JSONObject();
        try {
            logoutJSON.put(AppUtil.socket.type,AppUtil.socket.logout);
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(logoutJSON);
        return logoutJSON;
    }
}
