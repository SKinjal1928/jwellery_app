package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ExpenseReportAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.adapter.OrderListAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.DeliveryVoucherPdfGenerator;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityExpenseReportBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivitySalesListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpensePaginateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpensePaginateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseReport;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListPaginateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderPaginateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseReportActivity  extends AppCompatActivity {
    ActivityExpenseReportBinding binding;
    Map<String, String> expenseMap = new HashMap<>();
    RetroInterface apiInterface;
    List<ExpensePaginateResponse.List> expenseReportList;
    private ExpenseReportAdapter expenseReportAdapter;
    private int currentPage = 1;
    private final int limit = 20;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseReportBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        expenseReportList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseReportAdapter = new ExpenseReportAdapter(this, expenseReportList, new ExpenseReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

        });
        binding.recyclerView.setAdapter(expenseReportAdapter);

        if (NetworkUtils.isInternetAvailable(ExpenseReportActivity.this)) {
//            getOrderReportListing();
            getExpenseByPaginate(currentPage);
//            getOrderByPaginate(start_date, end_date, currentPage);
        } else {
            Toast.makeText(ExpenseReportActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getExpenseByPaginate(currentPage);
                }
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getExpenseByPaginate(int page) {
        binding.progressBar.setVisibility(View.VISIBLE);
        isLoading = true;

        ExpensePaginateReq req = new ExpensePaginateReq(SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""),
                 page, limit);
        Call<ExpensePaginateResponse> callOrderPaginate = apiInterface.getExpensePaginateList(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        callOrderPaginate.enqueue(new Callback<ExpensePaginateResponse>() {
            @Override
            public void onResponse(Call<ExpensePaginateResponse> call, Response<ExpensePaginateResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Order List", response.code() + "");


                if (response.code() == 200) {
                    Log.d("size Order List", response.body().getData().getList().size() + "");

                    if (page == 1) {
                        expenseReportList.clear();
                    }
                    if (response.body().getData().getList() != null && !response.body().getData().getList().isEmpty()) {
                        expenseReportList.addAll(response.body().getData().getList());
                        expenseReportAdapter = new ExpenseReportAdapter(ExpenseReportActivity.this,
                                expenseReportList, new ExpenseReportAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                getOrderReportListing();
                            }
                        });
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseReportActivity.this));
                        binding.recyclerView.setAdapter(expenseReportAdapter);
                        currentPage++;
                        isLoading = false;
                    }else {
                        isLoading = true; // prevent further unnecessary calls
                    }

                } else {
                    isLoading = false;
                    Toast.makeText(ExpenseReportActivity.this, "Expense error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ExpensePaginateResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                isLoading = false;

                Toast.makeText(ExpenseReportActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOrderReportListing() {
        binding.progressBar.setVisibility(View.VISIBLE);

        ExpenseListReq req = new ExpenseListReq( SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""));
        Call<ExpenseReport> call1 = apiInterface.expenseReportPdf(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        call1.enqueue(new Callback<ExpenseReport>() {
            @Override
            public void onResponse(Call<ExpenseReport> call, Response<ExpenseReport> response) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Expense List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size Expense_report List", response.body().getData().size() + "");
                    try {

                        DeliveryVoucherPdfGenerator.generateExpenseReportPdf(ExpenseReportActivity.this,
                                response.body().getData());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(ExpenseReportActivity.this, "Expense Report error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ExpenseReport> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ExpenseReportActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDeleteClickEvent(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteExpenseFromListId(expenseReportList.get(position).getId()+"", position);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void deleteExpenseFromListId(String id, int position){

        Log.e("Token", SharedPref.getString("token", "")+"");
        Call<ResponseBody> call = apiInterface.deleteExpense("Bearer "+SharedPref.getString("token", ""),
                id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle success
                Log.e("response delete", "success"+response.isSuccessful());

                if(response.isSuccessful()){
                    expenseReportList.remove(position);
                    expenseReportAdapter.notifyItemRemoved(position);
                    Toast.makeText(ExpenseReportActivity.this, "Expense deleted success..", Toast.LENGTH_SHORT).show();

                }else {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(ExpenseReportActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExpenseReportActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ExpenseReportActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e("response delete", "failure");
            }
        });

    }

}