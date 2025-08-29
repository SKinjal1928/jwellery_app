package app.bhavarlal.trilokchandhi.sons.ltd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import app.bhavarlal.trilokchandhi.sons.ltd.activity.PaymentDetailsActivity;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityFinalDetailsBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;

public class FinalDetailsBottomSheet extends BottomSheetDialogFragment {

    ActivityFinalDetailsBinding binding;
    String[] rateAppliedList = {"GST", "NON-GST"};
    double selectedStandard = 100.0;
    ArrayAdapter<String> rateAppliedAdapter;
    String selectedPayment = "";
    OrderListResponse.Datum order;
    public static final String TAG = "FinalDetailsBottomSheet";

    public static FinalDetailsBottomSheet newInstance(Bundle args) {
        FinalDetailsBottomSheet fragment = new FinalDetailsBottomSheet();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ActivityFinalDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        binding.tvFine.setText(bundle.getDouble("fine", 0.0)+"");
        binding.etLabour.setText(Math.round(bundle.getDouble("laboure_amount", 0.0))+"");

        if (bundle != null) {
            String date = bundle.getString("date");
            String customer = bundle.getString("customer");
            String address = bundle.getString("address");
            String customerId = bundle.getString("customer_id");
            ArrayList<OrderGenerateReq.Product> products = bundle.getParcelableArrayList("products");
            Double fine = bundle.getDouble("fine");
            Double laboureAmount = bundle.getDouble("laboure_amount");

            // Set UI:
            binding.tvFine.setText(fine+"");
            binding.etLabour.setText(Math.round(laboureAmount)+"");
        }

        order = OrderHolder.getOrder();

        if (order != null) {
            binding.backButton.setVisibility(View.GONE);
            binding.etOldFine.setText(order.getOldFine());
            binding.etOldAmount.setText(order.getOldAmount());
            selectedPayment = order.getModeOfPayment();
            binding.searchPaymentMode.setText(selectedPayment);
            binding.etNarration.setText(order.getComment1());
            setSpinnerValue(binding.spinnerStandard, order.getOtherPurity());
        }

        binding.btnSubmit.setOnClickListener(v -> {
            if (order != null) {
                order.setOldFine(binding.etOldFine.getText().toString());
                order.setBalanceFine(Double.parseDouble(binding.tvTotalFine.getText().toString()));
                order.setOldAmount(binding.etOldAmount.getText().toString());
                order.setComment1(binding.etNarration.getText().toString());
                order.setModeOfPayment(selectedPayment);
                order.setOtherPurity(String.valueOf(selectedStandard));
                OrderHolder.setOrder(order);
                dismiss();
            } else {
                Bundle bundle1 = new Bundle();
                bundle1.putString("date", bundle.getString("date", ""));
                bundle1.putString("customer", bundle.getString("customer", ""));
                bundle1.putString("address", bundle.getString("address", ""));
                bundle1.putString("customer_id", bundle.getString("customer_id", ""));
                bundle1.putParcelableArrayList("products", bundle.getParcelableArrayList("products"));
                bundle1.putString("fine", binding.tvTotalFine.getText().toString());
                bundle1.putString("old_fine", binding.etOldFine.getText().toString());
                bundle1.putString("balance_fine", binding.tvTotalFine.getText().toString());
                bundle1.putString("old_amount", binding.etOldAmount.getText().toString());
                bundle1.putString("comment_1", binding.etNarration.getText().toString());
                bundle1.putString("mode_of_payment", selectedPayment);
                bundle1.putString("other_purity", String.valueOf(selectedStandard));
                bundle1.putString("selectedPayment", selectedPayment);
                bundle1.putString("laboure_amount", bundle.getDouble("laboure_amount")+"");
                PaymentDetailsBottomSheet sheet = PaymentDetailsBottomSheet.newInstance(
                        bundle1);

                sheet.show(requireActivity().getSupportFragmentManager(), PaymentDetailsBottomSheet.TAG);

               /* Intent i = new Intent(getActivity(), PaymentDetailsActivity.class);
                i.putExtra("date", bundle.getString("date", ""));
                i.putExtra("customer", bundle.getString("customer", ""));
                i.putExtra("address", bundle.getString("address", ""));
                i.putExtra("customer_id", bundle.getString("customer_id", ""));
                i.putParcelableArrayListExtra("products", bundle.getParcelableArrayList("products"));

                i.putExtra("fine", binding.tvTotalFine.getText().toString());
                i.putExtra("old_fine", binding.etOldFine.getText().toString());
                i.putExtra("balance_fine", binding.tvTotalFine.getText().toString());
                i.putExtra("old_amount", binding.etOldAmount.getText().toString());
                i.putExtra("comment_1", binding.etNarration.getText().toString());
                i.putExtra("mode_of_payment", selectedPayment);
                i.putExtra("other_purity", String.valueOf(selectedStandard));
                i.putExtra("selectedPayment", selectedPayment);
                startActivity(i);*/
            }
        });

        textWatcherForTotalFine();
        searchPaymentListener();

        binding.spinnerStandard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStandard = Double.parseDouble(parent.getItemAtPosition(position).toString());
                double fine = Double.parseDouble(bundle.getDouble("fine", 0.0)+"");
                String formattedFine = new DecimalFormat("0.000").format(calculateFine(fine, selectedStandard));

                binding.tvFine.setText(formattedFine);
                updateFine();
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {
                selectedStandard = 100.0;
            }
        });
    }

    public void setSpinnerValue(Spinner spinner, String value) {
        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).toString().equalsIgnoreCase(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void textWatcherForTotalFine() {
        binding.etOldFine.addTextChangedListener(new TextWatcher() {
            @Override public void afterTextChanged(Editable s) { updateFine(); }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void updateFine() {
        try {
            String oldFineStr = binding.etOldFine.getText().toString().trim();
            double fine = Double.parseDouble(binding.tvFine.getText().toString());
            double totalFine = oldFineStr.isEmpty() ? fine : Double.parseDouble(oldFineStr) + fine;
            binding.tvTotalFine.setText(String.format(Locale.getDefault(), "%.3f", totalFine));
        } catch (NumberFormatException e) {
            binding.tvTotalFine.setText("0.0");
        }
    }

    public double calculateFine(double fine, double standard) {
        return (fine / standard) * 100;
    }

    private void searchPaymentListener() {
        binding.searchPaymentMode.setOnClickListener(v -> binding.searchPaymentMode.showDropDown());
        binding.searchPaymentMode.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> binding.searchPaymentMode.showDropDown(), 100);
            }
        });

        rateAppliedAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, rateAppliedList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                ((TextView) view.findViewById(android.R.id.text1)).setText(getItem(position));
                return view;
            }
        };

        binding.searchPaymentMode.setAdapter(rateAppliedAdapter);
        binding.searchPaymentMode.setThreshold(1);
        binding.searchPaymentMode.setOnItemClickListener((parent, view, position, id) -> {
            selectedPayment = rateAppliedAdapter.getItem(position);
        });
    }

}
