package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityLoginBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityProductInfoBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;

public class InvoiceGenerateActivity extends AppCompatActivity {

    ActivityProductInfoBinding binding;
    RetroInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InvoiceGenerateActivity.this, InvoiceDetailActivity.class));
            }
        });

    }

}
