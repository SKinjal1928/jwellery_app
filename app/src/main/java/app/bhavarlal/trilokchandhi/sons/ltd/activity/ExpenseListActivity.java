package app.bhavarlal.trilokchandhi.sons.ltd.activity;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ExpenseAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityExpenseListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseListActivity extends AppCompatActivity {
    ActivityExpenseListBinding binding;
    Map<String, String> expenseMap = new HashMap<>();
    RetroInterface apiInterface;
    List<ExpenseListResponse.Datum> expenseList;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        expenseList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseAdapter = new ExpenseAdapter(this, expenseList, new ExpenseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {

            }
        });
        binding.recyclerView.setAdapter(expenseAdapter);

        if (NetworkUtils.isInternetAvailable(ExpenseListActivity.this)) {
            getExpenseListing();
        } else {
            Toast.makeText(ExpenseListActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }

        binding.btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ExpenseListActivity.this, ExpenseActivity.class);
                startActivity(i);
            }
        });


}
    private void getExpenseListing() {
//        binding.progressBar.setVisibility(View.VISIBLE);

        ExpenseListReq req = new ExpenseListReq( SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""));
        Call<ExpenseListResponse> call1 = apiInterface.getExpenseList(
                "Bearer " + SharedPref.getString("token", "") + "",req
               );
        call1.enqueue(new Callback<ExpenseListResponse>() {
            @Override
            public void onResponse(Call<ExpenseListResponse> call, Response<ExpenseListResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Expense List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size Expense List", response.body().getData().size() + "");
                    expenseList.addAll(response.body().getData());
                    expenseAdapter = new ExpenseAdapter(ExpenseListActivity.this,
                            expenseList, new ExpenseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i = new Intent(ExpenseListActivity.this, ExpenseActivity.class);
                            i.putExtra("expense", expenseList.get(position));
                            startActivity(i);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            onDeleteClickEvent(position);
                        }
                    });
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(ExpenseListActivity.this));
                    binding.recyclerView.setAdapter(expenseAdapter);
                } else {
                    Toast.makeText(ExpenseListActivity.this, "Expense error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ExpenseListResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ExpenseListActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onDeleteClickEvent(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteExpenseFromListId(expenseList.get(position).getId()+"", position);

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
                    expenseList.remove(position);
                    expenseAdapter.notifyItemRemoved(position);
                    Toast.makeText(ExpenseListActivity.this, "Expense deleted success..", Toast.LENGTH_SHORT).show();

                }else {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(ExpenseListActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExpenseListActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ExpenseListActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
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
