package com.example.orestfufalko.bulbasaurandroidclient.View;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.orestfufalko.bulbasaurandroidclient.Event.OnPermissionGuaranteed;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnDialogClick;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnShowUsersByGame;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnUnauthorizedEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Event.OnViewUserProfileEvent;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserInfoForSearchDTO;
import com.example.orestfufalko.bulbasaurandroidclient.Model.Entity.UserProfileDTO;
import com.example.orestfufalko.bulbasaurandroidclient.R;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.AllGamesFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.DialogFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.AllUsersFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.DialogFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.EditProfileFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.FriendsFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.MessageFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Contract.PersonFragment;
import com.example.orestfufalko.bulbasaurandroidclient.View.Interface.EditPresenterListener;
import com.example.orestfufalko.bulbasaurandroidclient.utils.NetworkUtil;
import com.example.orestfufalko.bulbasaurandroidclient.utils.TokenUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = "MainActivityTAG";

    private final long NEGATIVE_ID_TO_GET_CURRENT_USER = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (TokenUtil.getInstance().getToken(getApplicationContext()) == null){
            startAuthorizationActivity();
        } else {
            PersonFragment personFragment = PersonFragment.newInstance(NEGATIVE_ID_TO_GET_CURRENT_USER);//firstly getCurrentUserProfile
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, personFragment).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null){
            Toast.makeText(this, "Data from authorization is null", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i(TAG, "onActivityResult: enter");
        switch (requestCode){
            case 1 :
                if (resultCode == RESULT_OK){

                    UserProfileDTO userProfile = data.getParcelableExtra(AuthorizationActivity.USER_PROFILE_EXTRA);

                    PersonFragment personFragment = PersonFragment.newInstance(userProfile);//firstly getCurrentUserProfile
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, personFragment).commit();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case DialogFragment.WRITE_PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    EventBus.getDefault().post(new OnPermissionGuaranteed());
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
        }
    }

    private void startAuthorizationActivity() {
        Intent intent = new Intent(getBaseContext(), AuthorizationActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.authorization_request_code));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        if (!NetworkUtil.isDeviceConnected(getApplicationContext())) {
            return false;
        }

        // Handle navigation view item clicks here.
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profile:
                PersonFragment personFragment = PersonFragment.newInstance(NEGATIVE_ID_TO_GET_CURRENT_USER);
                fragmentTransaction.replace(R.id.content_main, personFragment, null);

                break;
            case R.id.nav_friends:
                FriendsFragment friendsFragment = FriendsFragment.newInstance(NEGATIVE_ID_TO_GET_CURRENT_USER);
                fragmentTransaction.replace(R.id.content_main, friendsFragment, null);

                break;
            case R.id.nav_messages:
                MessageFragment messageFragment = new MessageFragment();
                fragmentTransaction.replace(R.id.content_main, messageFragment);

                break;
            case R.id.nav_games:
                AllGamesFragment gamesFragment = new AllGamesFragment();
                fragmentTransaction.replace(R.id.content_main, gamesFragment, null);

                break;
            case R.id.nav_search:
                AllUsersFragment allUsersFragment = new AllUsersFragment();
                fragmentTransaction.replace(R.id.content_main, allUsersFragment, null);

                break;
            case R.id.nav_edit_profile:
                Fragment fragment = new EditProfileFragment();
                fragmentTransaction.replace(R.id.content_main, fragment, null);

                break;
            case R.id.nav_log_out:
                TokenUtil.getInstance().saveToken(null, getBaseContext());
                startAuthorizationActivity();
                break;

        }

        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnauthorizedEvent(OnUnauthorizedEvent event) {
        TokenUtil.getInstance().saveToken(null, getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), AuthorizationActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.authorization_request_code));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onViewUserProfileEvent(OnViewUserProfileEvent event) {
        PersonFragment personFragment = PersonFragment.newInstance(event.userId);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, personFragment).commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowUsersByGame(OnShowUsersByGame event) {
        Log.i(TAG, "onShowUsersByGame: ");
        FriendsFragment friendsFragment = FriendsFragment.newInstance((ArrayList<UserInfoForSearchDTO>) event.users);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, friendsFragment, null).commit();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OnDialogClick event) {
        DialogFragment fragment = DialogFragment.newInstance(event.friendid);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

}
