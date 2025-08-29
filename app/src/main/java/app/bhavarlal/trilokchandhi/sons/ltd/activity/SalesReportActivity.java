package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.bhavarlal.trilokchandhi.sons.ltd.adapter.OrderListAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.DeliveryVoucherPdfGenerator;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivitySalesListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListPaginateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderPaginateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderPdfReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderPdfResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesReportActivity extends AppCompatActivity {
    ActivitySalesListBinding binding;
    RetroInterface apiInterface;
    List<OrderListPaginateResponse.List> orderList;
    private OrderListAdapter orderAdapter;
    String today_date, start_date = null, end_date = null;
    private int currentPage = 1;
    private final int limit = 20;
    private boolean isLoading = false;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySalesListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        orderList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderListAdapter(this, orderList, new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onPdfClick(int position) {

            }
        });
        binding.recyclerView.setAdapter(orderAdapter);

        if (NetworkUtils.isInternetAvailable(SalesReportActivity.this)) {
//            getOrderListing();
            getOrderByPaginate(start_date, end_date, currentPage);
        } else {
            Toast.makeText(SalesReportActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }


        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    isLoading = true;
                    getOrderByPaginate(start_date, end_date, currentPage);
                }
            }
        });

        FilterDataListener();

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void FilterDataListener() {
        binding.btnAll.setOnClickListener(v -> {
            binding.dateRangeLayout.setVisibility(View.GONE);
            start_date = null;
            end_date = null;
            currentPage = 1;
            getOrderByPaginate(start_date, end_date, currentPage);
//            fetchData(currentPage, null, null);
        });

        binding.btnToday.setOnClickListener(v -> {
            binding.dateRangeLayout.setVisibility(View.GONE);
            String today = sdf.format(new Date());
            start_date = today;
            end_date = today;
            currentPage = 1;
            getOrderByPaginate(today, today, currentPage);
//            fetchData(currentPage, today, today);
        });

        binding.btnDateRange.setOnClickListener(v -> binding.dateRangeLayout.setVisibility(View.VISIBLE));

        binding.tvStartDate.setOnClickListener(v -> showDatePicker((date) -> {
            start_date = date;
            binding.tvStartDate.setText(date);
            maybeFetchDateRange();
        }));

        binding.tvEndDate.setOnClickListener(v -> showDatePicker((date) -> {
            end_date = date;
            binding.tvEndDate.setText(date);
            maybeFetchDateRange();
        }));


    }

    private void showDatePicker(DateCallback callback) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dp = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selected = Calendar.getInstance();
            selected.set(year, month, dayOfMonth);
            callback.onDateSelected(sdf.format(selected.getTime()));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dp.show();
    }

    private void maybeFetchDateRange() {
        if (start_date != null && end_date != null) {
            currentPage = 1;
            getOrderByPaginate(start_date, end_date, currentPage);
//            fetchData(currentPage, startDate, endDate);
        }
    }

    interface DateCallback {
        void onDateSelected(String date);
    }

    private void getOrderByPaginate(String start_date, String end_date, int page) {
        binding.progressBar.setVisibility(View.VISIBLE);
        isLoading = true;

        OrderPaginateReq req = new OrderPaginateReq(SharedPref.getString("user_id", ""),
                today_date, start_date, end_date, page, limit);
        Call<OrderListPaginateResponse> callOrderPaginate = apiInterface.getOrderPaginateList(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        callOrderPaginate.enqueue(new Callback<OrderListPaginateResponse>() {
            @Override
            public void onResponse(Call<OrderListPaginateResponse> call, Response<OrderListPaginateResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Order List", response.code() + "");


                if (response.code() == 200) {
                    Log.d("size Order List", response.body().getData().getList().size() + "");

                    if (page == 1) {
                        orderList.clear();
                    }

                    if (response.body().getData().getList() != null && !response.body().getData().getList().isEmpty()) {
                        orderList.addAll(response.body().getData().getList());

                        /*Collections.sort(orderList, new Comparator<OrderListPaginateResponse.List>() {
                            @Override
                            public int compare(OrderListPaginateResponse.List b1, OrderListPaginateResponse.List b2) {
                                return b1.getId().toString().compareToIgnoreCase(b2.getId().toString());
                            }
                        });*/

                        orderAdapter = new OrderListAdapter(SalesReportActivity.this,
                                orderList, new OrderListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                          /*  Intent i = new Intent(SalesReportActivity.this, ProductInfoActivity.class);
                            i.putExtra("order", orderList.get(position));
                            startActivity(i);*/
                            }

                            @Override
                            public void onPdfClick(int position) {
//                                if(orderList.get(position).getModeOfPayment().equals("GST")) {
                                    onPDFClickEvent(position);
//                                }else{

//                                }
                            }
                        });
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(SalesReportActivity.this));
                        binding.recyclerView.setAdapter(orderAdapter);
                        currentPage++;
                        isLoading = false;
                    }else {
                        isLoading = true; // prevent further unnecessary calls
                    }


                } else {
                    isLoading = false;
                    Toast.makeText(SalesReportActivity.this, "Expense error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderListPaginateResponse> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                isLoading = false;

                Toast.makeText(SalesReportActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onPDFClickEvent(int position) {
        getOrderPdfData(orderList.get(position).getId() + "");
    }

   /* private void getOrderListing() {
//        binding.progressBar.setVisibility(View.VISIBLE);

        ExpenseListReq req = new ExpenseListReq( SharedPref.getString("delivery_id", ""),
                SharedPref.getString("user_id", ""));
        Call<OrderListResponse> call1 = apiInterface.getOrderList(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        call1.enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Expense List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size Expense List", response.body().getData().size() + "");
                    orderList.addAll(response.body().getData());
                    orderAdapter = new OrderListAdapter(SalesListingActivity.this,
                            orderList, new OrderListAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent i = new Intent(SalesListingActivity.this, ProductInfoActivity.class);
                            i.putExtra("order", orderList.get(position));
                            startActivity(i);
                        }

                        @Override
                        public void onDeleteClick(int position) {
                            onDeleteClickEvent(position);
                        }
                    });
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(SalesListingActivity.this));
                    binding.recyclerView.setAdapter(orderAdapter);
                } else {
                    Toast.makeText(SalesListingActivity.this, "Expense error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SalesListingActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    public void onDeleteClickEvent(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete this expense?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    deleteExpenseFromListId(orderList.get(position).getId() + "", position);

                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void deleteExpenseFromListId(String id, int position) {

        Log.e("Token", SharedPref.getString("token", "") + "");
        Call<ResponseBody> call = apiInterface.deleteExpense("Bearer " + SharedPref.getString("token", ""),
                id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                // Handle success
                Log.e("response delete", "success" + response.isSuccessful());

                if (response.isSuccessful()) {
                    orderList.remove(position);
                    orderAdapter.notifyItemRemoved(position);
                    Toast.makeText(SalesReportActivity.this, "Expense deleted success..", Toast.LENGTH_SHORT).show();

                } else {
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(SalesReportActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SalesReportActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SalesReportActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
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

    private void getOrderPdfData(String id) {
//        binding.progressBar.setVisibility(View.VISIBLE);
        String fileName = "delivery_voucher_" + System.currentTimeMillis() + ".pdf";
        File pdfFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName);
        String filePath = pdfFile.getAbsolutePath();

        OrderPdfReq req = new OrderPdfReq(id);
        Call<OrderPdfResponse> call1 = apiInterface.orderPdfReport(
                "Bearer " + SharedPref.getString("token", "") + "", req);
        call1.enqueue(new Callback<OrderPdfResponse>() {
            @Override
            public void onResponse(Call<OrderPdfResponse> call, Response<OrderPdfResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG order_pdf List", response.code() + "");

                if (response.code() == 200) {
                    try {
                        DeliveryVoucherPdfGenerator.generatePdf(SalesReportActivity.this,
                                response.body().getData());
//                        openPdfFile(SalesListingActivity.this, pdfFile);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(SalesReportActivity.this, "order_pdf error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<OrderPdfResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(SalesReportActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openPdfFile(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".provider",
                file
        );

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            context.startActivity(Intent.createChooser(intent, "Open PDF with"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No PDF viewer installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
