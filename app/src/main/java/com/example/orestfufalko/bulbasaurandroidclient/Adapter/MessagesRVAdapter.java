package com.example.orestfufalko.bulbasaurandroidclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnDialogClick;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.TimeParseUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class MessagesRVAdapter extends RecyclerView.Adapter<MessagesRVAdapter.MessagesViewHolder> {

    private ArrayList<LastMessageDTO> dialogs;
    private final String MEDIA_PATH = "http://10.0.2.2:5001";

    public MessagesRVAdapter(ArrayList<LastMessageDTO> dialogs){
        this.dialogs = dialogs;
    }

    @Override
    public MessagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messages_list_item, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MessagesViewHolder holder, int position) {
        String friendUrl = MEDIA_PATH + dialogs.get(position).getPhotoUrl();
        GlideUtil.loadProfileIcon(friendUrl, holder.civFriendPhoto);
        String senderUrl = MEDIA_PATH + dialogs.get(position).getSenderShortInfo().getSenderPhotoUrl();
        GlideUtil.loadProfileIcon(senderUrl, holder.civSenderPhoto);
        holder.tvFriendName.setText(dialogs.get(position).getName());
        holder.tvSenderName.setText(dialogs.get(position).getMessage());

        String time = getTime(dialogs.get(position).getTime());
        holder.tvTime.setText(time);
        holder.friendId = dialogs.get(position).getFriendId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new OnDialogClick(holder.friendId));
            }
        });
    }

    private String getTime(String dateTime){
        String date = TimeParseUtil.parseDate(dateTime);
        String time = TimeParseUtil.parseTime(dateTime);
        return date + " " + time;
    }

    @Override
    public int getItemCount() {
        return dialogs.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {
        final View itemView;
        final TextView tvFriendName;
        final TextView tvSenderName;
        final TextView tvTime;
        final CircleImageView civFriendPhoto;
        final CircleImageView civSenderPhoto;
        int friendId;
        MessagesViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            tvFriendName = (TextView) itemView.findViewById(R.id.tv_messages_friend_name);
            civFriendPhoto = (CircleImageView) itemView.findViewById(R.id.civ_messages_friend_photo);
            civSenderPhoto = (CircleImageView) itemView.findViewById(R.id.civ_messages_message_photo);
            tvSenderName = (TextView) itemView.findViewById(R.id.tv_messages_message_sender);
            tvTime = (TextView) itemView.findViewById(R.id.tv_messages_message_time);

        }
    }
}
