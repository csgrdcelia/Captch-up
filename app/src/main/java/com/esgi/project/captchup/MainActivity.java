package com.esgi.project.captchup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.logging.Level;

public class MainActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    //Fragment fragment = new LevelFragment();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateUI(new GameFragment());
                    return true;
                case R.id.navigation_new:
                    updateUI(new LevelFragment());
                    return true;
                case R.id.navigation_achievements:
                    updateUI(new GameFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //updateUI(new LevelFragment());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void updateUI(Fragment fragmentToGive) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        if(!(currentFragment.getClass() == fragmentToGive.getClass())) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, fragmentToGive);
            fragmentTransaction.disallowAddToBackStack();
            fragmentTransaction.commit();
        }
    }

}
