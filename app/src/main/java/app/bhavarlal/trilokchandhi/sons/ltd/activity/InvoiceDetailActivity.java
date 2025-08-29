package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityInvoiceInfoBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InvoiceDetailActivity extends AppCompatActivity {
    ActivityInvoiceInfoBinding binding;
    RetroInterface apiInterface;
    List<StockResponse.Datum> customerList;
    ArrayAdapter<StockResponse.Datum> adapter;

//    List<String> rateAppliedList;
    String[] rateAppliedList = {"PCS","GW","Less","NW","FW"};

    ArrayAdapter<String> rateAppliedAdapter;
    private boolean isItemSelectedManually = false;
    String product_id = "", rate_applied = "";
    ArrayList<OrderGenerateReq.Product> productEntryList = new ArrayList<>();
    ArrayList<OrderListResponse.OrderProduct> productUpdateList = new ArrayList<>();
    Double l_amt_total = 0.0, fine_total = 0.0;
    OrderListResponse.Datum datum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);
        customerList = new ArrayList<>();
        productUpdateList = new ArrayList<>();
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        if (NetworkUtils.isInternetAvailable(InvoiceDetailActivity.this)) {
            getProductItems();
        } else {
            Toast.makeText(InvoiceDetailActivity.this, "Please check connection!", Toast.LENGTH_SHORT).show();
        }
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validateInputs()) {
                    fine_total = fine_total + Double.parseDouble(binding.tvFine.getText().toString());
                    l_amt_total = l_amt_total + Double.parseDouble(binding.tvLAmount.getText().toString());
                    OrderGenerateReq.Product reqOrder = new OrderGenerateReq.Product();

                    reqOrder = new OrderGenerateReq.Product(product_id, binding.etPurity.getText().toString(),
                            binding.etLess.getText().toString(),
                            binding.etGW.getText().toString(),
                            binding.etPcs.getText().toString(),
                            binding.etWastage.getText().toString(),
                            binding.tvFine.getText().toString(),
                            binding.etLabourRate.getText().toString(),
                            rate_applied, binding.tvLAmount.getText().toString()
                    );

                    if(binding.btnNext.getText().toString().trim().equalsIgnoreCase("update")){
                        productEntryList.set(getIntent().getIntExtra("position", 0), reqOrder);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("updated_product", productEntryList.get(getIntent().getIntExtra("position", 0)));
                        resultIntent.putExtra("updated_product", productUpdateList.get(getIntent().getIntExtra("position", 0)));
                        resultIntent.putExtra("position", getIntent().getIntExtra("position", -1));
                        setResult(RESULT_OK, resultIntent);
                        finish();

                    }else {
                        productEntryList.add(reqOrder);

                        Intent i = new Intent(InvoiceDetailActivity.this, FinalDetailsActivity.class);
                        i.putExtra("date", getIntent().getStringExtra("date").toString());
                        i.putExtra("customer", getIntent().getStringExtra("customer").toString());
                        i.putExtra("customer_id", getIntent().getStringExtra("customer_id").toString());
                        i.putExtra("address", getIntent().getStringExtra("address").toString());

                        i.putParcelableArrayListExtra("products", productEntryList);

                        i.putExtra("product_id", product_id + "");
                        i.putExtra("quantity", binding.etPcs.getText().toString() + "");
                        i.putExtra("gross_weight", binding.etGW.getText().toString() + "");
                        i.putExtra("less_weight", binding.etLess.getText().toString() + "");
                        i.putExtra("purity", binding.etPurity.getText().toString() + "");
                        i.putExtra("wastage", binding.etWastage.getText().toString() + "");
                        i.putExtra("laboure_rate", binding.etLabourRate.getText().toString() + "");
                        i.putExtra("rate_on", rate_applied);
                        i.putExtra("laboure_amount", l_amt_total + "");
                        i.putExtra("fine", fine_total + "");

                        startActivity(i);
                    }


                }
            }
        });
        searchProductListener();
        searchRateAppliedListener();
        setListenerForAddProduct();

        textWatcherForNetWeight();
        textWatcherForFine();
        if ((OrderListResponse.Datum) getIntent().getSerializableExtra("order") != null) {
            binding.btnNext.setText("Update");
            datum = (OrderListResponse.Datum) getIntent().getSerializableExtra("order");
            for(int i = 0; i< datum.getOrderProduct().size(); i++ ){
                OrderGenerateReq.Product reqOrder = new OrderGenerateReq.Product();
                OrderListResponse.OrderProduct reqOrder1 = new OrderListResponse.OrderProduct(
                        datum.getOrderProduct().get(i).getId(),
                        datum.getOrderProduct().get(i).getProductId(),
                        Integer.parseInt(SharedPref.getString("user_id", "")),
                        Integer.parseInt(SharedPref.getString("delivery_id", "")),
                        datum.getOrderProduct().get(i).getOrderId(),
                        datum.getOrderProduct().get(i).getQuantity(),
                        datum.getOrderProduct().get(i).getGrossWeight(),
                        datum.getOrderProduct().get(i).getLessWeight(),
                        datum.getOrderProduct().get(i).getPurity(),
                        datum.getOrderProduct().get(i).getWastage(),
                        datum.getOrderProduct().get(i).getFine(),
                        datum.getOrderProduct().get(i).getLaboureRate(),
                        datum.getOrderProduct().get(i).getRateOn(),
                        datum.getOrderProduct().get(i).getLaboureAmount(),
                        new OrderListResponse.ProductDetails(datum.getOrderProduct().get(i).getProductDetails().getId(),
                                datum.getOrderProduct().get(i).getProductDetails().getProductName() )

                );

                reqOrder = new OrderGenerateReq.Product(datum.getOrderProduct().get(i).getProductId()+"",
                        datum.getOrderProduct().get(i).getPurity().toString(),
                        datum.getOrderProduct().get(i).getLessWeight().toString(),
                        datum.getOrderProduct().get(i).getGrossWeight().toString(),
                        datum.getOrderProduct().get(i).getQuantity().toString(),
                        datum.getOrderProduct().get(i).getWastage().toString(),
                        datum.getOrderProduct().get(i).getFine().toString(),
                        datum.getOrderProduct().get(i).getLaboureRate().toString(),
                        datum.getOrderProduct().get(i).getRateOn().toString(),
                        datum.getOrderProduct().get(i).getLaboureAmount().toString()
                );
                productEntryList.add(reqOrder);
                productUpdateList.add(reqOrder1);
            }

            binding.etPcs.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getQuantity()+"");
            binding.etLess.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getLessWeight()+"");
            binding.etPurity.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getPurity()+"");
            binding.etGW.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getGrossWeight()+"");
            binding.etWastage.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getWastage()+"");
            binding.tvFine.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getFine()+"");
            binding.etLabourRate.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getLaboureRate()+"");
            binding.tvLAmount.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getLaboureAmount()+"");


            binding.searchRateApplied.setText(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getRateOn()+"");

            /*binding.searchAutoComplete.setText(datum.getCustomer().getName());
            binding.txtAddress.setText(datum.getCustomer().getAddress());
            binding.txtPhone.setText(datum.getCustomer().getContact());*/
        }
    }

    public String getProductNameById(int productId, List<StockResponse.Datum> productList) {
        for (StockResponse.Datum product : productList) {
            if (product.getId() == productId) {
                return product.getProductName();
            }
        }
        return ""; // default if not found
    }

    private void textWatcherForFine() {
        binding.tvNW.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateFine();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        binding.etWastage.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateFine();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        binding.etPurity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateFine();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void textWatcherForNetWeight() {
        TextWatcher watcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {
                    double gw = Double.parseDouble(binding.etGW.getText().toString());
                    double less = Double.parseDouble(binding.etLess.getText().toString());
                    double nw = gw - less;
                    binding.tvNW.setText(String.format("%.2f", nw));
                } catch (NumberFormatException e) {
                    binding.tvNW.setText("0.00");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };

        binding.etGW.addTextChangedListener(watcher);
        binding.etLess.addTextChangedListener(watcher);
    }

    private void updateFine() {
        try {
            double netWeight = Double.parseDouble(binding.tvNW.getText().toString());
            double purityWastage = Double.parseDouble(binding.etPurity.getText().toString())+
                    Double.parseDouble(binding.etWastage.getText().toString());
            double fine = netWeight * (purityWastage / 100.0);
            binding.tvFine.setText(String.format(Locale.getDefault(), "%.3f", fine));
        } catch (NumberFormatException e) {
            binding.tvFine.setText(""); // clear if input invalid
        }
    }

    private void setListenerForAddProduct() {
        binding.addAnotherProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    l_amt_total = l_amt_total + Double.parseDouble(binding.tvLAmount.getText().toString());
                    Log.e("total_l_amt", l_amt_total+"");

                    fine_total = fine_total + Double.parseDouble(binding.tvFine.getText().toString());
                    Log.e("total_fine", fine_total+"");


                    OrderGenerateReq.Product reqOrder = new OrderGenerateReq.Product();
                  /*  Bundle productData = new Bundle();
                    productData.putString("product_id", product_id);
                    productData.putString("quantity", binding.etPcs.getText().toString());
                    productData.putString("gross_weight", binding.etGW.getText().toString());
                    productData.putString("less_weight", binding.etLess.getText().toString());
                    productData.putString("purity", binding.etPurity.getText().toString());
                    productData.putString("wastage", binding.etWastage.getText().toString());
                    productData.putString("laboure_rate", binding.etLabourRate.getText().toString());
                    productData.putString("fine", binding.tvFine.getText().toString());
                    productData.putString("rate_on", rate_applied);
                    productData.putString("laboure_amount", binding.tvLAmount.getText().toString());*/

                    reqOrder = new OrderGenerateReq.Product(product_id, binding.etPurity.getText().toString(),
                            binding.etLess.getText().toString(), binding.etGW.getText().toString(),
                            binding.etPcs.getText().toString(), binding.etWastage.getText().toString(),
                            binding.tvFine.getText().toString(), binding.etLabourRate.getText().toString(),
                            rate_applied, binding.tvLAmount.getText().toString()
                            );

/*                    new OrderGenerateReq.Product(product_id, binding.etPurity.getText().toString(),
                            Double.parseDouble(binding.etLess.getText().toString()),
                            Double.parseDouble(binding.etGW.getText().toString()),
                            Integer.parseInt(binding.etPcs.getText().toString()),
                            Double.parseDouble(binding.etWastage.getText().toString()),
                            Double.parseDouble(binding.tvFine.getText().toString()),
                            Double.parseDouble(binding.etLabourRate.getText().toString()),
                            rate_applied, Double.parseDouble(binding.tvLAmount.getText().toString())
                    );*/
                    productEntryList.add(reqOrder);

                    Toast.makeText(InvoiceDetailActivity.this, "Product added", Toast.LENGTH_SHORT).show();

                    clearInputs(); // Reset input fields
                }
            }
        });

    }

    private void clearInputs() {
        binding.searchItem.setText("");
        binding.etPcs.setText("");
        binding.etGW.setText("");
        binding.etLess.setText("");
        binding.etPurity.setText("");
        binding.etWastage.setText("");
        binding.etLabourRate.setText("");
        binding.searchRateApplied.setText("");
        binding.tvLAmount.setText("0.00");
        binding.tvNW.setText("0.00");

//        product_id = "";
//        rate_applied = "";
    }

    private boolean validateInputs() {

        boolean isValid = true;

        if (binding.searchItem.getText().toString().trim().isEmpty()) {
            binding.searchItem.setError("Select a product");
            isValid = false;
        }

        if (binding.etPcs.getText().toString().trim().isEmpty()) {
            binding.etPcs.setError("Enter quantity");
            isValid = false;
        }

        if (binding.etGW.getText().toString().trim().isEmpty()) {
            binding.etGW.setError("Enter gross weight");
            isValid = false;
        }

        if (binding.etLess.getText().toString().trim().isEmpty()) {
            binding.etLess.setError("Enter less weight");
            isValid = false;
        }

        if (binding.etPurity.getText().toString().trim().isEmpty()) {
            binding.etPurity.setError("Enter purity");
            isValid = false;
        }

        if (binding.etWastage.getText().toString().trim().isEmpty()) {
            binding.etWastage.setError("Enter wastage");
            isValid = false;
        }

        /*if (binding.etLabourRate.getText().toString().trim().isEmpty()) {
            binding.etLabourRate.setError("Enter labour rate");
            isValid = false;
        }*/

        if (binding.searchRateApplied.getText().toString().trim().isEmpty()) {
            binding.searchRateApplied.setError("Select rate applied");
            isValid = false;
        }


        // Add checks for other fields similarly if required
        return isValid;

    }

    private void searchRateAppliedListener() {
        binding.searchRateApplied.setOnClickListener(v -> binding.searchRateApplied.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        binding.searchRateApplied.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> binding.searchRateApplied.showDropDown(), 100);
            }
        });

        binding.searchRateApplied.addTextChangedListener(new TextWatcher() {
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
                            if (!query.isEmpty()&& !isItemSelectedManually) {
                                List<String> filteredUsers = getFilteredProduct(query);
                                Log.d("DEBUG", "Filtered size: " + filteredUsers.size());

                                rateAppliedAdapter.clear();
                                rateAppliedAdapter.addAll(filteredUsers);
                                rateAppliedAdapter.notifyDataSetChanged();

                                binding.searchRateApplied.showDropDown();

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

        rateAppliedAdapter = new ArrayAdapter<String>(InvoiceDetailActivity.this,
                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(Arrays.asList(rateAppliedList)));

        // Set adapter to AutoCompleteTextView
        binding.searchRateApplied.setAdapter(rateAppliedAdapter);

        // Set threshold for when suggestions will show (1 char here)
        binding.searchRateApplied.setThreshold(1);

        // Handle item click
        binding.searchRateApplied.setOnItemClickListener((parent, view, position, id) -> {
            isItemSelectedManually = true; // Prevent dropdown from reappearing
            String selectedProduct = rateAppliedAdapter.getItem(position);
        /*    Toast.makeText(InvoiceDetailActivity.this, "Rate on: " +
                    selectedProduct, Toast.LENGTH_SHORT).show();*/
            rate_applied = selectedProduct;
            binding.searchItem.dismissDropDown();
            binding.searchItem.clearFocus();

            binding.tvLAmount.setText(String.format(Locale.getDefault(), "%.2f", calculateLabourAmount(
                    binding.etLabourRate.getText().toString().equals("") || binding.etLabourRate.getText().toString().isEmpty()
                            ? 0.0 : Double.parseDouble(binding.etLabourRate.getText().toString()),
                    Integer.parseInt(binding.etPcs.getText().toString()),
                    Double.parseDouble(binding.etGW.getText().toString()),
                    Double.parseDouble(binding.etLess.getText().toString()),
                    Double.parseDouble(binding.tvNW.getText().toString()),
                    Double.parseDouble(binding.tvFine.getText().toString()),
                    rate_applied))+"");
        });
    }

    private void searchProductListener() {

        binding.searchItem.setOnClickListener(v -> binding.searchItem.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        binding.searchItem.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> binding.searchItem.showDropDown(), 100);
            }
        });

        binding.searchItem.addTextChangedListener(new TextWatcher() {
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
                                binding.searchItem.showDropDown();
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
        adapter = new ArrayAdapter<StockResponse.Datum>(InvoiceDetailActivity.this,
                android.R.layout.simple_dropdown_item_1line, customerList);
        // Set adapter to AutoCompleteTextView
        binding.searchItem.setAdapter(adapter);

        // Set threshold for when suggestions will show (1 char here)
        binding.searchItem.setThreshold(1);

        // Handle item click
        binding.searchItem.setOnItemClickListener((parent, view, position, id) -> {
            isItemSelectedManually = true; // Prevent dropdown from reappearing

            StockResponse.Datum selectedProduct = adapter.getItem(position);
            product_id = selectedProduct.getProduct_id()+"";
            binding.searchItem.dismissDropDown();
            binding.searchItem.clearFocus();
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
                    Log.d("size customer List", response.body().getData().size() + "");
                    customerList.clear();
                    customerList.addAll(response.body().getData());
                    /*adapter = new ArrayAdapter<StockResponse.Datum>(InvoiceDetailActivity.this,
                            android.R.layout.simple_dropdown_item_1line, customerList);
                    binding.searchItem.setAdapter(adapter);*/
                    adapter.notifyDataSetChanged();

                    if ((OrderListResponse.Datum) getIntent().getSerializableExtra("order") != null){
                        String productName = getProductNameById(datum.getOrderProduct().get(getIntent().getIntExtra("position", 0)).getProductId(), customerList);
                        binding.searchItem.setText(productName);
                    }

                } else {
                    Toast.makeText(InvoiceDetailActivity.this, "Customer retrieve error..", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<StockResponse> call, Throwable t) {
//                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(InvoiceDetailActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
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

}

