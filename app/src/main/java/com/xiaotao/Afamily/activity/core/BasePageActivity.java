package com.xiaotao.Afamily.activity.core;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseActivity;
import com.xiaotao.Afamily.model.adapter.FramePagerAdapter;

import java.util.ArrayList;
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
 * @date 2016-03-04  22:49
 */
public class BasePageActivity extends BaseActivity implements OnPageChangeListener {

    private ViewPager pager = null;
    private LocalActivityManager manager = null;

    private TextView textTab = null;

    private ImageButton taskPageButton = null;
    private ImageButton homePageButton = null;
    private ImageButton userPageButton = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData(savedInstanceState);
        initView();
        initViewPage();
    }

    protected void onStart() {
        super.onStart();
        System.out.println("### MainPage --> onStart");
    }

    protected void onRestart() {
        super.onRestart();
        System.out.println("### MainPage --> onRestart");
    }

    protected void onResume() {
        super.onResume();
        System.out.println("### MainPage --> onResume");
    }

    protected void onPause() {
        super.onPause();
        System.out.println("### MainPage --> onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("### MainPage --> onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("### MainPage --> onDestroy");
    }

    private void initData(Bundle savedInstanceState) {
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
    }

    private void initView() {
        this.textTab = (TextView) findViewById(R.id.textTab);
        this.taskPageButton = (ImageButton) findViewById(R.id.TaskPage);
        this.homePageButton = (ImageButton) findViewById(R.id.HomePage);
        this.userPageButton = (ImageButton) findViewById(R.id.UserPage);
    }

    private void initViewPage() {
        List<View> mListViews = new ArrayList<View>();
        pager = (ViewPager) findViewById(R.id.viewpager_user_main);
        Intent intentA = new Intent(BasePageActivity.this, ConversationListPageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("A", intentA));
        Intent intentB = new Intent(BasePageActivity.this, HomePageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("B", intentB));
        Intent intentC = new Intent(BasePageActivity.this, UserPageActivity.class); // 加载activity到viewpage
        mListViews.add(getView("C", intentC));

        pager.setAdapter(new FramePagerAdapter(mListViews));
        pager.setCurrentItem(1);
        pager.setOnPageChangeListener(this);
    }

    public void PageOnClick(View v) {
        switch (v.getId()) {
            case R.id.TaskPage:
                pager.setCurrentItem(0);
                this.textTab.setText("讨论");
                break;
            case R.id.HomePage:
                pager.setCurrentItem(1);
                this.textTab.setText("菜单");
                break;
            case R.id.UserPage:
                pager.setCurrentItem(2);
                this.textTab.setText("我");
                break;
            default:
                break;
        }
    }

    /**
     * 加载activity
     */
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }

    /*-------------------page----------------------------------------------------------------------------------------------------------*/
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        switch (arg0) {
            case 0:
                pager.setCurrentItem(0);
                this.textTab.setText("讨论");
                break;
            case 1:
                pager.setCurrentItem(1);
                this.textTab.setText("菜单");
                break;
            case 2:
                pager.setCurrentItem(2);
                this.textTab.setText("我");
                break;
            default:
                break;
        }
    }

}

