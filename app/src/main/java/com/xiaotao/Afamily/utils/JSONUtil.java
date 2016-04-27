package com.xiaotao.Afamily.utils;

import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.model.entity.Chat;
import com.xiaotao.Afamily.model.entity.User;

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

    //  签到
    public static JSONObject check(/*String flag*/) {
        //  创建 JSONObject 对象
        JSONObject checkJSON = new JSONObject();
        try {
            checkJSON.put(AppUtil.socket.type, AppUtil.socket.check);
        //    checkJSON.put(AppUtil.check.checkType, flag);
            checkJSON.put(AppUtil.user.account, BaseApplication.getInstance().getUser().getStuId());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return checkJSON;
    }

    //  login
    public static JSONObject login(User user){
        JSONObject loginJSON = new JSONObject();
        try {
            loginJSON.put(AppUtil.socket.type, AppUtil.socket.login);
            loginJSON.put(AppUtil.user.account, user.getStuId());
            loginJSON.put(AppUtil.user.password, user.getPassword());
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
            loginJSON.put(AppUtil.user.account, user.getStuId());
            loginJSON.put(AppUtil.user.password, user.getPassword());
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

    //  获取任务列表
    public static JSONObject getAllTaskInfoList(){
        JSONObject logoutJSON = new JSONObject();
        try {
            logoutJSON.put(AppUtil.socket.type,AppUtil.socket.taskList);
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(logoutJSON);
        return logoutJSON;
    }

    //  获取任务完成情况
    public static JSONObject getAllStudentTaskList(){
        JSONObject logoutJSON = new JSONObject();
        try {
            logoutJSON.put(AppUtil.socket.type, AppUtil.socket.studentTaskList);
            logoutJSON.put(AppUtil.studentTask.account, BaseApplication.getInstance().getUser().getStuId());
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(logoutJSON);
        return logoutJSON;
    }

    //  完成任务
    public static JSONObject submitTask(int taskId) {
        JSONObject logoutJSON = new JSONObject();
        try {
            logoutJSON.put(AppUtil.socket.type, AppUtil.socket.submitTask);
            logoutJSON.put(AppUtil.studentTask.taskId, taskId);
            logoutJSON.put(AppUtil.studentTask.account, BaseApplication.getInstance().getUser().getStuId());
        }catch (JSONException e){
            e.printStackTrace();
        }
        System.out.println(logoutJSON);
        return logoutJSON;
    }

    //  发送消息
    public static JSONObject sendConversationMessage(Chat chat) {
        JSONObject conversationJSON = new JSONObject();
        try {
            conversationJSON.put(AppUtil.socket.type, AppUtil.socket.sendConversationMessage);
            conversationJSON.put(AppUtil.conversation.taskId, chat.getType());
            conversationJSON.put(AppUtil.conversation.who, chat.getName());
            conversationJSON.put(AppUtil.conversation.account, chat.getAccount());
            conversationJSON.put(AppUtil.conversation.potrait, ChangeUtil.toBinary(chat.getPortrait()));
            conversationJSON.put(AppUtil.conversation.mesaage, chat.getMessage());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return conversationJSON;
    }

    //  意见反馈
    public static JSONObject feedback(String title, String body) {
        JSONObject feedbackJSON = new JSONObject();
        try {
            feedbackJSON.put(AppUtil.socket.type, AppUtil.socket.feedback);
            feedbackJSON.put(AppUtil.feedback.who, BaseApplication.getInstance().getUser().getUserName());
            feedbackJSON.put(AppUtil.feedback.title, title);
            feedbackJSON.put(AppUtil.feedback.body, body);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return feedbackJSON;
    }

    //  修改个人信息
    public static JSONObject updateUserInfo(int type, String message) {
        JSONObject feedbackJSON = new JSONObject();
        try {
            feedbackJSON.put(AppUtil.socket.type, AppUtil.socket.updateUserInfo);
            feedbackJSON.put(AppUtil.user.account, BaseApplication.getInstance().getUser().getStuId());
            feedbackJSON.put(AppUtil.updateUserInfo.updateType, type);
            feedbackJSON.put(AppUtil.updateUserInfo.updateBody, message);
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return feedbackJSON;
    }
}
