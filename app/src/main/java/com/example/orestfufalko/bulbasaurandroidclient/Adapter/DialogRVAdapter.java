package com.example.orestfufalko.bulbasaurandroidclient.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnMediaClick;
import com.example.orestfufalko.bulbasaurandroidclient.Media.AudioPlayer;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.Message;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.MediaUrlParser;
import com.example.orestfufalko.bulbasaurandroidclient.utils.TimeParseUtil;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;
import me.himanshusoni.chatmessageview.ChatMessageView;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class DialogRVAdapter extends RecyclerView.Adapter<DialogRVAdapter.DialogViewHolder> {

    private final String TAG = "DialogRVAdapterTAG";
    private final int IV_ID = 1234;
    private final String MEDIA_PATH = "http://10.0.2.2:5001"; // for as
//    private final String MEDIA_PATH = "http://10.0.3.2:5001"; //for geny
    private AllMessageResponseDTO allMessages;
    private int friendId;
    private String friendName;
    private String friendUrl;
    private String userName;
    private String userUrl;
    private String lastDate;
    private AudioPlayer audioPlayer;

    private int screenWidth;
    private int screenHeight;

    public DialogRVAdapter(AllMessageResponseDTO allMessages, int friendId, int screenWidth, int screenHeight) {
        setAllMessages(allMessages);
        this.friendId = friendId;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        audioPlayer = new AudioPlayer();

    }
    public void setAllMessages(AllMessageResponseDTO allMessages){
        this.allMessages = allMessages;
        friendName = allMessages.getFriendName();
        userName = allMessages.getCurrentUserName();
        friendUrl = MEDIA_PATH + allMessages.getFriendImageUrl();
        userUrl = MEDIA_PATH + allMessages.getCurrentUserImageUrl();
        if (allMessages.getMessages() != null && allMessages.getMessages().size() != 0)
        lastDate = TimeParseUtil.parseDate(allMessages.getMessages().get(0).getDateTime());
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_list_item, parent,false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DialogViewHolder holder, int position) {
        final Message current = allMessages.getMessages().get(position);
        clearView(holder.llFriendMessageField);
        clearView(holder.llUserMessageField);
        Log.i(TAG, "onBindViewHolder: Bindastarted" + current.getMessageId());
        Log.i(TAG, "onBindViewHolder: Bindastarted" + current.getText());
        if (current.getSenderId() == friendId){
            showFriendMessage(holder, current);
        }
        else {
            showUserMessage(holder, current);
        }

        String date = TimeParseUtil.parseDate(current.getDateTime());
        if (lastDate.compareTo(date) < 0){
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvDate.setText(date);
        }
        if (current.getAttachmentUrl() != null){
            holder.attachmentUrl = MEDIA_PATH + current.getAttachmentUrl();
        }

    }

    private void showUserMessage(final DialogViewHolder holder, Message current) {
        holder.llUserMessage.setVisibility(View.VISIBLE);
        GlideUtil.loadProfileIcon(userUrl, holder.civUserPhoto);
        holder.tvUserName.setText(userName);
        holder.tvUserMessage.setText(current.getText());
        holder.tvUserTime.setText(TimeParseUtil.parseTime(current.getDateTime()));

        if (!current.isRead()){
           // holder.llUserMessageField.setBackgroundColor(Color.GRAY);
        }

        if (current.getAttachmentUrl() != null){

            if (!isImage(current, holder.view.getContext(), holder.llUserMessageField)){
                holder.tvUserAttached.setText(current.getMediaName());
                holder.tvUserAttached.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(new OnMediaClick(holder.attachmentUrl));
                    }
                });
            }

        }
    }
    private boolean isImage(Message current, Context context, LinearLayout llHolder){
        if (MediaUrlParser.isImageFile(current.getAttachmentUrl())){
            ImageView ivPicture = new ImageView(context);
            ivPicture.setPadding(0, 10, 0 , 10);
            GlideUtil.loadImageWithWidthHeightParams(MEDIA_PATH + current.getAttachmentUrl(), ivPicture, screenWidth/2, screenHeight/4);
            llHolder.addView(ivPicture);
            return true;
        }
        return false;
    }

    private void showFriendMessage(final DialogViewHolder holder, Message current) {
        holder.llFriendMessage.setVisibility(View.VISIBLE);
        GlideUtil.loadProfileIcon(friendUrl, holder.civFriendPhoto);
        holder.tvFriendName.setText(friendName);
        holder.tvFriendMessage.setText(current.getText());
        holder.tvFriendTime.setText(TimeParseUtil.parseTime(current.getDateTime()));

        if (!current.isRead()){
           // holder.llFriendMessageField.setBackgroundColor(Color.GRAY);
        }

        if (current.getAttachmentUrl() != null){
                if (!isImage(current, holder.view.getContext(), holder.llFriendMessageField)) {
                    holder.tvFriendAttached.setText(current.getMediaName());
                    holder.tvFriendAttached.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EventBus.getDefault().post(new OnMediaClick(holder.attachmentUrl));
                        }
                    });
                }
            }
        }
    @Override
    public int getItemCount() {
        if (allMessages.getMessages() == null) {
            return 0;
        }
        return allMessages.getMessages().size();
    }

    private void clearView(LinearLayout ll){
        for (int i = 0; i < ll.getChildCount(); i++) {
            if (ll.getChildAt(i) instanceof ImageView){
                ll.removeViewAt(i);
            }
        }
    }



    class DialogViewHolder extends RecyclerView.ViewHolder {
        final View view;
        final LinearLayout llUserMessage;
        final LinearLayout llUserMessageField;
        final LinearLayout llFriendMessageField;
        final LinearLayout llFriendMessage;
        String attachmentUrl;

        final TextView tvUserMessage;
        final TextView tvFriendMessage;
        final TextView tvUserName;
        final TextView tvFriendName;
        final TextView tvUserTime;
        final TextView tvFriendTime;
        final TextView tvDate;
        final TextView tvUserAttached;
        final TextView tvFriendAttached;
        final CircleImageView civUserPhoto;
        final CircleImageView civFriendPhoto;
        final ChatMessageView chvFriendMessage;
        DialogViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            llUserMessage = (LinearLayout) itemView.findViewById(R.id.ll_dialog_my_message);
            llUserMessageField = (LinearLayout) itemView.findViewById(R.id.ll_user_message_field);
            llFriendMessageField = (LinearLayout) itemView.findViewById(R.id.ll_friend_message_field);
            llFriendMessage = (LinearLayout) itemView.findViewById(R.id.ll_dialog_friend_message);

            tvDate = (TextView) itemView.findViewById(R.id.tv_dialog_date);
            tvUserMessage = (TextView) itemView.findViewById(R.id.tv_dialog_user_message);
            tvFriendMessage = (TextView) itemView.findViewById(R.id.tv_dialog_friend_message);
            tvUserName = (TextView) itemView.findViewById(R.id.tv_dialog_user_name);
            tvFriendName = (TextView) itemView.findViewById(R.id.tv_dialog_friend_name);
            tvUserTime = (TextView) itemView.findViewById(R.id.tv_dialog_user_time);
            tvFriendTime = (TextView) itemView.findViewById(R.id.tv_friend_time);
            tvUserAttached = (TextView) itemView.findViewById(R.id.tv_dialog_user_attached);
            tvFriendAttached= (TextView) itemView.findViewById(R.id.tv_dialog_friend_attached);
            civUserPhoto = (CircleImageView) itemView.findViewById(R.id.civ_dialog_user);
            civFriendPhoto = (CircleImageView) itemView.findViewById(R.id.civ_dialog_friend);
            chvFriendMessage = (ChatMessageView) itemView.findViewById(R.id.chvFriendMessage);
        }
    }
}
