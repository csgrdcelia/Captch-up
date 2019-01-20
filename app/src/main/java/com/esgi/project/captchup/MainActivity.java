package com.esgi.project.captchup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.esgi.project.captchup.Game.GameFragment;
import com.esgi.project.captchup.Level.LevelFragment;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
                        return true;
                    case R.id.navigation_new:
                        updateFragment(GameFragment.newInstance(1)); //TODO: Run gallery instead
                        return true;
                    case R.id.navigation_achievements:
                        updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.FINISHED));
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if (existingFragment == null) {
            updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
        }
    }

    /**
     *  Bind the given fragment into R.id.mainFragment
     */
    private void updateFragment(Fragment fragmentToGive) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }
}
