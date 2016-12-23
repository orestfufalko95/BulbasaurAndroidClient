package com.example.orestfufalko.bulbasaurandroidclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.Interface.GamesViewerRecViewAdapterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.ViewHolder.GameViewHolder;

import java.util.ArrayList;

/**
 * Created by orestfufalko on 17.12.2016.
 */

public class GamesViewerRecViewAdapter extends RecyclerView.Adapter<GameViewHolder> implements GamesViewerRecViewAdapterInterface {

    private ArrayList<GameDTO> games;
    private int screenHeight;
    private int screenWidth;

    public GamesViewerRecViewAdapter(ArrayList<GameDTO> games, int itemHeight, int itemWidth) {
        this.games = games;
        this.screenHeight = itemHeight;
        this.screenWidth = itemWidth;
    }

    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_viewer_rec_view_item, parent,false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameViewHolder holder, int position) {
        GameDTO gameDTO = games.get(position);

        holder.screenHeight = screenHeight;
        holder.screenWidth = screenWidth;

        holder.gameInfo = gameDTO;
        holder.setViewData();
    }

    @Override
    public int getItemCount() {
        return games.size();
    }
    @Override
    public void setNewData(ArrayList<GameDTO> newData){
        Log.i(getClass().getSimpleName(), "setNewData: ");
        games = newData;
        notifyDataSetChanged();
    }
}
