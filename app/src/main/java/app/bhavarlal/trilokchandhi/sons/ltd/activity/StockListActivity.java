package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ExpenseAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.adapter.StockAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityExpenseListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityStockListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockListActivity extends AppCompatActivity {
    ActivityStockListBinding binding;
    Map<String, String> expenseMap = new HashMap<>();
    RetroInterface apiInterface;
    List<StockResponse.Datum> stockList;
    private StockAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStockListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        stockList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stockAdapter = new StockAdapter(this, stockList);
        binding.recyclerView.setAdapter(stockAdapter);

        if (NetworkUtils.isInternetAvailable(StockListActivity.this)) {
            getProductItems();
        } else {
            Toast.makeText(StockListActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getProductItems() {
        ExpenseListReq req = new ExpenseListReq( SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""));
        Call<StockResponse> call1 = apiInterface.getDeliveryVoucherStock(
                "Bearer " + SharedPref.getString("token", "") + "", req
        );
        call1.enqueue(new Callback<StockResponse>() {
            @Override
            public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Customer List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size stock List", response.body().getData().size() + "");
                    stockList.clear();
                    stockList.addAll(response.body().getData());
                    binding.recyclerView.setAdapter(stockAdapter);
                    stockAdapter.notifyDataSetChanged();

                    // Sort alphabetically by name
                    Collections.sort(stockList, new Comparator<StockResponse.Datum>() {
                        @Override
                        public int compare(StockResponse.Datum b1, StockResponse.Datum b2) {
                            return b1.getProductName().compareToIgnoreCase(b2.getProductName());
                        }
                    });
                    stockAdapter.setStockList(stockList);


                } else {
                    Toast.makeText(StockListActivity.this, "Stock retrieve error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(StockListActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
