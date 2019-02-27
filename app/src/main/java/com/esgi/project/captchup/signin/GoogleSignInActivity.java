package com.esgi.project.captchup.signin;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.esgi.project.captchup.MainActivity;
import com.esgi.project.captchup.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Firebase Authentication using a Google ID Token
 */
public class GoogleSignInActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        databaseReference = FirebaseDatabase.getInstance().getReference( "users/");


        findViewById(R.id.signInButton).setOnClickListener(view -> {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch(Exception e) {

            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle(getString(R.string.quit))
                .setMessage(getString(R.string.do_you_want_to_quit))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(getString(R.string.Yes), (dialog, which) -> finish())
                .setNegativeButton(getString(R.string.No), null)
                .show();
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser != null) {
            // OPEN MAIN ACTIVITY
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        addUser(user);
                        updateUI(user);
                    } else {
                        Snackbar.make(findViewById(R.id.main_layout), getString(R.string.auth_failed), Snackbar.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                });
    }

    private void addUser(FirebaseUser user) {
        if(user != null) {
            databaseReference.child(user.getUid()).setValue(user.getDisplayName());
        }
    }
}
