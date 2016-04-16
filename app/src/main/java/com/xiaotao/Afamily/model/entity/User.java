package com.xiaotao.Afamily.model.entity;

import android.graphics.Bitmap;

import com.xiaotao.Afamily.utils.AppUtil;
import com.xiaotao.Afamily.utils.ChangeUtil;

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
 * @date 2016-03-01  19:47
 */
public class User {

    private String stuId = null;
    private String password = null;
    // TODO: 2016/3/1 将头像的String改为Bitmap，在函数中转换，变量只存bitmap
    private Bitmap portrait = null;
    private String userName = null;
    private int sex = 0;
    private String grade = null;
    private String classes = null;

    public User(){ }

    //  JSON解析
    public User(JSONObject jsonObject) {
        try {
            this.stuId = jsonObject.getString(AppUtil.user.account);
            this.portrait = ChangeUtil.toBitmap(jsonObject.getString(AppUtil.user.password));
            this.userName = jsonObject.getString(AppUtil.user.password);
            this.sex = jsonObject.getInt(AppUtil.user.password);
            this.grade = jsonObject.getString(AppUtil.user.password);
            this.classes = jsonObject.getString(AppUtil.user.password);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getPortrait() {
        return portrait;
    }

    public void setPortrait(Bitmap portrait) {
        this.portrait = portrait;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
