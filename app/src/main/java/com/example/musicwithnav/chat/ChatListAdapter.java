package com.example.musicwithnav.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicwithnav.R;
import com.example.musicwithnav.models.User;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyHolder> {

    Context context;
    List<User> userList; //get user info
    private HashMap<String, String> lastMessageMap;

    public ChatListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
        lastMessageMap = new HashMap<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //inflate layout row_chatlist.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_chatlist, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //get data
        final String hisUid = userList.get(i).getUid();
        String userImage = userList.get(i).getUserProfileImageUrl();
        String userName = userList.get(i).getName();
        String lastMessage = lastMessageMap.get(hisUid);

        //set data
        myHolder.nameTv.setText(userName);
        if (lastMessage == null || lastMessage.equals("default")) {

            myHolder.lastMessageTv.setVisibility(View.GONE);
        } else {
            myHolder.lastMessageTv.setVisibility(View.VISIBLE);
            myHolder.lastMessageTv.setText(lastMessage);
        }
        try {
            Glide.with(myHolder.itemView)
                    .load(userImage)
                    .into(myHolder.profileIv);
            //Picasso.get().load(userImage).placeholder(R.drawable.ic_default_img).into(myHolder.profileIv);
        } catch (Exception e) {
            Glide.with(myHolder.itemView)
                    .load(R.drawable.gtunes_logo)
                    .into(myHolder.profileIv);
            //Picasso.get().load(R.drawable.ic_default_img).into(myHolder.profileIv);

        }
        //set online status of other users in chatlist
        if (userList.get(i).getOnlineStatus().equals("online")) {
            //online
            //myHolder.onlineStatusIv.setImageResource(R.drawable.circle_online);
        } else {
            //offline
            //myHolder.onlineStatusIv.setImageResource(R.drawable.circle_offline);
        }

        //handle click of user in chatList
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start chat activity with that user
//                Intent intent = new Intent(context, ChatActivity.class);
//                intent.putExtra("hisUid", hisUid);
//                context.startActivity(intent);
            }
        });
    }

    public void setLastMessageMap(String userId, String lastMessage) {
        lastMessageMap.put(userId, lastMessage);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        //views of row chatlist.xml
        ImageView profileIv, onlineStatusIv;
        TextView nameTv, lastMessageTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            profileIv = itemView.findViewById(R.id.profileIv);
            onlineStatusIv = itemView.findViewById(R.id.onlineStatusIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            lastMessageTv = itemView.findViewById(R.id.lastMessageTv);


        }
    }
}
