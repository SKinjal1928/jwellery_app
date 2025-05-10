package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(SplashActivity.this);
        startNextScreen();

    }

    private void startNextScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SharedPref.getString("token", "").isEmpty()) {
                    startDashboard();
                } else {
                startLogin();
                }

            }
        }, 3000);
    }

    private void startDashboard() {
        Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
    }

    private void startLogin() {
        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

}
