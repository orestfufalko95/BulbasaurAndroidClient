package com.example.orestfufalko.bulbasaurandroidclient.View.ViewHolder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnShowUsersByGame;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.GameApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.GameApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.GameBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.GamePresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.GamePresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.GamePresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.NetworkUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class GameViewHolder extends RecyclerView.ViewHolder implements GamePresenterListener, View.OnClickListener{

    public int screenWidth;
    public int screenHeight;
    public GameDTO gameInfo;

    public ImageView gameImageView;
    public ImageView likeGameImageView;
    public TextView gameNameTxtVw;

    private GamePresenterInterface gamePresenter;

    public GameViewHolder(View itemView) {
        super(itemView);


        gameNameTxtVw = (TextView) itemView.findViewById(R.id.game_name_txt_vw_game_item);

        likeGameImageView = (ImageView) itemView.findViewById(R.id.like_game_image_view_item);
        likeGameImageView.setOnClickListener(this);
        gameImageView = (ImageView) itemView.findViewById(R.id.game_image_view_game_item);
        gameImageView.setOnClickListener(this);

        setPresenter(gameImageView.getContext());
    }

    private void setPresenter(Context context) {
        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(context.getString(R.string.default_localhost_address_on_emulator), context);
        GameApiInteractorInterface gameApiInteractor = new GameApiInteractor(retrofit.create(GameBulbasaurApiInterface.class));
        gamePresenter = new GamePresenter(gameApiInteractor, this);
    }

    public void setViewData(){
        String pictureUrl = gameImageView.getContext().getString(R.string.default_localhost_address_on_emulator_for_media)
                + gameInfo.getPicture();
        GlideUtil.loadImageWithWidthHeightParams( pictureUrl, gameImageView, screenWidth, screenHeight);

        setCorrectLikeIcon(gameInfo.isChosenByUser());

        gameNameTxtVw.setText(gameInfo.getName());
    }

    @Override
    public void onClick(View view) {
        if (!NetworkUtil.isDeviceConnected(gameImageView.getContext())){
            return;
        }
        if (gamePresenter == null){
            Toast.makeText(gameImageView.getContext(), gameImageView.getContext().getString(R.string.error_occurs), Toast.LENGTH_LONG).show();
            Log.i(getClass().getSimpleName(), "presenter  IS NULL!!!: ");
        }
        switch (view.getId()){
            case R.id.like_game_image_view_item:
                if (gameInfo.isChosenByUser()){
                    gamePresenter.deleteGame(gameInfo.getId());
                } else {
                    gamePresenter.addGame(gameInfo.getId());
                }
                break;

            case R.id.game_image_view_game_item:
//                Toast.makeText(view.getContext(), "game id: " + gameInfo.getId(), Toast.LENGTH_SHORT).show();
                gamePresenter.getUsersByGame(gameInfo.getId());
                break;
        }
    }

    private void setCorrectLikeIcon(boolean isChosenByUser){
                likeGameImageView.setImageDrawable(ContextCompat.getDrawable(gameImageView.getContext(),
                        isChosenByUser ? R.drawable.heart_full : R.drawable.heart_empty
        ));
    }

    @Override
    public void onAddGameSuccess() {
        setCorrectLikeIcon(true);
    }

    @Override
    public void onAddGameFailure() {
        Toast.makeText(gameImageView.getContext(), gameImageView.getContext().getString(R.string.error_occurs), Toast.LENGTH_LONG).show();
        Log.i(getClass().getSimpleName(), "onAddGameFailure: ");
    }

    @Override
    public void onDeleteGameSuccess() {
        setCorrectLikeIcon(false);
    }

    @Override
    public void onDeleteGameFailure() {
        Toast.makeText(gameImageView.getContext(), gameImageView.getContext().getString(R.string.error_occurs), Toast.LENGTH_LONG).show();
        Log.i(getClass().getSimpleName(), "onDeleteGameFailure: ");
    }

    @Override
    public void onGetUsersByGameSuccess(ArrayList<UserInfoForSearchDTO> users) {
        if (users.isEmpty()){
            Toast.makeText(gameImageView.getContext(), R.string.noone_play_this_game, Toast.LENGTH_SHORT).show();
            return;
        }
        EventBus.getDefault().post(new OnShowUsersByGame(users));
    }

    @Override
    public void onGetUsersByNameFailure() {
        Toast.makeText(gameImageView.getContext(), gameImageView.getContext().getString(R.string.error_occurs), Toast.LENGTH_LONG).show();
        Log.i(getClass().getSimpleName(), "onGetUsersByNameFailure: ");
    }
}
