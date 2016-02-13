package com.xiaotao.acmerhome.network;

import java.io.OutputStream;

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
 * @date 2016-02-13  22:10
 */
public class ClientSend {

    private OutputStream os = null;
    public ClientSend(OutputStream os) {
        this.os = os;
    }

    //  设置同步方法，任意时刻有且只能有一个发送程序
    public synchronized void put(String msg) {
        try {
            os.write((msg + "\r\n").getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
