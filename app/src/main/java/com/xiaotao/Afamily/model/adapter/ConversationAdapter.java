package com.xiaotao.Afamily.model.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.model.entity.Conversation;

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
 * @date 2016-04-15  13:41
 */
public class ConversationAdapter extends BaseAdapter {

    private final int[] mTo = {
            R.id.taskName, R.id.conversationTime, R.id.conversationMessage, R.id.count
    };

    private int mResource;
    private LayoutInflater mInflater;

    private ViewHolder holder;

    private ArrayList<Conversation> mData;

    public ConversationAdapter(Context context, ArrayList<Conversation> list) {
        this.mData = list;
        this.mResource = R.layout.conversation_item;
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
            holder.conversationTime = (TextView) view.findViewById(mTo[1]);
            holder.conversationMessage = (TextView) view.findViewById(mTo[2]);
            holder.count = (TextView) view.findViewById(mTo[3]);
            view.setTag(holder);
        }
        bindView(position);

        return view;
    }

    private void bindView(int position) {
        Conversation itemData = mData.get(position);
        if (itemData != null) {
            String name = itemData.getTaskName();
            String who = itemData.getAccountName();
            String message = itemData.getConversationMessage();
            String time = itemData.getConversationTime();
            int count = itemData.getMessageCount();

            holder.taskName.setText(name);
            holder.conversationMessage.setText(who + message);
            holder.conversationTime.setText(time);
            if (count <= 0) {
                holder.count.setVisibility(View.GONE);
            } else {
                holder.count.setText(String.valueOf(count));
                holder.count.setVisibility(View.VISIBLE);
            }
        }
    }

    private final class ViewHolder {
        TextView taskName;
        TextView conversationMessage;
        TextView conversationTime;
        TextView count;
    }
}
