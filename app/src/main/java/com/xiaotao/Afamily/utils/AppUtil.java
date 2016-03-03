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

    //  Socket Connect
    public static final class net {
        public static final int port = 30000;
        public static final String IP = "192.168.1.108";
        public static final String tip = "消息类型错误！";
    }

    //  Connect Type
    public static final class socket {
        public static final String type = "CONNECT_TYPE";
        public static final String connectCheck = "CONNECT_CHECK";
        public static final String checkMSG = "###心跳检测###";
        public static final int test = -2;
        public static final int check = -1;
        public static final int notify = 0;
        public static final int login = 1;
    }

    //  login
    public static final class login {
        public static final String account = "LOGIN_ACCOUNT";
        public static final String password = "LOGIN_PASSWORD";
    }

    //  TAG
    public static final class tag {
        public static final String activity = "tag_activity";
        public static final String network = "tag_network";
        public static final String error = "tag_error";
    }

    //  BroadcastReceiver
    public static final class broadcast {
        public static final String service_client = "com.xiaotao.action.SERVICE_CLIENT";
        public static final String login = "com.xiaotao.action.LOGIN";
        public static final String test = "com.xiaotao.action.TEST";
    }

    //  Broadcast Message
    public static final class message {
        public static final String sendMessage = "SERVICE_MSG";
        public static final String login = "RECEIVE_LOGIN_MSG";
        public static final String test = "TEST_MSG";
    }

    public static final class sp {
        public static final String file_name = "spFile";
        public static final String flagLogin = "loginFlag";
    }
}
