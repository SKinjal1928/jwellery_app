package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ExpenseAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityDashboardBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityLoginBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.DeliveryVoucherReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.DeliveryVoucherResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    RetroInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        binding.viewStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, StockListActivity.class));
            }
        });
        binding.viewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ProductInfoActivity.class));
            }
        });
        binding.viewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ExpenseListActivity.class));
            }
        });


        if (NetworkUtils.isInternetAvailable(DashboardActivity.this)) {
            getDeliveryVoucher();
        } else {
            Toast.makeText(DashboardActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDeliveryVoucher() {
        DeliveryVoucherReq req =new DeliveryVoucherReq("1" , SharedPref.getString("user_id", ""));
        Call<DeliveryVoucherResponse> call1 = apiInterface.getVoucher("Bearer " +
                        SharedPref.getString("token", "") + "",
                req);
        call1.enqueue(new Callback<DeliveryVoucherResponse>() {
            @Override
            public void onResponse(Call<DeliveryVoucherResponse> call, Response<DeliveryVoucherResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Delivery voucher List", response.code() + "");

                if (response.code() == 200) {
                    SharedPref.putString("delivery_id", response.body().getData().getId()+"");
                } else {
                    Toast.makeText(DashboardActivity.this, "Delivery voucher error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeliveryVoucherResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(DashboardActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
