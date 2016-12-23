package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.GamesViewerRecViewAdapter;
import com.example.orestfufalko.bulbasaurandroidclient.Adapter.Interface.GamesViewerRecViewAdapterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.AllGamesApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.EditProfileApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllGamesBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.EditProfileBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.AllGamesPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllGamesPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.AllGamesPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.util.ArrayList;

import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 18.12.2016.
 */

public class AllGamesFragment extends BaseFragment implements AllGamesPresenterListener {

    private final String TAG = getClass().getSimpleName();

    private AllGamesPresenterInterface allGamePresenter;

    private final double GAMES_REC_VIEW_ITEM_HEIGHT_COEFFICIENT = 0.5;

    private ArrayList<GameDTO> games;

    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(
                getString(R.string.default_localhost_address_on_emulator), getContext());
        AllGamesApiInteractorInterface allGamesApiInteractor = new AllGamesApiInteractor(
                retrofit.create(AllGamesBulbasaurApiInterface.class));
        allGamePresenter = new AllGamesPresenter(allGamesApiInteractor, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_games, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.games_rec_view_all_games_fragment  );
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onAllGamesPresenterRequestSuccess(ArrayList<GameDTO> games) {
//        showToast("games: " + games.size(), Toast.LENGTH_SHORT);
        if (recyclerView.getAdapter() != null){
            GamesViewerRecViewAdapterInterface adapter = (GamesViewerRecViewAdapterInterface) recyclerView.getAdapter();
            adapter.setNewData(games);
        } else {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int screenWidth = displaymetrics.widthPixels;
            int screenHeight = displaymetrics.heightPixels;
            GamesViewerRecViewAdapter adapter = new GamesViewerRecViewAdapter(games,
                    (int) (screenHeight * GAMES_REC_VIEW_ITEM_HEIGHT_COEFFICIENT), screenWidth);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onAllGamesPresenterRequestFailure() {
        showToast(getString(R.string.error_occurs), Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (allGamePresenter != null) {
//            showToast("onStart", Toast.LENGTH_LONG);

            allGamePresenter.getAllGames();
        } else {
            showToast(getString(R.string.error_occurs), Toast.LENGTH_LONG);
            Log.i(TAG, "onStart: allGamePresenter IS NULL!!!");
        }
    }
}
