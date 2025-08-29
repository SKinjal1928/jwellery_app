package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.SalesAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivitySalesBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.fragment.ProductInfoBottomSheet;
import app.bhavarlal.trilokchandhi.sons.ltd.model.CustomerResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesListActivity extends AppCompatActivity {
    ActivitySalesBinding binding;
    Map<String, String> expenseMap = new HashMap<>();
    RetroInterface apiInterface;
    List<OrderListResponse.Datum> orderList;
    private SalesAdapter orderAdapter;
    String today_date, start_date = null, end_date = null;
    private int currentPage = 1;
    private final int limit = 20;
    private boolean isLoading = false;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalesBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        orderList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new SalesAdapter(this, orderList, new SalesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });
        binding.recyclerView.setAdapter(orderAdapter);

        if (NetworkUtils.isInternetAvailable(SalesListActivity.this)) {
            getOrderListing();
//            getOrderByPaginate(start_date, end_date, currentPage);
        } else {
            Toast.makeText(SalesListActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }

        binding.btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ProductInfoBottomSheet.newInstance().show(getSupportFragmentManager(), "ProductInfoBottomSheet");

                Intent i = new Intent(SalesListActivity.this, ProductInfoActivity.class);
                OrderHolder.clear();
                startActivity(i);
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDatePicker(SalesListActivity.DateCallback callback) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            callback.onDateSelected(sdf.format(selected.getTime()));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    interface DateCallback {
        void onDateSelected(String date);
    }

    private void getOrderListing() {
        binding.progressBar.setVisibility(View.VISIBLE);

        ExpenseListReq req = new ExpenseListReq( SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""));
        Call<OrderListResponse> call1 = apiInterface.getOrderList(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        call1.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Expense List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size Expense List", response.body().getData().size() + "");
                    orderList.addAll(response.body().getData());

                    Collections.sort(orderList, new Comparator<OrderListResponse.Datum>() {
                        @Override
                        public int compare(OrderListResponse.Datum b1, OrderListResponse.Datum b2) {
                            return b1.getId().toString().compareToIgnoreCase(b2.getId().toString());
                        }
                    });

//                    customerList.addAll(customerList);
//                    adapter.notifyDataSetChanged();
                    orderAdapter = new SalesAdapter(SalesListActivity.this,
                            orderList, new SalesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i = new Intent(SalesListActivity.this, EditInvoiceByPartActivity.class);
                            OrderHolder.setOrder(orderList.get(position)); // <- Set it before launching

                            i.putExtra("order", orderList.get(position));
                            startActivity(i);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            onDeleteClickEvent(position);
                        }
                    });
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(SalesListActivity.this));
                    binding.recyclerView.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(SalesListActivity.this, "Expense error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SalesListActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDeleteClickEvent(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteExpenseFromListId(orderList.get(position).getId()+"", position);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void deleteExpenseFromListId(String id, int position){
        binding.progressBar.setVisibility(View.VISIBLE);
        Log.e("Token", SharedPref.getString("token", "")+"");
        Call<ResponseBody> call = apiInterface.deleteExpense("Bearer "+SharedPref.getString("token", ""),
                id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle success
                Log.e("response delete", "success"+response.isSuccessful());
                binding.progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    orderList.remove(position);
                    orderAdapter.notifyItemRemoved(position);
                    Toast.makeText(SalesListActivity.this, "Expense deleted success..", Toast.LENGTH_SHORT).show();

                }else {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(SalesListActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SalesListActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SalesListActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e("response delete", "failure");
                binding.progressBar.setVisibility(View.GONE);
            }
        });

    }

}
