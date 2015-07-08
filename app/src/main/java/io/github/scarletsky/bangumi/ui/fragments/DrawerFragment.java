package io.github.scarletsky.bangumi.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import io.github.scarletsky.bangumi.BangumiApplication;
import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.api.models.User;
import io.github.scarletsky.bangumi.events.ClickNavigateIconEvent;
import io.github.scarletsky.bangumi.events.GetSubjectEvent;
import io.github.scarletsky.bangumi.events.SessionChangeEvent;
import io.github.scarletsky.bangumi.events.SetToolbarEvent;
import io.github.scarletsky.bangumi.utils.BusProvider;
import io.github.scarletsky.bangumi.utils.SessionManager;

/**
 * Created by scarlex on 15-7-2.
 */
public class DrawerFragment extends Fragment implements OnNavigationItemSelectedListener {

    private static final String TAG = DrawerFragment.class.getSimpleName();
    private static final String TAG_CALENDAR = CalendarFragment.class.getSimpleName();
    private static final String TAG_SUBJECT_DETAIL = SubjectDetailFragment.class.getSimpleName();
    private static final String TAG_LOGIN = LoginFragment.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;
    private SessionManager mSession = BangumiApplication.getInstance().getSession();

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer, container, false);
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.drawer_main, new BaseToolbarFragment())
                .commit();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFragmentManager = getActivity().getSupportFragmentManager();

        mDrawerLayout = (DrawerLayout) getView().findViewById(R.id.drawer_wrapper);
        mNavigationView = (NavigationView) getView().findViewById(R.id.drawer_nav);
        mNavigationView.setNavigationItemSelectedListener(this);

        if (mSession.isLogin()) {
            onLogined();
        } else {
            onLogouted();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.menu_login:
                goToDst(TAG_LOGIN);
                break;
            case R.id.menu_calendar:
                goToDst(TAG_CALENDAR);
                break;
            case R.id.menu_settings:
                Log.d(TAG, "clickkkk setttttting");
                break;
            case R.id.menu_logout:
                mSession.logout();
                break;
        }

        return false;
    }

    @Subscribe
    public void onClickNavigateIconEvent(ClickNavigateIconEvent event) {
        switch (event.getIconType()) {
            case MENU:
                openDrawer();
                break;
            case BACK:
                break;
        }
    }


    @Subscribe
    public void onGetSubjectIdEvent(GetSubjectEvent event) {
        mFragmentManager
                .beginTransaction()
                .add(R.id.drawer_main, new ImageToolbarFragment(event.getSubject()), TAG_SUBJECT_DETAIL)
                .addToBackStack(TAG_SUBJECT_DETAIL)
                .commit();
    }

    @Subscribe
    public void onSessionChangeEvent(SessionChangeEvent event) {
        boolean isLogin = event.isLogin();

        if (isLogin) {
            onLogined();
        } else {
            onLogouted();
        }

    }

    private void onLogined() {
        mNavigationView.getMenu().findItem(R.id.menu_login).setVisible(false);
        mNavigationView.getMenu().findItem(R.id.menu_logout).setVisible(true);
        setupUser();
    }

    private void onLogouted() {
        mNavigationView.getMenu().findItem(R.id.menu_login).setVisible(true);
        mNavigationView.getMenu().findItem(R.id.menu_logout).setVisible(false);
        setupUser();
        goToDst(TAG_LOGIN);
    }

    private void setupUser() {
        User user = mSession.getUser();

        RoundedImageView mUserAvatar = (RoundedImageView) mNavigationView.findViewById(R.id.user_avatar);
        TextView mUserNickname = (TextView) mNavigationView.findViewById(R.id.user_nickname);

        if (user == null) {
            mUserAvatar.setImageResource(R.drawable.ic_action_menu);
            mUserNickname.setText(getString(R.string.label_click_to_login));
        } else {
            mUserNickname.setText(user.getNickname());
            Picasso
                    .with(getActivity())
                    .load(user.getAvatar().getLarge())
                    .fit()
                    .centerCrop()
                    .into(mUserAvatar);
        }
    }

    private void goToDst(String tag) {
        Fragment targetFragment = mFragmentManager.findFragmentByTag(tag);
        if (targetFragment == null) {

            if (tag.equals(TAG_LOGIN)) {
                targetFragment = new LoginFragment();
            } else if (tag.equals(TAG_CALENDAR)) {
                targetFragment = new CalendarFragment();
            }

            mFragmentManager
                    .beginTransaction()
                    .add(R.id.frame_base_toolbar_content, targetFragment, tag)
                    .addToBackStack(tag)
                    .commit();

        } else {
            mFragmentManager.popBackStack(tag, 0);
        }
    }

    private void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}

