package com.blstream.studybox.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blstream.studybox.auth.login.LoginInterface;
import com.blstream.studybox.auth.login.LoginManager;
import com.blstream.studybox.auth.login_view.LoginActivity;
import com.blstream.studybox.decks_view.DecksActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent;
        LoginInterface login = new LoginManager();
        if (login.isUserLoggedIn()) {
            mainIntent = new Intent(SplashScreenActivity.this, DecksActivity.class);
        } else {
            mainIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        }
        startActivity(mainIntent);
        finish();
    }
}
