package com.example.chatgptapp.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatgptapp.Model.Msg;
import com.example.chatgptapp.R;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        //左对话框
        LinearLayout leftLayout;
        TextView leftMsg;

        //右对话框
        LinearLayout rightLayout;
        TextView rightMsg;

        public ViewHolder(View view) {
            super(view);

            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);

            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
         }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Msg msg = mMsgList.get(position);

        if(msg.getType() == Msg.TYPE_RECEIVED){
            //如果是收到消息,则显示左气泡
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }
        else if(msg.getType() == Msg.TYPE_SENT){
            //如果是发送消息,则显示右气泡
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
