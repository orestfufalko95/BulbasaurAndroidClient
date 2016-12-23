package com.example.orestfufalko.bulbasaurandroidclient.View.Contract;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Adapter.GamesViewerRecViewAdapter;
import com.example.orestfufalko.bulbasaurandroidclient.Adapter.Interface.GamesViewerRecViewAdapterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnDialogClick;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Contract.PersonApiInteractor;
import com.example.orestfufalko.bulbasaurandroidclient.Model.API.Interface.PersonBulbasaurApiInterface;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.GameDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Contract.PersonPresenter;
import com.example.orestfufalko.bulbasaurandroidclient.Presenter.Interface.PersonPresenterInterface;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.PersonPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.GlideUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.NetworkUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.RetrofitUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;

/**
 * Created by orestfufalko on 04.12.2016.
 */

public class PersonFragment extends BaseFragment implements PersonPresenterListener {

    private final String TAG = getClass().getSimpleName();

    private final double PROFILE_PHOTO_MAX_HEIGHT_COEFFICIENT = 0.7;
    private final double GAMES_REC_VIEW_MIN_HEIGHT_COEFFICIENT = 0.8;
    private final double GAMES_REC_VIEW_ITEM_HEIGHT_COEFFICIENT = 0.5;

    private static final String PERSON_ID = "PERSON_ID";
    private static final String USER_PROFILE_DTO_LIST = "USER_PROFILE_DTO_LIST";

    private long personId;

    private UserProfileDTO userProfileDTO;

    private ImageView imageView;
    private TextView fullNameTxtVw;
    private TextView cityTxtView;
    private TextView friendsCountTxtView;
    private TextView aboutTxtView;
    private TextView gamesCountTxtView;

    private RecyclerView recyclerView;

    private Button messageBtn;

    private LinearLayout randomFriendsLinLayout;

    private PersonPresenterInterface personPresenter;

    public static PersonFragment newInstance(long personId) {

        Bundle args = new Bundle();

        args.putLong(PERSON_ID, personId);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static PersonFragment newInstance(UserProfileDTO userProfileDTO) {

        Bundle args = new Bundle();

        args.putParcelable(USER_PROFILE_DTO_LIST, userProfileDTO);

        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Retrofit retrofit = RetrofitUtil.getInstance().getInstanceWithAuthHeader(getString(R.string.default_localhost_address_on_emulator), getContext());
        PersonApiInteractor personApiInteractor = new PersonApiInteractor(retrofit.create(PersonBulbasaurApiInterface.class));
        personPresenter = new PersonPresenter(personApiInteractor, this);

        getArgs();
    }

    private void getArgs() {
        userProfileDTO = getArguments().getParcelable(USER_PROFILE_DTO_LIST);
        if (userProfileDTO == null){
            personId = getArguments().getLong(PERSON_ID, NEGATIVE_ID_TO_GET_CURRENT_USER);// get current user profile if nothing passed
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        imageView = (ImageView) view.findViewById(R.id.image_view);
        fullNameTxtVw = (TextView) view.findViewById(R.id.full_name_txt_vw);
        cityTxtView = (TextView) view.findViewById(R.id.city_txt_vw);
        aboutTxtView = (TextView) view.findViewById(R.id.about_txt_vw);
        friendsCountTxtView = (TextView) view.findViewById(R.id.friends_count_txt_vw_person_fragment);
        randomFriendsLinLayout = (LinearLayout) view.findViewById(R.id.random_friends_lin_layout_person_fragment);
        gamesCountTxtView = (TextView) view.findViewById(R.id.game_count_txt_vw_person_fragment);

        recyclerView = (RecyclerView) view.findViewById(R.id.games_recycler_view_person_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messageBtn = (Button) view.findViewById(R.id.send_message_person_fragment);
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new OnDialogClick((int) personId));
            }
        });

