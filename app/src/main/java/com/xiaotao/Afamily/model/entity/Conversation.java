package com.xiaotao.Afamily.model.entity;

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
 * @date 2016-04-15  13:38
 */
public class Conversation {

    private String taskName = "";
    private String accountName = "";
    private String conversationMessage = "";
    private String conversationTime = "";
    private int messageCount = 0;

    public Conversation(){}
    public Conversation(String name){
        taskName = name;
    }
    public Conversation(String name, String who, String message, String time, int count){
        taskName = name;
        accountName = who + ": ";
        conversationMessage = message;
        conversationTime = time;
        messageCount = count;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getConversationMessage() {
        return conversationMessage;
    }

    public void setConversationMessage(String conversationMessage) {
        this.conversationMessage = conversationMessage;
    }

    public String getConversationTime() {
        return conversationTime;
    }

    public void setConversationTime(String conversationTime) {
        this.conversationTime = conversationTime;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void addCount() {
        this.messageCount ++;
    }

}
