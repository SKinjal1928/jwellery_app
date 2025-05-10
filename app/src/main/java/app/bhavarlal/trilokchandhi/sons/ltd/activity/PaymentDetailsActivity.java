package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityPaymentDetailsBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPref.init(this);
        productList = new ArrayList<>();
        productList = getIntent().getParcelableArrayListExtra("products");

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);

        showViewAsPerPayment();
        textWatcherAsPerGoldRate();
        textWatcherNonGSTRate();
        paymentMethodSelection();
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentDetailsActivity.this, PaymentDetailsActivity.class);
                i.putExtra("date", getIntent().getStringExtra("date").toString());
                i.putExtra("customer", getIntent().getStringExtra("customer").toString());
                i.putExtra("address", getIntent().getStringExtra("address").toString());
                i.putExtra("customer_id", getIntent().getStringExtra("customer_id").toString());

                i.putParcelableArrayListExtra("products", getIntent().getParcelableArrayListExtra("products"));
                i.putExtra("product_id", getIntent().getStringExtra("product_id").toString());
                i.putExtra("quantity", getIntent().getStringExtra("quantity").toString());
                i.putExtra("gross_weight", getIntent().getStringExtra("gross_weight").toString());
                i.putExtra("less_weight", getIntent().getStringExtra("less_weight").toString());
                i.putExtra("purity", getIntent().getStringExtra("purity").toString());
                i.putExtra("wastage", getIntent().getStringExtra("wastage").toString());
                i.putExtra("laboure_rate", getIntent().getStringExtra("laboure_rate").toString());
                i.putExtra("rate_on", getIntent().getStringExtra("rate_on").toString());
                i.putExtra("laboure_amount", getIntent().getStringExtra("laboure_amount").toString());
                i.putParcelableArrayListExtra("products", getIntent().getParcelableArrayListExtra("products"));

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

                startActivity(i);

                generateOrderInvoice();
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
        if (getIntent().getStringExtra("mode_of_payment").toString().equals("GST")) {
            binding.llGST.setVisibility(View.VISIBLE);
            binding.llNonGST.setVisibility(View.GONE);
            if(binding.etGoldRate.getText().toString().equals("")){
                gst_amt = Double.parseDouble(getIntent().getStringExtra("fine").toString()) * 0.0;
            }else {
                gst_amt = Double.parseDouble(getIntent().getStringExtra("fine").toString()) *
                        Double.parseDouble(binding.etGoldRate.getText().toString());
            }
            gst_per_amt = Double.parseDouble((Double.parseDouble(getIntent().getStringExtra("laboure_amount").toString())*0.03)+
                    (gst_amt*0.03)+"");
            binding.tvAmount.setText(new DecimalFormat("#.000").format(gst_amt));
            binding.tvGstPercentage.setText(new DecimalFormat("#.000").format(
                    Double.parseDouble((Double.parseDouble(getIntent().getStringExtra("laboure_amount").toString())*0.03)+
                            (gst_amt*0.03)+"")));
            total_amt = gst_amt + gst_per_amt + Double.parseDouble(getIntent().getStringExtra("laboure_amount").toString()) +
             Double.parseDouble(getIntent().getStringExtra("old_amount").toString());
            binding.tvTotalAmt.setText(total_amt+"");
            if(!binding.etGoldRate.getText().toString().equals("")) {
                if (Double.parseDouble(binding.etGoldRate.getText().toString()) > 0.0) {
                    if (!binding.etReceivedAmt.getText().toString().equals("")) {
                        binding.tvBalanceAmt.setText(new DecimalFormat("#.000").format(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString())) + "");
                    }else {
                        binding.tvBalanceAmt.setText(new DecimalFormat("#.000").format(total_amt - 0.0) + "");
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
                binding.tvFine.setText((Double.parseDouble(binding.etWeight.getText().toString()) *
                        Double.parseDouble(binding.etPurity.getText().toString()))/100  +"");
                balance_fine = Double.parseDouble(getIntent().getStringExtra("fine").toString()) -
                        Double.parseDouble(binding.tvFine.getText().toString());
                if(binding.etGoldRate.getText().toString().equals("")) {
                    binding.tvBalanceFine.setText(balance_fine+ "");
                }else {
                    binding.tvBalanceFine.setText("");
                }
            }else{
                binding.tvFine.setText(0.0  +"");
                balance_fine = Double.parseDouble(getIntent().getStringExtra("fine").toString());
                if(binding.etGoldRate.getText().toString().equals("")){
                    binding.tvBalanceFine.setText(balance_fine+"");
                }else {
                    binding.tvBalanceFine.setText("");
                }

            }
            if(!binding.etGoldRate.getText().toString().equals("")) {
                total_amt = (Double.parseDouble(binding.etGoldRate.getText().toString()) * balance_fine)
                        + (Double.parseDouble(getIntent().getStringExtra("old_amount").toString()) + Double.parseDouble(getIntent().getStringExtra("laboure_amount").toString()));
            }else{
                total_amt = (0.0 * balance_fine)
                        + (Double.parseDouble(getIntent().getStringExtra("old_amount").toString()) + Double.parseDouble(getIntent().getStringExtra("laboure_amount").toString()));
            }
            binding.tvTotalAmt.setText(total_amt+"");
            if(!binding.etReceivedAmt.getText().toString().equals("") && !binding.etRoundOffAmt.getText().toString().equals("") ) {
                binding.tvBalanceAmt.setText(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString()) -
                        Double.parseDouble(binding.etRoundOffAmt.getText().toString())+"");
            }else if(!binding.etReceivedAmt.getText().toString().equals("") && binding.etRoundOffAmt.getText().toString().equals("") ){
                binding.tvBalanceAmt.setText(total_amt - Double.parseDouble(binding.etReceivedAmt.getText().toString()) -
                        0+"");
            }else if(binding.etReceivedAmt.getText().toString().equals("") && !binding.etRoundOffAmt.getText().toString().equals("") ){
                binding.tvBalanceAmt.setText(total_amt - 0 -
                        Double.parseDouble(binding.etRoundOffAmt.getText().toString())+"");
            }else{
                binding.tvBalanceAmt.setText(total_amt - 0 - 0 +"");
            }

        }
    }

    private void generateOrderInvoice() {
        OrderGenerateReq request = new OrderGenerateReq(SharedPref.getString("user_id", ""),
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
               productList);


        Log.e("Token", SharedPref.getString("token", "") + "");
        Call<ResponseBody> call = apiInterface.createOrder("Bearer " + SharedPref.getString("token", ""), request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("OrderResponse", response.body().string());
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

}
