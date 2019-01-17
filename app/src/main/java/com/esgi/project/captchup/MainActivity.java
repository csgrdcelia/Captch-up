package com.esgi.project.captchup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.esgi.project.captchup.Game.GameFragment;
import com.esgi.project.captchup.Level.LevelFragment;

    public class MainActivity extends FragmentActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
                    return true;
                case R.id.navigation_new:
                    updateFragment(new GameFragment()); //TODO: Run gallery
                    return true;
                case R.id.navigation_achievements:
                    updateFragment(LevelFragment.   newInstance(LevelFragment.LevelFragmentType.FINISHED));
                    return true;
            }
            return false;
        }
    };
/*
    @Override
    protected void onResume() {
        super.onResume();
        updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if(existingFragment == null) {
            updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
        }
    }

    private void updateFragment(Fragment fragmentToGive) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, fragmentToGive);
        fragmentTransaction.disallowAddToBackStack();
        fragmentTransaction.commit();
    }

}
