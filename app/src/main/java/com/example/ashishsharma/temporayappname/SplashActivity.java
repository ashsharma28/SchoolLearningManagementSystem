package com.example.ashishsharma.temporayappname;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    String MY_PREFS = "MyPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(MY_PREFS,0);
        setContentView(R.layout.splash_layout);
        initViews();
        final Class launchClass ;
        int delay;
        if(CurrentUser.INSTANCE.isFirstTime()){
            launchClass = OnboardingActivity.class;
            delay = 1500;
        }else{
            launchClass = OnboardingActivity.class;
          Toast.makeText(this, "qwerty", Toast.LENGTH_LONG).show();
            delay = 1000;
        }

        new Timer().schedule(new TimerTask() {
            public void run() {
                SplashActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Intent startIntent = new Intent(SplashActivity.this, launchClass);
                        startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startIntent);
                        finish();
                    }
                });
            }
        }, delay);

    }


    private void initViews() {
        ImageView imageView = (ImageView)findViewById(R.id.splash_screen);
        imageView.setImageResource(R.drawable.splash);
    }

}

