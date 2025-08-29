package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ItemProductAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityProductListBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListingActivity extends AppCompatActivity {
    ActivityProductListBinding binding;
    RetroInterface apiInterface;
    List<OrderListResponse.OrderProduct> orderList;
    private ItemProductAdapter productAdapter;
    private static final int REQUEST_EDIT_PRODUCT = 101;
    String product_id = "", rate_applied = "";
    List<StockResponse.Datum> customerList;
    String[] rateAppliedList = {"PCS", "GW", "Less", "NW", "FW"};
    ArrayList<OrderGenerateReq.Product> productEntryList = new ArrayList<>();
    ArrayAdapter<String> rateAppliedAdapter;
    Double l_amt_total = 0.0, fine_total = 0.0;
    Double old_fine_total = 0.0, old_amt_total = 0.0;
    OrderListResponse.Datum order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        orderList = new ArrayList<>();
        customerList = new ArrayList<>();
        order = OrderHolder.getOrder();
        Log.e("list_order", order.getOrderProduct().size() + "");

        orderList.addAll(order.getOrderProduct());
        productAdapter = new ItemProductAdapter(ProductListingActivity.this,
                orderList, new ItemProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
              /*  InvoiceDetailBottomSheet sheet = InvoiceDetailBottomSheet.newInstance(
                        order.getOrderDateDmy(),
                        order.getCustomer().getName(), order.getCustomerId()+"",
                        order.getCustomer().getAddress(),
                        order,
                        0);
                sheet.show(getSupportFragmentManager(), InvoiceDetailBottomSheet.TAG);*/
//            }

                openBottomUpForEditDetails(position);
            }

        });
        binding.rvListItem.setLayoutManager(new LinearLayoutManager(ProductListingActivity.this));
        binding.rvListItem.setAdapter(productAdapter);

        if (NetworkUtils.isInternetAvailable(ProductListingActivity.this)) {
            getProductItems();
        } else {
            Toast.makeText(ProductListingActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }
        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                finish();
                /*Intent i = new Intent(ProductListingActivity.this, FinalDetailsActivity.class);
                i.putExtra("date", getIntent().getStringExtra("date").toString());
                i.putExtra("customer", getIntent().getStringExtra("customer").toString());
                i.putExtra("customer_id", getIntent().getStringExtra("customer_id").toString());
                i.putExtra("address", getIntent().getStringExtra("address").toString());

                i.putExtra("order", (OrderListResponse.Datum) getIntent().getSerializableExtra("order"));

                i.putExtra("position", 0);
                i.putExtra("laboure_amount", l_amt_total + "");
                i.putExtra("fine", fine_total + "");
                i.putExtra("old_fine", fine_total + "");
                i.putExtra("old_amount", fine_total + "");


                i.putParcelableArrayListExtra("products", productEntryList);

                startActivity(i);*/
            }
        });
        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    boolean isItemSelectedManually = false;
    ArrayAdapter<StockResponse.Datum> adapter;

    private void openBottomUpForEditDetails(int position) {
        isItemSelectedManually = false;
        double labourAmount = 0.0;
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = getLayoutInflater().inflate(R.layout.activity_invoice_info, null);
        bottomSheetDialog.setContentView(sheetView);
        // Get the selected product
        OrderListResponse.OrderProduct selectedProduct = orderList.get(position);

        adapter = new ArrayAdapter<StockResponse.Datum>(ProductListingActivity.this,
                android.R.layout.simple_dropdown_item_1line, customerList);
        // Access views
        AutoCompleteTextView searchItem = sheetView.findViewById(R.id.search_item);
        ImageView add_another_product = sheetView.findViewById(R.id.add_another_product);
        TextInputEditText etPcs = sheetView.findViewById(R.id.etPcs);
        TextInputEditText etGW = sheetView.findViewById(R.id.etGW);
        TextInputEditText etLess = sheetView.findViewById(R.id.etLess);
        TextView tvNW = sheetView.findViewById(R.id.tvNW);
        TextInputEditText etPurity = sheetView.findViewById(R.id.etPurity);
        TextInputEditText etWastage = sheetView.findViewById(R.id.etWastage);
        TextView tvFine = sheetView.findViewById(R.id.tvFine);
        TextInputEditText etLabourRate = sheetView.findViewById(R.id.etLabourRate);
        AutoCompleteTextView searchRateApplied = sheetView.findViewById(R.id.search_rate_applied);
        TextView tvLAmount = sheetView.findViewById(R.id.tvLAmount);
        TextView btnNext = sheetView.findViewById(R.id.btn_next);
        searchRateAppliedListener(searchRateApplied, tvLAmount, etLabourRate, etPcs, etGW, etLess,
                tvNW, tvFine);

        add_another_product.setVisibility(View.GONE);
        // Set values
        searchItem.setText(selectedProduct.getProductDetails().getProductName());
        etPcs.setText(String.valueOf(formatClean(selectedProduct.getQuantity())));
        etGW.setText(String.format(Locale.US, "%.3f",Double.parseDouble(selectedProduct.getGrossWeight())));
        etLess.setText(String.format(Locale.US, "%.3f",Double.parseDouble(selectedProduct.getLessWeight())));
        tvNW.setText(String.format(Locale.US, "%.3f",Double.parseDouble(String.valueOf(Double.parseDouble(selectedProduct.getGrossWeight()) -
                Double.parseDouble(selectedProduct.getLessWeight())))));
        etPurity.setText(String.valueOf(selectedProduct.getPurity()));
        etWastage.setText(String.valueOf(selectedProduct.getWastage()));
        tvFine.setText(String.format(Locale.US, "%.3f",Double.parseDouble(String.valueOf(selectedProduct.getFine()))));
        etLabourRate.setText(Math.round(Double.parseDouble(selectedProduct.getLaboureRate()))+"");
        searchRateApplied.setText(selectedProduct.getRateOn());
        tvLAmount.setText(Math.round(Double.parseDouble(String.valueOf(selectedProduct.getLaboureAmount())))+"");

        btnNext.setOnClickListener(v -> {
            try {
                // Read updated values from fields
                int pcs = Integer.parseInt(etPcs.getText().toString().trim());
                double gw = Double.parseDouble(etGW.getText().toString().trim());
                double less = Double.parseDouble(etLess.getText().toString().trim());
                double purity = Double.parseDouble(etPurity.getText().toString().trim());
                double wastage = Double.parseDouble(etWastage.getText().toString().trim());
                double labourRate = Double.parseDouble(etLabourRate.getText().toString().trim());
                String rateOn = searchRateApplied.getText().toString().trim();

                // Calculated values
                double nw = gw - less;
                double fine = nw * (purity + wastage) / 100;
//                double labourAmount = labourRate * nw;


                // Update object in list
                OrderListResponse.OrderProduct product = orderList.get(position);
                product.setQuantity(pcs + "");
                product.setGrossWeight(gw + "");
                product.setLessWeight(less + "");
                product.setPurity(purity + "");
                product.setWastage(wastage + "");
                product.setLaboureRate(labourRate + "");
                product.setRateOn(rateOn + "");
                product.setFine(fine + "");
                product.setLaboureAmount(Math.round(calculateAndDisplayValues(searchRateApplied.getText().toString(), etPcs, etGW, etLess, etPurity, etWastage, etLabourRate, tvNW, tvFine,
                        tvLAmount))+"");

                order.getOrderProduct().get(position).setQuantity(pcs + "");
                order.getOrderProduct().get(position).setGrossWeight(gw + "");
                order.getOrderProduct().get(position).setLessWeight(less + "");
                order.getOrderProduct().get(position).setPurity(purity + "");
                order.getOrderProduct().get(position).setWastage(wastage + "");
                order.getOrderProduct().get(position).setLaboureRate(labourRate + "");
                order.getOrderProduct().get(position).setRateOn(rateOn + "");
                order.getOrderProduct().get(position).setFine(fine + "");
                order.getOrderProduct().get(position).setLaboureAmount(Math.round(calculateAndDisplayValues(searchRateApplied.getText().toString(), etPcs, etGW, etLess, etPurity, etWastage, etLabourRate, tvNW, tvFine,
                        tvLAmount))+"");

                OrderHolder.setOrder(order);

                // Notify adapter
                productAdapter.notifyItemChanged(position);

                // Close bottom sheet
                bottomSheetDialog.dismiss();

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
            }
        });
        add_another_product.setOnClickListener(v -> {
            int pcs = Integer.parseInt(etPcs.getText().toString().trim());
            double gw = Double.parseDouble(etGW.getText().toString().trim());
            double less = Double.parseDouble(etLess.getText().toString().trim());
            double purity = Double.parseDouble(etPurity.getText().toString().trim());
            double wastage = Double.parseDouble(etWastage.getText().toString().trim());
            double labourRate = Double.parseDouble(etLabourRate.getText().toString().trim());
            String rateOn = searchRateApplied.getText().toString().trim();

            double nw = gw - less;
            double fine = nw * (purity + wastage) / 100;
//            double labourAmount = 0.0;
//            double labourAmount = nw * labourRate;

            OrderListResponse.OrderProduct product = new OrderListResponse.OrderProduct(Integer.parseInt(product_id),
                    purity + "",
                    less + "", gw + "",
                    pcs + "", wastage + "",
                    fine + "", labourRate + "",
                    rate_applied, labourAmount + "");

            // Add to your product list
            orderList.add(product);  // <- Make sure productList is your global or passed list

            // Notify the adapter
            productAdapter.notifyItemInserted(orderList.size() - 1);

            // Close bottom sheet
            bottomSheetDialog.dismiss();
        });

        searchItem.setOnClickListener(v -> searchItem.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        searchItem.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> searchItem.showDropDown(), 100);
            }
        });

        searchItem.addTextChangedListener(new TextWatcher() {
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
                                List<StockResponse.Datum> filteredUsers = getFilteredItems(query);
                                Log.d("DEBUG", "Filtered size: " + filteredUsers.size());

                                adapter.clear();
                                adapter.addAll(filteredUsers);
                                adapter.notifyDataSetChanged();
                                searchItem.showDropDown();
                            }
                            isItemSelectedManually = false; // Reset flag
                        });
                    }
                }, DELAY);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        adapter = new ArrayAdapter<StockResponse.Datum>(ProductListingActivity.this,
                android.R.layout.simple_dropdown_item_1line, customerList);
        // Set adapter to AutoCompleteTextView
        searchItem.setAdapter(adapter);

        // Set threshold for when suggestions will show (1 char here)
        searchItem.setThreshold(1);

        // Handle item click
        searchItem.setOnItemClickListener((parent, view, position1, id) -> {
            isItemSelectedManually = true; // Prevent dropdown from reappearing

            StockResponse.Datum selectedProduct1 = adapter.getItem(position1);
            product_id = selectedProduct1.getId() + "";
            searchItem.dismissDropDown();
            searchItem.clearFocus();
        });


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calculateAndDisplayValues(searchRateApplied.getText().toString(), etPcs, etGW, etLess, etPurity, etWastage, etLabourRate, tvNW, tvFine,
                        tvLAmount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        etGW.addTextChangedListener(watcher);
        etLess.addTextChangedListener(watcher);
        etPurity.addTextChangedListener(watcher);
        etWastage.addTextChangedListener(watcher);
        etLabourRate.addTextChangedListener(watcher);
        etPcs.addTextChangedListener(watcher);

        bottomSheetDialog.show();
    }

    private String formatClean(String input) {
        try {
            double value = Double.parseDouble(input);
            if (value == (int) value) {
                return String.valueOf((int) value); // Show "5" instead of "5.000"
            } else {
                return String.format(Locale.getDefault(), "%.3f", value); // e.g., "5.123"
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return input; // fallback to original input
        }
    }

    private void searchRateAppliedListener(AutoCompleteTextView searchRateApplied, TextView tvLAmount, TextInputEditText etLabourRate, TextInputEditText etPcs, TextInputEditText etGW, TextInputEditText etLess, TextView tvNW, TextView tvFine) {
        searchRateApplied.setOnClickListener(v -> searchRateApplied.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        searchRateApplied.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> searchRateApplied.showDropDown(), 100);
            }
        });

        searchRateApplied.addTextChangedListener(new TextWatcher() {
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
                                List<String> filteredUsers = getFilteredProduct(query);
                                Log.d("DEBUG", "Filtered size: " + filteredUsers.size());

                                rateAppliedAdapter.clear();
                                rateAppliedAdapter.addAll(filteredUsers);
                                rateAppliedAdapter.notifyDataSetChanged();

                                searchRateApplied.showDropDown();

                            }
                            isItemSelectedManually = false; // Reset flag
                        });
                    }
                }, DELAY);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rateAppliedAdapter = new ArrayAdapter<String>(ProductListingActivity.this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(Arrays.asList(rateAppliedList)));

        // Set adapter to AutoCompleteTextView
        searchRateApplied.setAdapter(rateAppliedAdapter);

        // Set threshold for when suggestions will show (1 char here)
        searchRateApplied.setThreshold(1);

        // Handle item click
        searchRateApplied.setOnItemClickListener((parent, view, position, id) -> {
            isItemSelectedManually = true; // Prevent dropdown from reappearing
            String selectedProduct = rateAppliedAdapter.getItem(position);
        /*    Toast.makeText(InvoiceDetailActivity.this, "Rate on: " +
                    selectedProduct, Toast.LENGTH_SHORT).show();*/
            rate_applied = selectedProduct;
            searchRateApplied.dismissDropDown();
            searchRateApplied.clearFocus();

            tvLAmount.setText(String.format(Locale.getDefault(), "%.2f", calculateLabourAmount(
                    etLabourRate.getText().toString().equals("") || etLabourRate.getText().toString().isEmpty()
                            ? 0.0 : Double.parseDouble(etLabourRate.getText().toString()),
                    Integer.parseInt(etPcs.getText().toString()),
                    Double.parseDouble(etGW.getText().toString()),
                    Double.parseDouble(etLess.getText().toString()),
                    Double.parseDouble(tvNW.getText().toString()),
                    Double.parseDouble(tvFine.getText().toString()),
                    rate_applied)) + "");
        });
    }

    private List<StockResponse.Datum> getFilteredItems(String query) {
        List<StockResponse.Datum> filteredUsers = new ArrayList<>();
        for (StockResponse.Datum user : customerList) {
            if (user.getProductName().toLowerCase().contains(query.toLowerCase())) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }

    private List<String> getFilteredProduct(String query) {
        List<String> filteredUsers = new ArrayList<>();
        for (String product : rateAppliedList) {
            if (product.toLowerCase().contains(query.toLowerCase())) {
                filteredUsers.add(product);
            }
        }
        return filteredUsers;
    }

    public double calculateLabourAmount(double labourRate, int pcs, double gw, double less,
                                        double nw, double fine, String rateOn) {
        switch (rateOn.toUpperCase()) {
            case "PCS":
                return labourRate * pcs;
            case "GW":
                return labourRate * gw;
            case "LESS":
                return labourRate * less;
            case "NW":
                return labourRate * nw;
            case "FW": // Assuming "FW" means Fine Weight
            case "FINE":
                return labourRate * fine;
            default:
                return 0;
        }
    }

    private double calculateAndDisplayValues(String rateOn, TextInputEditText etPcs, TextInputEditText etGW,
                                             TextInputEditText etLess, TextInputEditText etPurity,
                                             TextInputEditText etWastage, TextInputEditText etLabourRate,
                                             TextView tvNW, TextView tvFine, TextView tvLAmount) {
        double labourAmount = 0.0;
        try {
            double gw = parseDouble(etGW.getText().toString());
            double less = parseDouble(etLess.getText().toString());
            double purity = parseDouble(etPurity.getText().toString());
            double wastage = parseDouble(etWastage.getText().toString());
            double labourRate = parseDouble(etLabourRate.getText().toString());

            double nw = gw - less;
            double fine = nw * (purity + wastage) / 100;


            tvNW.setText(String.format(Locale.getDefault(), "%.3f", nw));
            tvFine.setText(String.format(Locale.getDefault(), "%.3f", fine));
            tvLAmount.setText(Math.round(calculateLabourAmount(
                    etLabourRate.getText().toString().equals("") || etLabourRate.getText().toString().isEmpty()
                            ? 0.0 : Double.parseDouble(etLabourRate.getText().toString()),
                    Integer.parseInt(etPcs.getText().toString()),
                    Double.parseDouble(etGW.getText().toString()),
                    Double.parseDouble(etLess.getText().toString()),
                    Double.parseDouble(tvNW.getText().toString()),
                    Double.parseDouble(tvFine.getText().toString()),
                    rateOn)) + "");
            /*orderProduct.setLaboureAmount(Math.round(calculateLabourAmount(
                    etLabourRate.getText().toString().equals("") || etLabourRate.getText().toString().isEmpty()
                            ? 0.0 : Double.parseDouble(etLabourRate.getText().toString()),
                    Integer.parseInt(etPcs.getText().toString()),
                    Double.parseDouble(etGW.getText().toString()),
                    Double.parseDouble(etLess.getText().toString()),
                    Double.parseDouble(tvNW.getText().toString()),
                    Double.parseDouble(tvFine.getText().toString()),
                    rateOn))+"");*/
            labourAmount = Math.round(calculateLabourAmount(
                    etLabourRate.getText().toString().equals("") || etLabourRate.getText().toString().isEmpty()
                            ? 0.0 : Double.parseDouble(etLabourRate.getText().toString()),
                    Integer.parseInt(etPcs.getText().toString()),
                    Double.parseDouble(etGW.getText().toString()),
                    Double.parseDouble(etLess.getText().toString()),
                    Double.parseDouble(tvNW.getText().toString()),
                    Double.parseDouble(tvFine.getText().toString()),
                    rateOn));
//            OrderHolder.setOrder(order);
//            tvLAmount.setText(String.format(Locale.getDefault(), "%.2f", labourAmount));

        } catch (Exception e) {
            // Optional: clear or show blank values if inputs are incomplete
            tvNW.setText("");
            tvFine.setText("");
            tvLAmount.setText("");
        }
        return  labourAmount;
    }

    private double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) return 0.0;
        return Double.parseDouble(value.trim());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_PRODUCT && resultCode == RESULT_OK) {
            OrderListResponse.OrderProduct updatedProduct = (OrderListResponse.OrderProduct) data.getSerializableExtra("updated_product");
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                orderList.set(position, updatedProduct);
//                productAdapter.notifyItemChanged(position);
            }
        }
    }

    private void getProductItems() {
        ExpenseListReq req = new ExpenseListReq(SharedPref.getString("delivery_id", ""),
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
                    Log.d("size customer List", response.body().getData().size() + "");
                    customerList.clear();
                    customerList.addAll(response.body().getData());
                    adapter = new ArrayAdapter<StockResponse.Datum>(ProductListingActivity.this,
                            android.R.layout.simple_dropdown_item_1line, customerList);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(ProductListingActivity.this, "Customer retrieve error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ProductListingActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
