package com.xiaotao.Afamily.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.model.entity.Conversation;
import com.xiaotao.Afamily.model.entity.Notify;

import java.util.ArrayList;

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
 * @date 2016-04-21  14:26
 */
public class NotifyAdapter extends BaseAdapter {

    private final int[] mTo = {
            R.id.notifyTitle, R.id.notifyTime, R.id.notifyMessage
    };

    private int mResource;
    private LayoutInflater mInflater;

    private ViewHolder holder;

    private ArrayList<Notify> mData;

    public NotifyAdapter() {

    }
    public NotifyAdapter(Context context, ArrayList<Notify> list) {
        this.mData = list;
        this.mResource = R.layout.item_notify;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, mResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view;
        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            view = mInflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.mTitle = (TextView) view.findViewById(mTo[0]);
            holder.mTime = (TextView) view.findViewById(mTo[1]);
            holder.mMessage = (TextView) view.findViewById(mTo[2]);
            view.setTag(holder);
        }
        if (position == 0) {
            AbsListView.LayoutParams layoutParams = (AbsListView.LayoutParams) view.getLayoutParams();
            layoutParams.height = 70;
            view.setLayoutParams(layoutParams);
       //     view.setVisibility(View.INVISIBLE);
            return view;
        }
        view.setVisibility(View.VISIBLE);
        bindView(position);

        return view;
    }

    private void bindView(int position) {
        Notify itemData = mData.get(position);
        if (itemData != null) {
            String title = itemData.getTitle();
            String time = itemData.getTime() ;
            String message = itemData.getMessage();
            holder.mTitle.setText(title);
            holder.mTime.setText(time);
            holder.mMessage.setText("\t" + message);
        }
    }

    private final class ViewHolder {
        TextView mTitle;
        TextView mMessage;
        TextView mTime;
    }

}
