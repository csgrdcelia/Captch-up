package com.esgi.project.captchup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.esgi.project.captchup.imageprocessing.ImageProcessingFragment;
import com.esgi.project.captchup.level.LevelFragment;
import com.esgi.project.captchup.signin.GoogleSignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
                        return true;
                    case R.id.navigation_new:
                        updateFragment(new ImageProcessingFragment());
                        return true;
                    case R.id.navigation_achievements:
                        updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.FINISHED));
                        return true;
                    default:
                        return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser account = FirebaseAuth.getInstance().getCurrentUser();
        if(account == null) {
            Toast.makeText(this, getString(R.string.please_sign_in), Toast.LENGTH_SHORT).show();
            goBackToSignIn();
        } else {
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.mainFragment);
            if (getIntent().getBooleanExtra("WIDGET", false)) {
                updateFragment(new ImageProcessingFragment());
                getIntent().removeExtra("WIDGET");
            }
            else if (existingFragment == null) {
                updateFragment(LevelFragment.newInstance(LevelFragment.LevelFragmentType.UNFINISHED));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(getString(R.string.logout))
                .setMessage(getString(R.string.are_you_sure_logout))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        goBackToSignIn();
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton(getString(R.string.No), null)
                .show();
    }

    private void goBackToSignIn(){
        Intent intent = new Intent(this, GoogleSignInActivity.class);
        startActivity(intent);
        finish();
    }


}
