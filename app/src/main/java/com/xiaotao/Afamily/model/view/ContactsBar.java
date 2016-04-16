package com.xiaotao.Afamily.model.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
 * @date 2016-04-11  16:25
 */
public class ContactsBar extends View {

    //  索引
    private final String[] index = {"#","A","B","C","D","E","F","G","H","I","J","K","L"
            ,"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    //  提醒框
    private TextView alert = null;
    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener = null;
    private int choose = -1;
    private Paint paint = new Paint();
    private boolean showBg = false;

    public ContactsBar(Context context) {
        super(context);
    }

    public ContactsBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactsBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAlert(TextView mTextDialog) {
        this.alert = mTextDialog;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(showBg){
            canvas.drawColor(Color.parseColor("#40000000"));
        }

        // 获取焦点改变背景颜色.
        int height = getHeight();   // 获取对应高度
        int width = getWidth(); // 获取对应宽度
        // 获取每一个字母的高度
        int singleHeight = height / index.length;

        for(int i = 0; i < index.length; i ++){
            paint.setColor(Color.WHITE);
            paint.setTextSize(20);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            // 选中的状态
            if(i == choose){
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            // x坐标等于中间-字符串宽度的一半.
            float xPos = width/2  - paint.measureText(index[i])/2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(index[i], xPos, yPos, paint);
            // 重置画笔
            paint.reset();
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) (y/getHeight() * index.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                showBg = true;
                if(oldChoose != c && listener != null){
                    if(c > 0 && c < index.length){
                        alert.setText(index[c]);
                        alert.setVisibility(VISIBLE);
                        listener.onTouchingLetterChanged(index[c]);
                        choose = c;
                        invalidate();
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if(oldChoose != c && listener != null){
                    if(c > 0 && c < index.length){
                        if (alert.getVisibility() == GONE){
                            alert.setVisibility(VISIBLE);
                        }
                        alert.setText(index[c]);
                        listener.onTouchingLetterChanged(index[c]);
                        choose = c;
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                alert.setVisibility(GONE);
                showBg = false;
                choose = -1;
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener{
        void onTouchingLetterChanged(String s);
    }

}
