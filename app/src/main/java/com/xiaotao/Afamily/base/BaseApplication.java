package com.xiaotao.Afamily.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.xiaotao.Afamily.model.entity.User;
import com.xiaotao.Afamily.utils.AppUtil;

import java.util.LinkedList;
import java.util.List;

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
 * @date 2016-02-13  20:46
 */
public class BaseApplication extends Application {

    private static BaseApplication instance;
    private List<Activity> activityList = new LinkedList<Activity>();

    private User user = null;

    // 单例模式中获取唯一的MyApplication实例
    public static BaseApplication getInstance() {
        if (null == instance) {
            instance = new BaseApplication();
        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
        Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> is add to List.");
    }

    //  结束指定Activity
    public void finish(Context context){
        System.out.println(context);
        activityList.remove(context);
        Log.i(AppUtil.tag.activity, context.getClass().getSimpleName() + "---> was finished.");

    }

    //  打印所有的Activity
    public void display(){
        for (Activity activity : activityList) {
            Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> was display.");
        }
    }

    //  判断是否存在前台程序
    public boolean isRunning(){
        if (activityList.isEmpty()){
            return false;
        }
        return true;
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
            Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> was finished.");
        }
   //     System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = new User(user);
    }
}

