package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.MessagesRVAdapter;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnDialogClick;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.MessagesApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.MessageApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.MessagesBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.LastMessageDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.MessagesPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.MessagesPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.MessagesViewInterface;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import retrofit2.Retrofit;


public class MessageFragment extends BaseFragment implements MessagesViewInterface{

    private RecyclerView rvMessages;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);

        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(getString(R.string.default_localhost_address_on_emulator),
                getContext());

        MessageApiInteractorInterface messageApiInteractor = new MessagesApiInteractor(retrofit.create(MessagesBulbasaurApiInterface.class));
        MessagesPresenterInterface messagesPresenter = new MessagesPresenter(messageApiInteractor, this);
        rvMessages = (RecyclerView) view.findViewById(R.id.rv_messages);
        messagesPresenter.GetLastMessages();
        return view;
    }

    @Override
    public void showMessages(ArrayList<LastMessageDTO> lastMessages) {
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMessages.setAdapter(new MessagesRVAdapter(lastMessages));
    }
}
