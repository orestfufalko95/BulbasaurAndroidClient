package com.example.orestfufalko.bulbasaurandroidclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.Interface.UsersRecViewAdapterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnDialogClick;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnViewUserProfileEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by orestfufalko on 15.12.2016.
 */

public class UsersRecViewAdapter extends RecyclerView.Adapter<UsersRecViewAdapter.FriendViewHolder> implements UsersRecViewAdapterInterface {

    private ArrayList<UserInfoForSearchDTO> friends;

    public UsersRecViewAdapter(ArrayList<UserInfoForSearchDTO> friends) {
        this.friends = friends;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_rec_view_item, parent,false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        UserInfoForSearchDTO userInfo = friends.get(position);

        holder.userDTO = userInfo;

        String pictureUrl = holder.friendImageView.getContext().getString(R.string.default_localhost_address_on_emulator_for_media)
                + userInfo.getPhotoUrl();
//        Log.i(getClass().getSimpleName(), "onBindViewHolder: imageUrl: " + pictureUrl);

        GlideUtil.loadProfileIcon( pictureUrl, holder.friendImageView);
        holder.friendNameTxtVw.setText(userInfo.getName() + " "+ userInfo.getSurName());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    @Override
    public void setNewData(ArrayList<UserInfoForSearchDTO> users) {
        this.friends = users;
    }

    class FriendViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView friendImageView;
        public ImageButton chatImageButton;
        public TextView friendNameTxtVw;
        public UserInfoForSearchDTO userDTO;

        public FriendViewHolder(View itemView) {
            super(itemView);
            friendImageView = (CircleImageView) itemView.findViewById(R.id.friend_icon_image_view);
            chatImageButton = (ImageButton) itemView.findViewById(R.id.friend_chat_image_button_friends_fragment);
            friendNameTxtVw = (TextView) itemView.findViewById(R.id.friend_name_txt_vw);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventBus.getDefault().post(new OnViewUserProfileEvent(userDTO.getId()));
                }
            });

            chatImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Chat icon id: " + userDTO.getId(), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new OnDialogClick(userDTO.getId()));
                }
            });
        }
    }
}
