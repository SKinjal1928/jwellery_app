package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityAddExpenseBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityProductInfoBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.Expense;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseRequest;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseUpdateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseUpdateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.LoginResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseActivity extends AppCompatActivity {
    ActivityAddExpenseBinding binding;
    RetroInterface apiInterface;
    ExpenseListResponse.Datum expense;
    List<ExpenseRequest.ExpenseItem> expenses = new ArrayList<>();
    ExpenseUpdateReq expenses_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddExpenseBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        SharedPref.init(this);

        expense = (ExpenseListResponse.Datum) getIntent().getSerializableExtra("expense");
        if(expense != null){
            binding.etPlaceLodging.setText(expense.getNamePlace()+"");
            binding.editTextDate.setText(expense.getDate().toString()+"");
            binding.etAmtLodging.setText(expense.getLodging().toString()+"");
            binding.etAmtTravelling.setText(expense.getTravelling().toString()+"");
            binding.etAmtFood.setText(expense.getFood().toString()+"");
            binding.emOther.setText(expense.getOther().toString()+"");
            binding.btnNext.setText("Update Expense");
            binding.txtTitle.setText("Update Expense");
        }

        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkUtils.isInternetAvailable(ExpenseActivity.this)) {
                    if(binding.btnNext.getText().toString().contains("Update")){
                        updateExpenseApi();
                    }else {
                        sendExpenseApi();
                    }

                } else {
                    Toast.makeText(ExpenseActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDatePickerDialog(binding.editTextDate);
            }
        });
    }


    public void sendExpenseApi(){
        expenses.add(new ExpenseRequest.ExpenseItem(binding.etPlaceLodging.getText().toString(),
                binding.editTextDate.getText().toString(), binding.etAmtLodging.getText().toString(),
                binding.etAmtTravelling.getText().toString(), binding.etAmtFood.getText().toString(),
                binding.emOther.getText().toString()));
        ExpenseRequest request = new ExpenseRequest(Integer.parseInt(SharedPref.getString("delivery_id", "")),
                Integer.parseInt(SharedPref.getString("user_id", "")), expenses);

        Log.e("Token", SharedPref.getString("token", "")+"");
        Call<ResponseBody> call = apiInterface.sendExpenseApi("Bearer "+SharedPref.getString("token", ""),
                request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle success
                Log.e("response", "success"+response.isSuccessful());
                if(response.isSuccessful()){
                    Toast.makeText(ExpenseActivity.this, "Expense added success..", Toast.LENGTH_SHORT).show();
                    finish();
                }
                /*String responseBody = response.body().toString();
                Log.d("API_RESPONSE", responseBody);*/

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle failure
                Log.e("response", "failure");
            }
        });

    }

    public void updateExpenseApi(){
        expenses_update = new ExpenseUpdateReq(expense.getId().toString(),binding.etPlaceLodging.getText().toString(),
                binding.editTextDate.getText().toString(), binding.etAmtLodging.getText().toString(),
                binding.etAmtTravelling.getText().toString(), binding.etAmtFood.getText().toString(),
                binding.emOther.getText().toString());

//        ExpenseUpdateReq request = new ExpenseUpdateReq(Integer.parseInt(SharedPref.getString("delivery_id", "")),
//                Integer.parseInt(SharedPref.getString("user_id", "")), expenses_update);

        Log.e("Token", SharedPref.getString("token", "")+"");
        Call<ExpenseUpdateResponse> call = apiInterface.updateExpenseApi("Bearer "+SharedPref.getString("token", ""),
                expenses_update);
        call.enqueue(new Callback<ExpenseUpdateResponse>() {
            @Override
            public void onResponse(Call<ExpenseUpdateResponse> call, Response<ExpenseUpdateResponse> response) {
                // Handle success
                Log.e("response update", "success"+response.isSuccessful());
                if(response.isSuccessful()){
                    Toast.makeText(ExpenseActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(ExpenseActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ExpenseActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(ExpenseActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                /*String responseBody = response.body().toString();
                Log.d("API_RESPONSE", responseBody);*/

            }

            @Override
            public void onFailure(Call<ExpenseUpdateResponse> call, Throwable t) {
                // Handle failure
                Log.e("response update", "failure");
            }
        });

    }

    private void showOrderDatePickerDialog(TextView txtDate) {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create the DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ExpenseActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format the selected date
                        String date = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth, monthOfYear + 1, year);
                        // Set the selected date to the TextView
                        txtDate.setText(date);
                    }
                },
                year, month, dayOfMonth);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

}
