package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.DialogRVAdapter;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnFileDownloadProgress;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnMediaClick;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnPermissionGuaranteed;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.DialogApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.DialogApiInteractorInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.DialogBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.AllMessageResponseDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.MessageInputDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.DialogPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.DialogPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.Service.FileDownloadService;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.DialogViewInterface;
import com.example.orestfufalko.bulbasaurandroidclient.utils.MediaUrlParser;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

import retrofit2.Retrofit;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by Vitalii Khorobchuk on 17.12.2016.
 */

public class DialogFragment extends BaseFragment implements DialogViewInterface {

    private static final String FRIEND_ID_KEY = "friendId";
    private final String TAG = "DialogFragmentTAG";
    public static final int WRITE_PERMISSION_CODE = 100;
    private final int NOTIFICATION_ID = 1;
    private int friendId;
    private String otherFilePath ;


    private RecyclerView rvDialog;
    private ImageButton btnSend;
    private ImageButton btnRefresh;
    private EditText etMessage;
    private TextView tvEmpty;
    private DialogRVAdapter adapter;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    private DialogPresenterInterface dialogPresenter;



    public static DialogFragment newInstance(int friendId){
        DialogFragment dialogFragment = new DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRIEND_ID_KEY, friendId);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        getFriendId();
        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(getString(R.string.default_localhost_address_on_emulator),
                getContext());
        DialogApiInteractorInterface dialogApi = new DialogApiInteractor(retrofit.create(DialogBulbasaurApiInterface.class));
        dialogPresenter = new DialogPresenter(this, dialogApi, friendId);
        dialogPresenter.getMessages();
        //initDialogRV(view, new AllMessageResponseDTO());
        etMessage = (EditText) view.findViewById(R.id.et_dialog_message);
        tvEmpty = (TextView) view.findViewById(R.id.tv_dialog_empty);

        initSendBtn(view);
        initRefreshBtn(view);
        return view;
    }

    private void initDialogRV(View view, AllMessageResponseDTO allMessages){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        rvDialog = (RecyclerView) view.findViewById(R.id.rv_dialog);
        LinearLayoutManager llManager = new LinearLayoutManager(getContext());
        llManager.setStackFromEnd(true);
        rvDialog.setLayoutManager(llManager);
        adapter = new DialogRVAdapter(allMessages, friendId, screenWidth, screenHeight);
        rvDialog.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showProgress(OnFileDownloadProgress progress){
        mBuilder.setProgress(100, (int) progress.getProgress(), false);
        if ((int) progress.getProgress() >= 100){
            mBuilder.setContentText("Download complete");
        }

//        etMessage.setText(String.valueOf(progress.getProgress()));
        mBuilder.setProgress(100, (int) progress.getProgress(), false);
        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoClick(OnMediaClick event){
        Log.i(TAG, "onVideoClick: " + event.getMediaUrl());
        if (MediaUrlParser.isVideoFile(event.getMediaUrl())){
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setDataAndType(Uri.parse(event.getMediaUrl()), "video/*");
            startActivity(Intent.createChooser(intent, "Complete action using"));
        }
        else if (MediaUrlParser.isAudioFile(event.getMediaUrl())){
            Intent intent = new Intent();
            ComponentName comp = new ComponentName("com.android.music", "com.android.music.MediaPlaybackActivity");
            intent.setComponent(comp);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(event.getMediaUrl()), "audio/*");
            startActivity(intent);
        }
        else {
            otherFilePath = event.getMediaUrl();
            if (isStoragePermissionGranted()){
                downLoadFile();
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionGaranteed(OnPermissionGuaranteed permissionGuaranteed){
        downLoadFile();
    }


        private void downLoadFile(){

        String fileName = otherFilePath.substring(otherFilePath.lastIndexOf('/') + 1);

        Intent intent = new Intent(getActivity(), FileDownloadService.class);
        intent.putExtra("url", otherFilePath);
        intent.putExtra("fileName", fileName);
        getActivity().startService(intent);

        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getActivity());
        mBuilder.setContentTitle(getString(R.string.download_notification) + fileName)
                .setContentText(getString(R.string.download_progress))
                .setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setProgress(100, 0, false);
        mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    public boolean isStoragePermissionGranted() {

        if (Build.VERSION.SDK_INT >= 23) {

            if (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION_CODE);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }



    private void initSendBtn(View view){
        btnSend = (ImageButton) view.findViewById(R.id.btn_dialog_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageInputDTO messageInput = new MessageInputDTO();
                messageInput.setMessage(etMessage.getText().toString());
                messageInput.setReceiverId(String.valueOf(friendId));
                dialogPresenter.sendMessage(messageInput);
                InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                etMessage.setText("");
                tvEmpty.setVisibility(View.GONE);
            }
        });
    }

    private void initRefreshBtn(View view){
        btnRefresh = (ImageButton) view.findViewById(R.id.btn_dialog_refresh);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPresenter.getMessages();
            }
        });
    }

    private void getFriendId(){
        try {
            friendId = getArguments().getInt(FRIEND_ID_KEY);

        }
        catch (Exception e){
            Log.e(TAG, "getFriendId: " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void showMessages(AllMessageResponseDTO messages) {
        if (messages.getMessages().size() == 0){

            tvEmpty.setVisibility(View.VISIBLE);
        }
        else {
            initDialogRV(getView(), messages);
        }

    }

}
