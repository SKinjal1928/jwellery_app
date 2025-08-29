package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import app.bhavarlal.trilokchandhi.sons.ltd.OnProductListSubmittedListener;
import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ItemListAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityProductInfoBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.fragment.FinalDetailsBottomSheet;
import app.bhavarlal.trilokchandhi.sons.ltd.fragment.InvoiceDetailBottomSheet;
import app.bhavarlal.trilokchandhi.sons.ltd.model.CustomerResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoActivity extends AppCompatActivity {
    ActivityProductInfoBinding binding;
    RetroInterface apiInterface;
    List<CustomerResponse.Datum> customerList;
    ArrayAdapter<CustomerResponse.Datum> adapter;
    String customer_id = "";
    private boolean isItemSelectedManually = false;
    //    OrderListResponse.Datum datum;
    OrderListResponse.Datum order;
    ArrayList<OrderGenerateReq.Product> productListMain;
    private ItemListAdapter productAdapter;
    Double l_amt_total = 0.0, fine_total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);
        customerList = new ArrayList<>();
        productListMain = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        if (OrderHolder.getOrder() != null) {
//           datum = (OrderListResponse.Datum) getIntent().getSerializableExtra("order");
            binding.btnNext.setText("Save & Update");
            binding.llRv.setVisibility(View.GONE);
            order = OrderHolder.getOrder();
            binding.etDate.setText(order.getOrderDateDmy());
            binding.searchAutoComplete.setText(order.getCustomer().getName());
            binding.txtAddress.setText(order.getCustomer().getAddress());
            binding.txtPhone.setText(order.getCustomer().getContact());

        } else {
            binding.etDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        }
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.btnNext.getText().toString().contains("Update")) {
                    order.getCustomer().setName(binding.searchAutoComplete.getText().toString());
                    order.getCustomer().setAddress(binding.txtAddress.getText().toString());
                    order.getCustomer().setContact(binding.txtPhone.getText().toString());
                    order.setOrderDateDmy(binding.etDate.getText().toString());
                    OrderHolder.setOrder(order); // Optional: re-save if object reference is replaced

                    finish();
                } else {

                    Intent i;

                    if ((OrderListResponse.Datum) getIntent().getSerializableExtra("order") != null) {
                        Log.e("list_order", ((OrderListResponse.Datum) getIntent().getSerializableExtra("order")).getOrderProduct().size() + "");
                        if (((OrderListResponse.Datum) getIntent().getSerializableExtra("order")).getOrderProduct().size() > 1) {
                            i = new Intent(ProductInfoActivity.this, EditInvoiceByPartActivity.class);
                            i.putExtra("date", binding.etDate.getText().toString());
                            i.putExtra("customer", binding.searchAutoComplete.getText().toString());
                            i.putExtra("customer_id", customer_id);
                            i.putExtra("address", binding.txtAddress.getText().toString());
                            i.putExtra("order", (OrderListResponse.Datum) getIntent().getSerializableExtra("order"));
                    startActivity(i);

                        } else {

                            InvoiceDetailBottomSheet sheet = InvoiceDetailBottomSheet.newInstance(
                                            binding.etDate.getText().toString(),
                                            binding.searchAutoComplete.getText().toString(), customer_id,
                                            binding.txtAddress.getText().toString(),
                                            (OrderListResponse.Datum) getIntent().getSerializableExtra("order"),
                                            0);
                            sheet.setOnProductListSubmittedListener(new OnProductListSubmittedListener() {
                                @Override
                                public void onProductListSubmitted(ArrayList<OrderGenerateReq.Product> productList) {
                                    Log.d("Activity", "Received product list on dismiss: " + productList.size());
                                    productListMain.addAll(productList);
                                    Log.d("Activity", "on dismiss: " + productListMain.size());
                                }
                            });
                            sheet.show(getSupportFragmentManager(), InvoiceDetailBottomSheet.TAG);

                          /*  i = new Intent(ProductInfoActivity.this, InvoiceDetailActivity.class);
                            i.putExtra("date", binding.etDate.getText().toString());
                            i.putExtra("customer", binding.searchAutoComplete.getText().toString());
                            i.putExtra("customer_id", customer_id);
                            i.putExtra("address", binding.txtAddress.getText().toString());
                            i.putExtra("order", (OrderListResponse.Datum) getIntent().getSerializableExtra("order"));

                            i.putExtra("position", 0);*/
                        }
                    } else {
                        fine_total = 0.0;
                        l_amt_total = 0.0;
                        for (int j = 0; j < productListMain.size(); j++) {
                            /*fin_total = fine_total + old_fine(from finalDetailsActivity)*/
                            fine_total = fine_total + Double.parseDouble(productListMain.get(j).fine);
                            l_amt_total = l_amt_total + Double.parseDouble(productListMain.get(j).laboure_amount);
                        }

                        Bundle bundle = new Bundle();
                        bundle.putString("date", binding.etDate.getText().toString());
                        bundle.putString("customer", binding.searchAutoComplete.getText().toString());
                        bundle.putString("address", binding.txtAddress.getText().toString());
                        bundle.putString("customer_id", customer_id);
                        bundle.putParcelableArrayList("products", productListMain);
                        bundle.putDouble("fine", fine_total);
                        bundle.putDouble("laboure_amount", l_amt_total);


                        FinalDetailsBottomSheet sheet = FinalDetailsBottomSheet.newInstance(
                                bundle);
                        sheet.show(getSupportFragmentManager(), FinalDetailsBottomSheet.TAG);

                      /*  i = new Intent(ProductInfoActivity.this, InvoiceDetailActivity.class);
                        i.putExtra("date", binding.etDate.getText().toString());
                        i.putExtra("customer", binding.searchAutoComplete.getText().toString());
                        i.putExtra("customer_id", customer_id);
                        i.putExtra("address", binding.txtAddress.getText().toString());

                        i.putExtra("position", 0);*/
                    }
//                    startActivity(i);
                }
            }
        });
        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InvoiceDetailBottomSheet sheet = InvoiceDetailBottomSheet.newInstance(binding.etDate.getText().toString(),
                        binding.searchAutoComplete.getText().toString(), customer_id,
                        binding.txtAddress.getText().toString(),
                        null,
                        0);

                sheet.setOnProductListSubmittedListener(new OnProductListSubmittedListener() {
                    @Override
                    public void onProductListSubmitted(ArrayList<OrderGenerateReq.Product> productList) {
                        Log.d("Activity", "Received product list on dismiss: " + productList.size());
                        productListMain.addAll(productList);
                        Log.d("Activity", "on dismiss: " + productListMain.size());
                        productAdapter = new ItemListAdapter(ProductInfoActivity.this,
                                productListMain, new ItemListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

//                                        openBottomUpForEditDetails(position);
                            }

                            @Override
                            public void onDeleteItemClick(int position) {
                                productListMain.remove(position);
                                productAdapter.notifyItemRemoved(position);
                            }

                        });
                        binding.rvListItem.setLayoutManager(new LinearLayoutManager(ProductInfoActivity.this));
                        binding.rvListItem.setAdapter(productAdapter);

                    }
                });
                sheet.show(getSupportFragmentManager(), InvoiceDetailBottomSheet.TAG);
            }
        });
        binding.etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDatePickerDialog(binding.etDate);
            }
        });
