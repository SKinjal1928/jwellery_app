package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityLoginBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.LoginReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.LoginResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    RetroInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginApiCall();
            }
        });
    }
    private void loginApiCall() {
        binding.progressBar.setVisibility(View.VISIBLE);
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        LoginReq user = new LoginReq(binding.etUsername.getText().toString() + "", binding.etPassword.getText().toString() + "");
        Call<LoginResponse> call1 = apiInterface.loginApi(binding.etUsername.getText().toString() + "",
                binding.etPassword.getText().toString() + "");
        call1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG", response.code() + "");

                if (response.code() == 200) {
                    Log.d("Login Token", response.body().getData().getAccessToken() + "");
                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(i);
                   /* token = response.body().getData().getToken();
                    SharedPref.putString("token", token);
                    SharedPref.putString("user", response.body().getData().getUser().getName());
                    startMainScreen(token);*/

                } else {
                    Toast.makeText(LoginActivity.this, "User not found..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