        return view;
    }

    @Override
    public void onPersonByIdPresenterRequestSuccess(UserProfileDTO profileDTO) {
        personId = profileDTO.getUserDTO().getId();
        fillViewWithUserInfo(profileDTO);
    }

    private void fillViewWithUserInfo(final UserProfileDTO profileDTO) {
        userProfileDTO = profileDTO;
        String photoUrl = getString(R.string.default_localhost_address_on_emulator_for_media) + userProfileDTO.getUserDTO().getPicture();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        imageView.setMaxHeight((int) (screenHeight * PROFILE_PHOTO_MAX_HEIGHT_COEFFICIENT));

        GlideUtil.loadImageWithWidthHeightParams(photoUrl, imageView, screenWidth, (int) (screenHeight * PROFILE_PHOTO_MAX_HEIGHT_COEFFICIENT));
        Log.i(TAG, "fillViewWithUserInfo: user photo: " + photoUrl);

        fullNameTxtVw.setText(profileDTO.getUserDTO().getName() + " " + profileDTO.getUserDTO().getSurname());
        cityTxtView.setText(profileDTO.getUserDTO().getAddress());
        aboutTxtView.setText(profileDTO.getUserDTO().getAbout());
        int friendsCount = profileDTO.getFriends().size();
        friendsCountTxtView.setText(String.valueOf(friendsCount));
        int gameCount = userProfileDTO.getGames().size();
        gamesCountTxtView.setText(String.valueOf(gameCount));

        int matchParent = RecyclerView.LayoutParams.MATCH_PARENT;

        if (friendsCount > 0){
            fillFriendsLinLayout(friendsCount);
        }

        if (gameCount > 0) {
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(matchParent,
                    (int) (screenHeight * GAMES_REC_VIEW_MIN_HEIGHT_COEFFICIENT)));
            fillGamesRecView(userProfileDTO.getGames(), screenHeight, screenWidth);
        } else {
            Toast.makeText(getContext(), "No games", Toast.LENGTH_SHORT).show();
            recyclerView.setMinimumHeight(0);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 0));
        }
    }

    private void fillGamesRecView(List<GameDTO> games, final int screenHeight, int screenWidth) {

        if (recyclerView.getAdapter() == null){
            GamesViewerRecViewAdapter adapter = new GamesViewerRecViewAdapter((ArrayList<GameDTO>)  games,
                    (int) (screenHeight * GAMES_REC_VIEW_ITEM_HEIGHT_COEFFICIENT), screenWidth);
            recyclerView.setAdapter(adapter);
        } else {
            GamesViewerRecViewAdapterInterface adapterInterface = (GamesViewerRecViewAdapterInterface) recyclerView.getAdapter();
            adapterInterface.setNewData((ArrayList<GameDTO>) games);
        }
    }

    private void fillFriendsLinLayout(int size) {
        Random random = new Random();

        int childCount = randomFriendsLinLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (randomFriendsLinLayout.getChildAt(i) instanceof CircleImageView){
                CircleImageView circleImageView = (CircleImageView) randomFriendsLinLayout.getChildAt(i);
                String url = getString(R.string.default_localhost_address_on_emulator_for_media)
                        + userProfileDTO.getFriends().get(random.nextInt(size)).getPicture();

                GlideUtil.loadProfileIcon(url, circleImageView);

                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!NetworkUtil.isDeviceConnected(getContext())) {
                            return;
                        }
                        FriendsFragment friendsFragment = FriendsFragment.newInstance(userProfileDTO.getUserDTO().getId());
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content_main, friendsFragment, null).commit();
                    }
                });
            }
        }
    }

    @Override
    public void onPersonByIdPresenterRequestFailure(long id, int statusCode) {
        showToast("onPersonByIdPresenterRequestFailure, id: " + id + " \n status code: " + statusCode, Toast.LENGTH_SHORT);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (userProfileDTO != null){
            onPersonByIdPresenterRequestSuccess(userProfileDTO);
        } else {
            if (personPresenter != null) {
                personPresenter.getPersonInfoById(personId);
            } else {
                showToast(getString(R.string.error_occurs), Toast.LENGTH_LONG);
                Log.i(TAG, "onStart: personPresenter IS NULL!!!");
            }
        }
    }
}