//        binding.searchAutoComplete.setDropDownVerticalOffset(200); // moves dropdown lower


        binding.searchAutoComplete.setOnClickListener(v -> binding.searchAutoComplete.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        binding.searchAutoComplete.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> binding.searchAutoComplete.showDropDown(), 100);
            }
        });

        binding.searchAutoComplete.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 300; // milliseconds


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timer.cancel(); // cancel previous timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            String query = charSequence.toString().trim();
                            if (!query.isEmpty() && !isItemSelectedManually) {
                                List<CustomerResponse.Datum> filteredUsers = getFilteredUsers(query);
                                Log.d("DEBUG", "Filtered size: " + filteredUsers.size());

                                adapter.clear();
                                adapter.addAll(filteredUsers);
                                adapter.notifyDataSetChanged();
//                                binding.searchAutoComplete.setAdapter(adapter);


                                binding.searchAutoComplete.showDropDown();

                            }
                            isItemSelectedManually = false; // Reset
                        });
                    }
                }, DELAY);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter = new ArrayAdapter<CustomerResponse.Datum>(ProductInfoActivity.this,
                R.layout.item_dropdown, customerList);

        // Set adapter to AutoCompleteTextView
        binding.searchAutoComplete.setAdapter(adapter);

        // Set threshold for when suggestions will show (1 char here)
        binding.searchAutoComplete.setThreshold(1);

        // Handle item click
        binding.searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            isItemSelectedManually = true; // Prevent dropdown from reappearing
            CustomerResponse.Datum selectedProduct = adapter.getItem(position);
            customer_id = selectedProduct.getId() + "";
            binding.txtAddress.setText(selectedProduct.getAddress() + "");
            binding.txtPhone.setText(selectedProduct.getContact() + "");
//            Toast.makeText(ProductInfoActivity.this, "Selected: " + selectedProduct.getName(), Toast.LENGTH_SHORT).show();
        });
        if (NetworkUtils.isInternetAvailable(ProductInfoActivity.this)) {
            getCustomers();
        } else {
            Toast.makeText(ProductInfoActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }
    }

    // Sample mock filtering logic
    private List<String> getFilteredResults(String query) {
        String[] allItems = {"India", "Indonesia", "Iceland", "Ireland", "Italy", "Iran"};
        List<String> result = new ArrayList<>();
        for (String item : allItems) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                result.add(item);
            }
        }

        return result;
    }

    // Filter function that searches by name or email
    private List<CustomerResponse.Datum> getFilteredUsers(String query) {
        List<CustomerResponse.Datum> filteredUsers = new ArrayList<>();
        for (CustomerResponse.Datum user : customerList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }


    private void getCustomers() {
//        binding.progressBar.setVisibility(View.VISIBLE);

        Call<CustomerResponse> call1 = apiInterface.getCustomers(
                "Bearer " + SharedPref.getString("token", "") + ""
        );
        call1.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
//                binding.progressBar.setVisibility(View.GONE);
                Log.d("TAG Customer List", response.code() + "");

                if (response.code() == 200) {
                    Log.d("size customer List", response.body().getData().size() + "");
                    customerList.clear();
                    customerList.addAll(response.body().getData());

                    Collections.sort(customerList, new Comparator<CustomerResponse.Datum>() {
                        @Override
                        public int compare(CustomerResponse.Datum b1, CustomerResponse.Datum b2) {
                            return b1.getName().compareToIgnoreCase(b2.getName());
                        }
                    });
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(ProductInfoActivity.this, "Customer retrieve error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductInfoActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
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
                ProductInfoActivity.this,
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
