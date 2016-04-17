package com.xiaotao.Afamily.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.model.entity.Conversation;
import com.xiaotao.Afamily.model.entity.StudentTask;

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
 * @date 2016-04-17  13:00
 */
public class StudentTaskAdapter extends BaseAdapter {

    private final int[] mTo = {
            R.id.taskName, R.id.isFinish
    };

    private int mResource;
    private LayoutInflater mInflater;

    private ViewHolder holder;

    private ArrayList<StudentTask> mData;

    public StudentTaskAdapter(Context context, ArrayList<StudentTask> list) {
        this.mData = list;
        this.mResource = R.layout.check_task_item;
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
            holder.taskName = (TextView) view.findViewById(mTo[0]);
            holder.isFinish = (ImageView) view.findViewById(mTo[1]);
            view.setTag(holder);
        }
        bindView(position);

        return view;
    }

    private void bindView(int position) {
        StudentTask itemData = mData.get(position);
        if (itemData != null) {
            String name = itemData.getTaskName();
            boolean finish = itemData.isFinish();

            holder.taskName.setText(name);
            if (finish) {
                holder.isFinish.setImageResource(R.color.green);
            } else {
                holder.isFinish.setImageResource(R.color.grey);
            }
        }
    }

    private final class ViewHolder {
        TextView taskName;
        ImageView isFinish;
    }
}
