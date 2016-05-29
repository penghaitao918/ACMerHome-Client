package com.xiaotao.Afamily.utils;

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
 * @author littleTao
 * @date 2016-02-03  21:11
 */
public class AppUtil {

    //  本地服务
    public static final class localService {
        public static final int all = 0;
        public static final int sound = 1;
        public static final int vibrate = 2;
    }

    //  Socket Connect
    public static final class net {
        public static final int port = 30000;
    //    public static final String IP = "192.168.173.1";
        public static final String IP = "10.10.44.35";
        public static final String tip = "消息类型错误！";
    }

    //  Connect Type
    public static final class socket {
        public static final String type = "CONNECT_TYPE";

        public static final int check = 0;
        public static final int notify = 1;
        public static final int login = 2;
        public static final int reLogin = 3;
        public static final int logout =4 ;
        public static final int taskList = 5;
        public static final int studentTaskList = 6;
        public static final int submitTask = 7;
        public static final int sendConversationMessage = 8;
        public static final int feedback = 9;
        public static final int updateUserInfo = 10;
    }

    public static final class notify {
        public static final String notifyTitle = "NOTIFY_TITLE";
        public static final String notifyMessage = "NOTIFY_MESSAGE";
    }

    //  user
    public static final class user {
        public static final String loginFlag = "LOGIN_FLAG";
        public static final String account = "USER_ACCOUNT";
        public static final String password = "USER_PASSWORD";
        public static final String portrait = "USER_PORTRAIT";
        public static final String userName = "USER_NAME";
        public static final String sex = "USER_SEX";
        public static final String classes = "USER_CLASSES";
    }

    //  TAG
    public static final class tag {
        public static final String activity = "tag_activity";
        public static final String network = "tag_network";
        public static final String error = "tag_error";
    }

    //  BroadcastReceiver
    public static final class broadcast {
        public static final String network_service = "com.xiaotao.action.NETWORK_SERVICE";
        public static final String local_service = "com.xiaotao.action.LOCAL_SERVICE";

        public static final String check = "com.xiaotao.action.CHECK_IN_OUT";
        public static final String login = "com.xiaotao.action.LOGIN";
        public static final String reLogin = "com.xiaotao.action.RELOGIN";
        public static final String conversationList = "com.xiaotao.action.CONVERSATION_LIST";
        public static final String studentTaskList = "com.xiaotao.action.STUDENT_TASK_LIST";
        public static final String chat = "com.xiaotao.action.CHAT";
        public static final String feedback = "com.xiaotao.action.FEEDBACK";
        public static final String userPage = "com.xiaotao.action.USER_PAGE";
    }

    //  Broadcast Message
    public static final class message {
        public static final String sendMessage = "SERVICE_MSG";
        public static final String type = "RECEIVE_TYPE";
        public static final String login = "RECEIVE_LOGIN_MSG";
        public static final String reLogin = "RECEIVE_RELOGIN_MSG";
        public static final String taskList = "RECEIVE_TASK_LIST_MSG";
        public static final String studentTask = "RECEIVE_STUDENT_TASK_MSG";
        public static final String chatMessage = "RECEIVE_STUDENT_TASK_MSG";
        public static final String userPage = "RECEIVE_STUDENT_USER_PAGE";
    }

    //  SharedPreferences 存储
    public static final class sp {
        public static final String fileName = "spFile";
        public static final String loginFlag = "loginFlag";
        public static final String account = "userAccount";
        public static final String password = "userPassword";
        public static final String portrait = "userPortrait";
        public static final String userName = "userName";
        public static final String sex = "userSex";
        public static final String classes = "userClasses";

        public static final String localSound = "LOCAL_SOUND";
        public static final String localVibrate = "LOCAL_VIBRATE";

        public static final String signTime = "SIGN_TIME";
    }

    //  讨论组
    public static final class conversation {
        public static final String taskId = "TASK_ID";
        public static final String taskName = "TASK_NAME";
        public static final String account = "STUDENT_ACCOUNT";
        public static final String potrait = "STUDENT_PORTRAIT";
        public static final String who = "STUDENT_NAME";
        public static final String mesaage = "MESSAGE";
        public static final String time = "MESSAHE_TIME";
    }

    //  任务完成情况
    public static final class studentTask {
        public static final String taskId = "TASK_ID";
        public static final String name = "TASK_NAME";
        public static final String account = "USER_ACCOUNT";
        public static final String[] task = {
                "TASK_A","TASK_B","TASK_C","TASK_D","TASK_E","TASK_F","TASK_G","TASK_H","TASK_I","TASK_J"
        };
    }

    //  意见反馈
    public static final class feedback {
        public static final String who = "FEEDBACK_NAME";
        public static final String title = "FEEDBACK_TITLE";
        public static final String body = "FEEDBACK_BODY";
    }

    public static final class updateUserInfo {
        //  图片调用
        public static final int IMAGE_REQUEST_CODE = 0;
        public static final int RESULT_REQUEST_CODE = 1;

        public static final int updateSex = 0;
        public static final int updatePortrait = 1;
        public static final String updateType = "UPDATE_USER_INFO_TYPE";
        public static final String updateBody = "UPDATE_USER_INFO_MESSAGE";
    }

}
