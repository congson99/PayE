package com.example.paye.adapter;

import android.content.Context;
import android.service.autofill.UserData;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paye.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<UserAdapter> mUser;

    public UserAdapter(Context mContext, List<UserAdapter> mUser){
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_chatbox, parent);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserAdapter user = mUser.get(position);
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.chatBoxItem_username);
            avatar = itemView.findViewById(R.id.chatBoxItem_avatar);
        }
    }

}
