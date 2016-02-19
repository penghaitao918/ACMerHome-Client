package com.xiaotao.acmfamily.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xiaotao.acmfamily.util.AppUtil;

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

/*    //  结束指定Activity
    public void finish(Context context){
        for (Activity activity : activityList) {
            if (activity.equals(context)) {
                activity.finish();
                Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> was finished.");
            }
        }
    }*/

    //  打印所有的Activity
    public void display(){
        for (Activity activity : activityList) {
            Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> was display.");
        }
    }

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
            Log.i(AppUtil.tag.activity, activity.getClass().getSimpleName() + "---> was finished.");
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}

