package com.xiaotao.Afamily.model.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaotao.Afamily.R;
import com.xiaotao.Afamily.base.BaseApplication;
import com.xiaotao.Afamily.model.entity.Chat;
import com.xiaotao.Afamily.utils.AppUtil;

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
 * @date 2016-04-18  9:31
 */
public class ChatAdapter extends BaseAdapter {

    private final int[] mTo = {
            R.id.chatPortait, R.id.chatName, R.id.chatMessage
    };

    private Context mContext;
    private LayoutInflater mInflater;

    private ViewHolder holder;

    private ArrayList<Chat> mData;

    public ChatAdapter(Context context, ArrayList<Chat> list ) {
        this.mData = list;
        this.mContext = context;
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
        return createViewFromResource(position, null);
    }

    private View createViewFromResource(int position, View convertView) {

        String account = mData.get(position).getAccount();
        if (BaseApplication.getInstance().getUser().getStuId().equals(account)) {
            System.out.println("A");
            convertView = mInflater.inflate(R.layout.chatto_item, null);
        } else {
            System.out.println("B");
            convertView = mInflater.inflate(R.layout.chafrom_item, null);
        }
        if (position == 0) {
            convertView.setVisibility(View.INVISIBLE);
            return convertView;
        }
        holder = new ViewHolder();
        holder.chatPortait = (ImageView) convertView.findViewById(mTo[0]);
        holder.chatName = (TextView) convertView.findViewById(mTo[1]);
        holder.chatMessage = (TextView) convertView.findViewById(mTo[2]);

        bindView(position);

        return convertView;
    }

    private void bindView(int position) {
        Chat itemData = mData.get(position);
        if (itemData != null) {
            String name = itemData.getName();
            Bitmap portrait = itemData.getPortrait();
            String message = itemData.getMessage();

            holder.chatName.setText(name);
            holder.chatPortait.setBackground(
                    new BitmapDrawable(mContext.getResources(), portrait)
            );
            holder.chatMessage.setText(message);
        }
    }

    private final class ViewHolder {
        ImageView chatPortait;
        TextView chatName;
        TextView chatMessage;
    }

}
