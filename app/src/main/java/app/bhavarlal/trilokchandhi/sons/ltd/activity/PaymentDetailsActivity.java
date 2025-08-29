package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityPaymentDetailsBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentDetailsActivity extends AppCompatActivity {
    ActivityPaymentDetailsBinding binding;
    RetroInterface apiInterface;
    List<OrderGenerateReq.Product> productList = new ArrayList<>();
    Double gst_amt = 0.0, total_amt = 0.0, gst_per_amt = 0.0;
    String gst_payment_type = "";
    OrderListResponse.Datum order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPref.init(this);
        productList = new ArrayList<>();
        if(getIntent().getParcelableArrayListExtra("products") != null)
        productList = getIntent().getParcelableArrayListExtra("products");

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        order = OrderHolder.getOrder();
        if(order != null){
            binding.btnSubmit.setText("Save & Update");
            binding.backButton.setVisibility(View.GONE);
            binding.etGoldRate.setText(order.getGoldRate()+"");
            binding.etWeight.setText(order.getMetalRWeight()+"");
            binding.etPurity.setText(order.getMetalRPurity()+"");
            binding.tvFine.setText(order.getMetalRFine()+"");
            binding.etNarration.setText(order.getComment2()+"");
            binding.tvBalanceFine.setText(order.getBalanceFine()+"");
            binding.etRoundOffAmt.setText(order.getRoundOffAmount()+"");
            binding.etReceivedAmt.setText(order.getReceivedAmount()+"");
            binding.tvTotalAmt.setText(order.getTotalAmount()+"");
            binding.tvBalanceAmt.setText(Math.round(order.getBalanceAmount())+"");
            if (order.getPaymentType().equalsIgnoreCase("cheque")) {
                gst_payment_type = "cheque";
                binding.llCheque.setVisibility(View.VISIBLE);
                binding.etChqDate.setText(order.getChequeDate());
                binding.etChqNo.setText(order.getChequeNo());
                binding.etBankName.setText(order.getChequeBankName());
            } else if (order.getPaymentType().equalsIgnoreCase("NEFT")) {
                gst_payment_type = "NEFT";
                binding.llCheque.setVisibility(View.GONE);
            }

        }
        showViewAsPerPayment();
        textWatcherAsPerGoldRate();
        textWatcherNonGSTRate();
        paymentMethodSelection();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(order != null){
//                    order.setOldFine(binding.etOldFine.getText().toString());
                    order.setGoldRate(binding.etGoldRate.getText().toString());
                    order.setTotalAmount(binding.tvTotalAmt.getText().toString());
                    order.setMetalRFine(binding.tvFine.getText().toString()+"");
                    order.setMetalRPurity(binding.etPurity.getText().toString());
                    order.setMetalRWeight(binding.etWeight.getText().toString());
                    order.setComment2(binding.etNarration.getText().toString()+"");
                    order.setBalanceFine(binding.tvBalanceFine.getText().toString().equals("") ? 0.000 : Double.parseDouble(binding.tvBalanceFine.getText().toString()+""));
                    order.setRoundOffAmount(binding.etRoundOffAmt.getText().toString().equals("") ? 0 : Double.parseDouble(binding.etRoundOffAmt.getText().toString()+""));
                    order.setReceivedAmount(binding.etReceivedAmt.getText().toString()+"");
                    order.setBalanceAmount(binding.tvBalanceAmt.getText().toString().equals("") ? 0 :Double.parseDouble(binding.tvBalanceAmt.getText().toString()+""));
                    order.setAmount(binding.tvAmount.getText().toString().equals("") ? "0" :binding.tvAmount.getText().toString()+"");
                    order.setGstPercentage(binding.tvGstPercentage.getText().toString()+"");

                    if (gst_payment_type.equalsIgnoreCase("cheque")) {
                        order.setPaymentType(gst_payment_type);
                        order.setChequeDate(binding.etChqDate.getText().toString()+"");
                        order.setChequeNo(binding.etChqNo.getText().toString()+"");
                        order.setChequeBankName(binding.etBankName.getText().toString()+"");
                    } else if (order.getPaymentType().equalsIgnoreCase("NEFT")) {
                        order.setPaymentType(gst_payment_type);
                        order.setChequeDate("");
                        order.setChequeNo("");
                        order.setChequeBankName("");
                    }

                    OrderHolder.setOrder(order);
                    finish();
                }else {
                    Intent i = new Intent(PaymentDetailsActivity.this, PaymentDetailsActivity.class);
                    i.putExtra("date", getIntent().getStringExtra("date").toString());
                    i.putExtra("customer", getIntent().getStringExtra("customer").toString());
                    i.putExtra("address", getIntent().getStringExtra("address").toString());
                    i.putExtra("customer_id", getIntent().getStringExtra("customer_id").toString());

                    i.putParcelableArrayListExtra("products", getIntent().getParcelableArrayListExtra("products"));
                   /* i.putExtra("product_id", getIntent().getStringExtra("product_id").toString());
                    i.putExtra("quantity", getIntent().getStringExtra("quantity").toString());
                    i.putExtra("gross_weight", getIntent().getStringExtra("gross_weight").toString());
                    i.putExtra("less_weight", getIntent().getStringExtra("less_weight").toString());
                    i.putExtra("purity", getIntent().getStringExtra("purity").toString());
                    i.putExtra("wastage", getIntent().getStringExtra("wastage").toString());
                    i.putExtra("laboure_rate", getIntent().getStringExtra("laboure_rate").toString());
                    i.putExtra("rate_on", getIntent().getStringExtra("rate_on").toString());
                    i.putExtra("laboure_amount", getIntent().getStringExtra("laboure_amount").toString());
                    i.putParcelableArrayListExtra("products", getIntent().getParcelableArrayListExtra("products"));*/

                    i.putExtra("fine", getIntent().getStringExtra("fine").toString());
                    i.putExtra("old_fine", getIntent().getStringExtra("old_fine").toString());
                    i.putExtra("balance_fine", getIntent().getStringExtra("balance_fine").toString());
                    i.putExtra("old_amount", getIntent().getStringExtra("old_amount").toString());
                    i.putExtra("comment_1", getIntent().getStringExtra("comment_1").toString());
                    i.putExtra("mode_of_payment", getIntent().getStringExtra("mode_of_payment").toString());
                    i.putExtra("other_purity", getIntent().getStringExtra("other_purity").toString());

                    i.putExtra("gold_rate", binding.etGoldRate.getText().toString() + "");
                    i.putExtra("total_amount", binding.tvTotalAmt.getText().toString() + "");
                    i.putExtra("amount", binding.tvAmount.getText().toString() + "");
                    i.putExtra("gst_percentage", "3");

                    i.putExtra("cheque_no", binding.etChqNo.getText().toString() + "");
                    i.putExtra("cheque_date", binding.etChqDate.getText().toString() + "");
                    i.putExtra("cheque_bank_name", binding.etBankName.getText().toString() + "");
                    i.putExtra("comment_2", binding.etNarration.getText().toString() + "");
                    i.putExtra("selectedPayment", getIntent().getStringExtra("selectedPayment").toString() + "");

                    startActivity(i);

                    generateOrderInvoice();
                }


            }
        });
        binding.etChqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDatePickerDialog(binding.etChqDate);
            }
        });
    }

    private void textWatcherNonGSTRate() {
        binding.etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                showViewAsPerPayment();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.etPurity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                showViewAsPerPayment();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

    }

    private void paymentMethodSelection() {
        binding.paymentMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radio_cheque) {
                gst_payment_type = "cheque";
                binding.llCheque.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.radio_rtgs) {
                gst_payment_type = "NEFT";
                binding.llCheque.setVisibility(View.GONE);
            }
        });

    }

    private void textWatcherAsPerGoldRate() {

        binding.etGoldRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                showViewAsPerPayment();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        binding.etReceivedAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                showViewAsPerPayment();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        binding.etRoundOffAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                showViewAsPerPayment();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    Double balance_fine = 0.0;
    private void showViewAsPerPayment() {
        String labourAmountStr = getIntent().getStringExtra("laboure_amount");
        String oldAmountStr = getIntent().getStringExtra("old_amount");

        double labourAmount = 0.0;
        double oldAmount = 0.0;

        if (labourAmountStr != null && !labourAmountStr.equals("null") && !labourAmountStr.isEmpty()) {
            labourAmount = Double.parseDouble(labourAmountStr);
        }

        if (oldAmountStr != null && !oldAmountStr.equals("null") && !oldAmountStr.isEmpty()) {
            oldAmount = Double.parseDouble(oldAmountStr);
        }

        if (getIntent().getStringExtra("mode_of_payment").toString().equals("GST")) {
            binding.llGST.setVisibility(View.VISIBLE);
            binding.llNonGST.setVisibility(View.GONE);
            if(binding.etGoldRate.getText().toString().equals("")){
                gst_amt = Double.parseDouble(getIntent().getStringExtra("fine").toString()) * 0.0;
                binding.tvGstPercentage.setText(0.00 +"");
            }else {
                gst_amt = Double.parseDouble(getIntent().getStringExtra("fine").toString()) *
                        Double.parseDouble(binding.etGoldRate.getText().toString());
                binding.tvGstPercentage.setText(new DecimalFormat("0.000").format(
                        Double.parseDouble((labourAmount*0.03)+
                                (gst_amt*0.03)+"")));
            }
            gst_per_amt = Double.parseDouble((labourAmount*0.03)+
                    (gst_amt*0.03)+"");
            binding.tvAmount.setText(Math.round(gst_amt)+"");

            total_amt = gst_amt + gst_per_amt +
                    labourAmount +
                    oldAmount;
//            binding.tvTotalAmt.setText(total_amt+"");
            binding.tvTotalAmt.setText(Math.round(total_amt)+"");
            if(!binding.etGoldRate.getText().toString().equals("")) {
                if (Double.parseDouble(binding.etGoldRate.getText().toString()) > 0.0) {
                    if (!binding.etReceivedAmt.getText().toString().equals("")) {
                        binding.tvBalanceAmt.setText(Math.round(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString())) + "");
                    }else {
                        binding.tvBalanceAmt.setText(Math.round(total_amt - 0.0) + "");
                    }
                }
            }else {
                binding.tvBalanceAmt.setText("0.0");
            }

        } else {
            gst_payment_type = "";
            binding.llNonGST.setVisibility(View.VISIBLE);
            binding.llGST.setVisibility(View.GONE);


            if(!binding.etWeight.getText().toString().equals("")&&
                    !binding.etPurity.getText().toString().equals("")){
                binding.tvFine.setText(new DecimalFormat("0.000").format((Double.parseDouble(binding.etWeight.getText().toString()) *
                        Double.parseDouble(binding.etPurity.getText().toString()))/100)  +"");
                balance_fine = Double.parseDouble(getIntent().getStringExtra("fine").toString()) -
                        Double.parseDouble(binding.tvFine.getText().toString());
                if(binding.etGoldRate.getText().toString().equals("") || binding.etGoldRate.getText().toString().equals("0.00")) {
                    binding.tvBalanceFine.setText(new DecimalFormat("0.000").format(balance_fine)+ "");
                }else {
                    binding.tvBalanceFine.setText("");
                }
            }else{
                binding.tvFine.setText(0.000  +"");
                balance_fine = Double.parseDouble(getIntent().getStringExtra("fine").toString());
                if(binding.etGoldRate.getText().toString().equals("")){
                    binding.tvBalanceFine.setText(new DecimalFormat("0.000").format(balance_fine)+"");
                }else {
                    binding.tvBalanceFine.setText("");
                }

            }
            if(!binding.etGoldRate.getText().toString().equals("")) {
                total_amt = (Double.parseDouble(binding.etGoldRate.getText().toString()) * balance_fine)
                        + (oldAmount +labourAmount);
            }else{
                total_amt = (0.0 * balance_fine)
                        + (oldAmount + labourAmount);
            }
//            binding.tvTotalAmt.setText(total_amt+"");
            binding.tvTotalAmt.setText(Math.round(total_amt)+"");
            if(!binding.etReceivedAmt.getText().toString().equals("") && !binding.etRoundOffAmt.getText().toString().equals("") ) {
                binding.tvBalanceAmt.setText(Math.round(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString()) -
                        Double.parseDouble(binding.etRoundOffAmt.getText().toString()))+"");
            }else if(!binding.etReceivedAmt.getText().toString().equals("") && binding.etRoundOffAmt.getText().toString().equals("") ){
                binding.tvBalanceAmt.setText(Math.round(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString()) -
                        0)+"");
            }else if(binding.etReceivedAmt.getText().toString().equals("") && !binding.etRoundOffAmt.getText().toString().equals("") ){
                binding.tvBalanceAmt.setText(Math.round(total_amt - 0 -
                        Double.parseDouble(binding.etRoundOffAmt.getText().toString()))+"");
            }else{
                binding.tvBalanceAmt.setText(Math.round(total_amt - 0 - 0) +"");
            }

        }
    }

    private void generateOrderInvoice() {
        OrderGenerateReq request;
        if(binding.etChqDate.getText().toString().isEmpty() || binding.etChqDate.getText().toString().equals("")) {
            request = new OrderGenerateReq(SharedPref.getString("user_id", ""),
                    SharedPref.getString("delivery_id", ""),
                    getIntent().getStringExtra("customer_id").toString(),
                    getIntent().getStringExtra("date").toString(),
                    Double.parseDouble(getIntent().getStringExtra("old_fine").toString()),
                    Double.parseDouble(getIntent().getStringExtra("old_amount").toString()),
                    "",
                    Double.parseDouble(binding.etWeight.getText().toString()),
                    Double.parseDouble(binding.etPurity.getText().toString()),
                    Double.parseDouble(binding.tvFine.getText().toString()),
                    balance_fine,
                    Double.parseDouble(binding.tvTotalAmt.getText().toString()),
                    Double.parseDouble("3"),
                    binding.etReceivedAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.etReceivedAmt.getText().toString()),
                    binding.etRoundOffAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.etRoundOffAmt.getText().toString()),
                    binding.tvBalanceAmt.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.tvBalanceAmt.getText().toString()),
                    binding.etChqNo.getText().toString(), null,
                    binding.etBankName.getText().toString(),
                    binding.etNarration.getText().toString(),
                    binding.etNarrationNonGst.getText().toString(),
                    Double.parseDouble(getIntent().getStringExtra("other_purity").toString()),
                    getIntent().getStringExtra("mode_of_payment").toString(),
                    binding.etGoldRate.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etGoldRate.getText().toString()),
                    binding.tvTotalAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.tvTotalAmt.getText().toString()),
                    gst_payment_type,
                    productList);
        }else {
            request = new OrderGenerateReq(SharedPref.getString("user_id", ""),
                    SharedPref.getString("delivery_id", ""),
                    getIntent().getStringExtra("customer_id").toString(),
                    getIntent().getStringExtra("date").toString(),
                    Double.parseDouble(getIntent().getStringExtra("old_fine").toString()),
                    Double.parseDouble(getIntent().getStringExtra("old_amount").toString()),
                    "",
                    Double.parseDouble(binding.etWeight.getText().toString()),
                    Double.parseDouble(binding.etPurity.getText().toString()),
                    Double.parseDouble(binding.tvFine.getText().toString()),
                    balance_fine,
                    Double.parseDouble(binding.tvTotalAmt.getText().toString()),
                    Double.parseDouble("3"),
                    binding.etReceivedAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.etReceivedAmt.getText().toString()),
                    binding.etRoundOffAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.etRoundOffAmt.getText().toString()),
                    binding.tvBalanceAmt.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.tvBalanceAmt.getText().toString()),
                    binding.etChqNo.getText().toString(),
                    binding.etChqDate.getText().toString(),
                    binding.etBankName.getText().toString(),
                    binding.etNarration.getText().toString(),
                    binding.etNarrationNonGst.getText().toString(),
                    Double.parseDouble(getIntent().getStringExtra("other_purity").toString()),
                    getIntent().getStringExtra("mode_of_payment").toString(),
                    binding.etGoldRate.getText().toString().isEmpty() ? 0.0 : Double.parseDouble(binding.etGoldRate.getText().toString()),
                    binding.tvTotalAmt.getText().toString().isEmpty() ? 0.0 :Double.parseDouble(binding.tvTotalAmt.getText().toString()),
                    gst_payment_type,
                    productList);
        }


        Log.e("Token", SharedPref.getString("token", "") + "");
        Call<ResponseBody> call = apiInterface.createOrder("Bearer " + SharedPref.getString("token", ""), request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("OrderResponse", response.body().string());
                        if(response.isSuccessful()){
                            Toast.makeText(PaymentDetailsActivity.this, "Order added successfully", Toast.LENGTH_SHORT).show();
//                            finish();
                            startActivity(new Intent(PaymentDetailsActivity.this, DashboardActivity.class));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("OrderError", "Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("OrderFailure", t.getMessage());
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
                PaymentDetailsActivity.this,
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
