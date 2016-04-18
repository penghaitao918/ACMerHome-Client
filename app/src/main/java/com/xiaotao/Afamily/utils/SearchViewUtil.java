package com.xiaotao.Afamily.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.xiaotao.Afamily.R;

import java.lang.reflect.Field;

import static com.xiaotao.Afamily.R.drawable.ic_launcher;
import static com.xiaotao.Afamily.R.drawable.search_close_btn;
import static com.xiaotao.Afamily.R.drawable.shape_search_cursor_res;

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
 * @date 2016-04-12  12:14
 */
public class SearchViewUtil {

    private Class<?> argClass = null;
    private Context mContext = null;
    private SearchView mSearchView = null;

    public SearchViewUtil(Context context, SearchView searchView) {
        this.mContext = context;
        this.mSearchView = searchView;
        //  拿到字节码
        this.argClass = mSearchView.getClass();
    }

    //  修改背景图片
    public void setBackground(int drawableRes) {
        try {
            //  指定某个私有属性,mSearchPlate是搜索框父布局的名字
            //  注意mSearchPlate的背景是stateListDrawable(不同状态不同的图片)
            //  所以不能用BitmapDrawable
            Field ownField = argClass.getDeclaredField("mSearchPlate");
            //  暴力反射,只有暴力反射才能拿到私有属性
            //  setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            ownField.setAccessible(true);
            //  设置背景
            View mView = (View) ownField.get(mSearchView);
            mView.setBackground(ContextCompat.getDrawable(mContext, drawableRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //  修改光标颜色
    public void setCursorDrawableRes(int drawableRes){
        try {
            //指定某个私有属性
            Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
            //  暴力反射,只有暴力反射才能拿到私有属性
            mQueryTextView.setAccessible(true);
            //mCursorDrawableRes光标图片Id的属性 这个属性是TextView的属性，所以要用mQueryTextView（SearchAutoComplete）
            //的父类（AutoCompleteTextView）的父  类( EditText）的父类(TextView)
            Class<?> mTextViewClass = mQueryTextView.get(mSearchView).getClass().getSuperclass().getSuperclass().getSuperclass();
            Field mCursorDrawableRes = mTextViewClass.getDeclaredField("mCursorDrawableRes");
            //setAccessible 它是用来设置是否有权限访问反射类中的私有属性的，只有设置为true时才可以访问，默认为false
            mCursorDrawableRes.setAccessible(true);
            //注意第一个参数持有这个属性(mQueryTextView)的对象(mSearchView) 光标必须是一张图片不能是颜色，因为光标有两张图片，
            //一张是第一次获得焦点的时候的闪烁的图片，一张是后边有内容时候的图片，如果用颜色填充的话，就会失去闪烁的那张图片，
            //颜色填充的会缩短文字和光标的距离（某些字母会背光标覆盖一部分）。
            mCursorDrawableRes.set(mQueryTextView.get(mSearchView), drawableRes);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //  设置关闭按钮
    public void setCloseButton() {
        try {
            Field mCloseButton = argClass.getDeclaredField("mCloseButton");
            mCloseButton.setAccessible(true);
            ImageView backView = (ImageView) mCloseButton.get(mSearchView);
            backView.setBackground(null);
            backView.setImageResource(search_close_btn);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //  设置字体颜色
    public void setTextColor(int resources) {
        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(resources);
    }
}
