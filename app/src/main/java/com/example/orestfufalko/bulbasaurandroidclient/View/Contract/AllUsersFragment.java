package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.UsersRecViewAdapter;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.AllUsersApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllUsersApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.AllUsersBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.AllUsersPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.AllUsersPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.AllUsersPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.util.ArrayList;

import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 19.12.2016.
 */

public class AllUsersFragment extends BaseFragment implements AllUsersPresenterListener {

    private final String TAG = getClass().getSimpleName();

    private ArrayList<UserInfoForSearchDTO> users;

    private RecyclerView recyclerView;

    private AllUsersPresenterInterface allUsersPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(
                getString(R.string.default_localhost_address_on_emulator), getContext());
        AllUsersApiInteractorInterface apiInteractor = new AllUsersApiInteractor(retrofit.create(AllUsersBulbasaurApiInterface.class));
        allUsersPresenter = new AllUsersPresenter(apiInteractor, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.users_recycler_view_all_users_fragment);

        return view;
    }

    @Override
    public void onRequestAllUsersSuccess(ArrayList<UserInfoForSearchDTO> users) {
        if (users.isEmpty() && isAdded()){
            showToast( "Sorry, list of all friends is empty!", Toast.LENGTH_SHORT);
        }

        this.users = users;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null){
            adapter = new UsersRecViewAdapter(users);
            recyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            ((UsersRecViewAdapter) adapter).setNewData(this.users);
        }
    }

    @Override
    public void onAllUsersRequestFailure() {
        showToast("Sorry, some error occurs!\nPlease restart app!", Toast.LENGTH_LONG);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(users == null){
            if (allUsersPresenter != null) {
                allUsersPresenter.getAllUsers();
            } else {
                showToast("Sorry, some error occurs!\nPlease restart app!", Toast.LENGTH_LONG);
                Log.i(TAG, "onStart: personPresenter IS NULL!!!");
            }
        } else {
            onRequestAllUsersSuccess(users);
        }
    }
}
