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
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.FriendApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.FriendApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.FriendBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.FriendPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.FriendPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import java.util.ArrayList;

import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 15.12.2016.
 */

public class FriendsFragment extends BaseFragment implements FriendPresenterListener {

    private final String TAG = getClass().getSimpleName();

    private static final String PERSON_ID = "PERSON_ID";
    private static final String FRIEND_PROFILE_DTO_LIST = "FRIEND_PROFILE_DTO_LIST";

    private long personId;
    private ArrayList<UserInfoForSearchDTO> friends;

    private RecyclerView recyclerView;

    private FriendPresenter friendPresenter;

    public static FriendsFragment newInstance(long personId) {

        Bundle args = new Bundle();

        args.putLong(PERSON_ID, personId);

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static FriendsFragment newInstanceByUserDTOList(ArrayList<UserDTO> friends) {
        return newInstance(UserInfoForSearchDTO.getFromUserDTOList(friends));
    }

    public static FriendsFragment newInstance(ArrayList<UserInfoForSearchDTO> friends) {

        Bundle args = new Bundle();

        args.putParcelableArrayList(FRIEND_PROFILE_DTO_LIST, friends);

        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getArgs();

        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(getString(R.string.default_localhost_address_on_emulator), getContext());
        FriendApiInteractorInterface friendApiInteractor = new FriendApiInteractor(retrofit.create(FriendBulbasaurApiInterface.class));
        friendPresenter = new FriendPresenter(friendApiInteractor, this);
    }

    private void getArgs() {
        friends = getArguments().getParcelableArrayList(FRIEND_PROFILE_DTO_LIST);
        if (friends == null){
            personId = getArguments().getLong(PERSON_ID, NEGATIVE_ID_TO_GET_CURRENT_USER);// get current user profile if nothing passed
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.friends_recycler_view_friends_fragment);

        return view;
    }

    @Override
    public void onFriendPresenterRequestSuccess(ArrayList<UserInfoForSearchDTO> list) {
        if (list.isEmpty() && isAdded()){
            Toast.makeText(getContext(), "Sorry, list of your friends is empty!", Toast.LENGTH_SHORT).show();
        }

        friends = list;

        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null){
            adapter = new UsersRecViewAdapter(friends);
            recyclerView.setAdapter(adapter);

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            ((UsersRecViewAdapter) adapter).setNewData(this.friends);
        }
    }

    @Override
    public void onFriendPresenterRequestFailure(long id, int statusCode) {
        showToast( "Failure friends fragment; userId: " + id + " code: " + statusCode, Toast.LENGTH_SHORT);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (friends == null) {
            if (friendPresenter != null) {
                friendPresenter.getFriendsById(personId);
            } else {
                showToast("Sorry, some error occurs!\nPlease restart app!", Toast.LENGTH_LONG);
                Log.i(TAG, "onStart: personPresenter IS NULL!!!");
            }
        } else {
            onFriendPresenterRequestSuccess(friends);
        }
    }

}
