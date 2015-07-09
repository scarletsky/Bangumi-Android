package io.github.scarletsky.bangumi.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import io.github.scarletsky.bangumi.R;
import io.github.scarletsky.bangumi.ui.fragments.DrawerFragment;


public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerFragment mDrawerFragment = new DrawerFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().add(R.id.frame_main, mDrawerFragment).commit();
    }

    public FragmentManager getFM() {
        return mFragmentManager;
    }

}
