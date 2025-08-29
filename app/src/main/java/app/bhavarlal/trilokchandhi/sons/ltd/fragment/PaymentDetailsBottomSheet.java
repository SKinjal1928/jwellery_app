package app.bhavarlal.trilokchandhi.sons.ltd.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.activity.DashboardActivity;
import app.bhavarlal.trilokchandhi.sons.ltd.activity.EditInvoiceByPartActivity;
import app.bhavarlal.trilokchandhi.sons.ltd.activity.PaymentDetailsActivity;
import app.bhavarlal.trilokchandhi.sons.ltd.activity.ProductInfoActivity;
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

public class PaymentDetailsBottomSheet  extends BottomSheetDialogFragment {
    private ActivityPaymentDetailsBinding binding;
    private RetroInterface apiInterface;
    private List<OrderGenerateReq.Product> productList = new ArrayList<>();
    private Double gst_amt = 0.0, total_amt = 0.0, gst_per_amt = 0.0;
    private String gst_payment_type = "", narration ="";
    private OrderListResponse.Datum order;
    private Double balance_fine = 0.0;
    public static final String TAG = "PaymentDetailsBottomSheet";

    public static PaymentDetailsBottomSheet newInstance(Bundle args) {
        PaymentDetailsBottomSheet fragment = new PaymentDetailsBottomSheet();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ActivityPaymentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPref.init(requireContext());
        productList = new ArrayList<>();
        if (getArguments() != null && getArguments().getParcelableArrayList("products") != null) {
            productList = getArguments().getParcelableArrayList("products");
        }

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        order = OrderHolder.getOrder();

        if (order != null) {
            binding.btnSubmit.setText("Save & Update");
            binding.backButton.setVisibility(View.GONE);
            binding.etGoldRate.setText(String.valueOf(order.getGoldRate()));
            binding.etWeight.setText(String.valueOf(order.getMetalRWeight()));
            binding.etPurity.setText(String.valueOf(order.getMetalRPurity()));
            binding.tvFine.setText(String.valueOf(order.getMetalRFine()));
            binding.etNarration.setText(String.valueOf(order.getComment2()));
            binding.tvBalanceFine.setText(String.valueOf(order.getBalanceFine()));
            binding.etRoundOffAmt.setText(String.valueOf(order.getRoundOffAmount()));
            binding.etReceivedAmt.setText(String.valueOf(order.getReceivedAmount()));
            binding.tvTotalAmt.setText(String.valueOf(order.getTotalAmount()));
            binding.tvBalanceAmt.setText(String.valueOf(order.getBalanceAmount()));

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
        binding.etChqDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrderDatePickerDialog(binding.etChqDate);
            }
        });

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
                    order.setBalanceFine(Double.parseDouble(binding.tvBalanceFine.getText().toString()+""));
                    order.setRoundOffAmount(Double.parseDouble(binding.etRoundOffAmt.getText().toString()+""));
                    order.setReceivedAmount(binding.etReceivedAmt.getText().toString()+"");
                    order.setBalanceAmount(Double.parseDouble(binding.tvBalanceAmt.getText().toString()+""));

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
                    dismiss();
                }else {
                    Intent i = new Intent(requireActivity(), PaymentDetailsActivity.class);
                    i.putExtra("date", getArguments().getString("date").toString());
                    i.putExtra("customer", getArguments().getString("customer").toString());
                    i.putExtra("address", getArguments().getString("address").toString());
                    i.putExtra("customer_id", getArguments().getString("customer_id").toString());

                    i.putParcelableArrayListExtra("products", getArguments().getParcelableArrayList("products"));
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

                    i.putExtra("fine", getArguments().getString("fine").toString());
                    i.putExtra("old_fine", getArguments().getString("old_fine").toString());
                    i.putExtra("balance_fine", getArguments().getString("balance_fine").toString());
                    i.putExtra("old_amount", getArguments().getString("old_amount").toString());
                    i.putExtra("comment_1", getArguments().getString("comment_1").toString());
                    i.putExtra("mode_of_payment", getArguments().getString("mode_of_payment").toString());
                    i.putExtra("other_purity", getArguments().getString("other_purity").toString());

                    i.putExtra("gold_rate", binding.etGoldRate.getText().toString() + "");
                    i.putExtra("total_amount", binding.tvTotalAmt.getText().toString() + "");
                    i.putExtra("amount", binding.tvAmount.getText().toString() + "");
                    i.putExtra("gst_percentage", "3");

                    i.putExtra("cheque_no", binding.etChqNo.getText().toString() + "");
                    i.putExtra("cheque_date", binding.etChqDate.getText().toString() + "");
                    i.putExtra("cheque_bank_name", binding.etBankName.getText().toString() + "");
                    i.putExtra("comment_2", binding.etNarration.getText().toString() + "");
                    i.putExtra("selectedPayment", getArguments().getString("selectedPayment").toString() + "");

                    startActivity(i);

                    generateOrderInvoice();
                }


            }
        });
    }

    private void textWatcherNonGSTRate() {
        binding.etWeight.addTextChangedListener(createGenericWatcher());
        binding.etPurity.addTextChangedListener(createGenericWatcher());
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
        binding.etGoldRate.addTextChangedListener(createGenericWatcher());
        binding.etReceivedAmt.addTextChangedListener(createGenericWatcher());
        binding.etRoundOffAmt.addTextChangedListener(createGenericWatcher());
    }

    private TextWatcher createGenericWatcher() {
        return new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { showViewAsPerPayment(); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };
    }

    private void showViewAsPerPayment() {
        String labourAmountStr = getArguments().getString("laboure_amount", "0");
        String oldAmountStr = getArguments().getString("old_amount", "0");
        String fineStr = getArguments().getString("fine", "0");
        String modeOfPayment = getArguments().getString("mode_of_payment", "");

        double labourAmount = parseDoubleSafe(labourAmountStr);
        double oldAmount = parseDoubleSafe(oldAmountStr);
        double fine = parseDoubleSafe(fineStr);

        if (modeOfPayment.equals("GST")) {
            balance_fine = fine;
            narration =  binding.etNarration.getText().toString();
            binding.llGST.setVisibility(View.VISIBLE);
            binding.llNonGST.setVisibility(View.GONE);

            double rate = parseDoubleSafe(binding.etGoldRate.getText().toString());
            if(binding.etGoldRate.getText().toString().equals("")){
                gst_amt = fine * 0.0;
                gst_per_amt = (0 * 0.03) + (gst_amt * 0.03);
                binding.tvGstPercentage.setText(0.00 +"");
            }else {
                gst_amt = fine * rate;
                gst_per_amt = (labourAmount * 0.03) + (gst_amt * 0.03);
                binding.tvGstPercentage.setText(String.format("%.2f", gst_per_amt));
            }

            binding.tvAmount.setText(Math.round(gst_amt)+"");

            total_amt = gst_amt + gst_per_amt + labourAmount + oldAmount;
            binding.tvTotalAmt.setText(Math.round( total_amt)+"");

            double received = parseDoubleSafe(binding.etReceivedAmt.getText().toString());
            binding.tvBalanceAmt.setText(Math.round(total_amt - received)+"");

            Log.e("sending fine Gst",getArguments().getString("fine").toString());

        } else {

            narration =  binding.etNarrationNonGst.getText().toString();
            binding.llNonGST.setVisibility(View.VISIBLE);
            binding.llGST.setVisibility(View.GONE);

            double weight = parseDoubleSafe(binding.etWeight.getText().toString());
            double purity = parseDoubleSafe(binding.etPurity.getText().toString());

            double fineCalculated = (weight * purity) / 100.0;
            binding.tvFine.setText(String.valueOf(fineCalculated));
            balance_fine = fine - fineCalculated;

            double rate = parseDoubleSafe(binding.etGoldRate.getText().toString());
            total_amt = (rate * balance_fine) + (labourAmount + oldAmount);
            binding.tvTotalAmt.setText(Math.round(total_amt)+"");

            double received = parseDoubleSafe(binding.etReceivedAmt.getText().toString());
            double roundOff = parseDoubleSafe(binding.etRoundOffAmt.getText().toString());

            binding.tvBalanceAmt.setText(Math.round( total_amt - received - roundOff)+"");

            if(!binding.etWeight.getText().toString().equals("")&&
                    !binding.etPurity.getText().toString().equals("")){
                binding.tvFine.setText(String.format("%.3f",fineCalculated) +"");
                if(binding.etPurity.getText().toString().equals("99.5")){
                    balance_fine = fine - weight;
                }else {
                    balance_fine = fine - fineCalculated;
                }
                if(binding.etGoldRate.getText().toString().equals("")) {
//                    balance_fine = fine - fineCalculated;
                    binding.tvBalanceFine.setText(String.format("%.3f",balance_fine)+ "");
                }else {
//                    balance_fine = 0.000;
                    binding.tvBalanceFine.setText("");
                }
                total_amt = (rate * balance_fine) + (labourAmount + oldAmount);
                binding.tvTotalAmt.setText(Math.round(total_amt)+"");
                binding.tvBalanceAmt.setText(Math.round( total_amt - received - roundOff)+"");
            }else{
                binding.tvFine.setText(0.000  +"");
                balance_fine = fine;
                if(binding.etGoldRate.getText().toString().equals("")){
//                    balance_fine = fine;
                    binding.tvBalanceFine.setText(String.format("%.3f",balance_fine)+"");
                }else {
//                    balance_fine = 0.000;
                    binding.tvBalanceFine.setText("");
                }

            }

            Log.e("sending fine",getArguments().getString("fine").toString());
            Log.e("sending balance_fine",balance_fine +"" );
        }
    }

    private double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void generateOrderInvoice() {
        OrderGenerateReq request;
        String chqDate = binding.etChqDate.getText().toString();

        String customerId = getArguments().getString("customer_id", "");
        String date = getArguments().getString("date", "");
        double oldFine = parseDoubleSafe(getArguments().getString("old_fine", "0"));
        double oldAmount = parseDoubleSafe(getArguments().getString("old_amount", "0"));
        double otherPurity = parseDoubleSafe(getArguments().getString("other_purity", "0"));
        String modeOfPayment = getArguments().getString("mode_of_payment", "");

        double weight = parseDoubleSafe(binding.etWeight.getText().toString());
        double purity = parseDoubleSafe(binding.etPurity.getText().toString());
        double fine = parseDoubleSafe(binding.tvFine.getText().toString());
        double totalAmt = parseDoubleSafe(binding.tvTotalAmt.getText().toString());
        double receivedAmt = parseDoubleSafe(binding.etReceivedAmt.getText().toString());
        double roundOffAmt = parseDoubleSafe(binding.etRoundOffAmt.getText().toString());
        double balanceAmt = parseDoubleSafe(binding.tvBalanceAmt.getText().toString());
        double goldRate = parseDoubleSafe(binding.etGoldRate.getText().toString());

        Log.e("sending fine", fine+"");
        Log.e("sending balance_fine",balance_fine +"" );
        Log.e("sending balanceAmt",balanceAmt +"" );
        request = new OrderGenerateReq(
                SharedPref.getString("user_id", ""),
                SharedPref.getString("delivery_id", ""),
                customerId,
                date,
                oldFine,
                oldAmount,
                "",
                weight,
                purity,
                fine,
                balance_fine,
                gst_amt,
                3.0,
                receivedAmt,
                roundOffAmt,
                balanceAmt,
                binding.etChqNo.getText().toString(),
                chqDate.isEmpty() ? null : chqDate,
                binding.etBankName.getText().toString(),
                getArguments().getString("comment_1").toString(),
                narration,
                otherPurity,
                modeOfPayment,
                goldRate,
                totalAmt,
                gst_payment_type,
                productList
        );

        Log.e("Token", SharedPref.getString("token", ""));
        Call<ResponseBody> call = apiInterface.createOrder("Bearer " + SharedPref.getString("token", ""), request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("OrderResponse", response.body().string());
                        Toast.makeText(requireContext(), "Order added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(requireContext(), DashboardActivity.class));
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("OrderError", "Code: " + response.code()+response.message());
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(requireContext(), DashboardActivity.class));
                            dismiss();
                        } else {
                            Toast.makeText(requireContext(), "An unknown error occurred.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(requireContext(), DashboardActivity.class));
                            dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(requireContext(), "Error parsing error message.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(requireContext(), DashboardActivity.class));
                        dismiss();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
                requireActivity(),
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
    @Override
    public void onStart() {
        super.onStart();
       /* BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        if (dialog != null) {
            FrameLayout bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                // Set height to MATCH_PARENT so it can expand fully
                bottomSheet.setLayoutParams(new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED); // Optional
                behavior.setSkipCollapsed(true); // Optional
            }
        }*/
    }

}

