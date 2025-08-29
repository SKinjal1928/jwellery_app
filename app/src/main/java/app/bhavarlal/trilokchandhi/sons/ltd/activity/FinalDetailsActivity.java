package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityFinalDetailsBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;

public class FinalDetailsActivity extends AppCompatActivity {
    ActivityFinalDetailsBinding binding;
    String[] rateAppliedList = {"GST","NON-GST"};
    double selectedStandard = 100.0; // default
    ArrayAdapter<String> rateAppliedAdapter;
    String selectedPayment = "";
    OrderListResponse.Datum order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFinalDetailsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.tvFine.setText(getIntent().getStringExtra("fine").toString());
        binding.etLabour.setText(getIntent().getStringExtra("laboure_amount").toString());
        order = OrderHolder.getOrder();
        if(order != null){
            binding.backButton.setVisibility(View.GONE);
            binding.etOldFine.setText(order.getOldFine());
            binding.etOldAmount.setText(order.getOldAmount());
            selectedPayment = order.getModeOfPayment();
            binding.searchPaymentMode.setText(selectedPayment);
            binding.etNarration.setText(order.getComment1());
            setSpinnerValue(binding.spinnerStandard, order.getOtherPurity());
        }
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(order != null){
//                    order.setOldFine(binding.etOldFine.getText().toString());
                    order.setOldFine(binding.etOldFine.getText().toString());
                    order.setBalanceFine(Double.parseDouble(binding.tvTotalFine.getText().toString()+""));
                    order.setOldAmount(binding.etOldAmount.getText().toString()+"");
                    order.setComment1(binding.etNarration.getText().toString());
                    order.setModeOfPayment(selectedPayment);
                    order.setOtherPurity(selectedStandard+"");

                    OrderHolder.setOrder(order);
                    finish();
                }else {
                    Intent i = new Intent(FinalDetailsActivity.this, PaymentDetailsActivity.class);
                    i.putExtra("date", getIntent().getStringExtra("date").toString());
                    i.putExtra("customer", getIntent().getStringExtra("customer").toString());
                    i.putExtra("address", getIntent().getStringExtra("address").toString());
                    i.putExtra("customer_id", getIntent().getStringExtra("customer_id").toString());
                    i.putParcelableArrayListExtra("products", getIntent().getParcelableArrayListExtra("products"));

/*
                    i.putExtra("product_id", getIntent().getStringExtra("product_id").toString());
                    i.putExtra("quantity", getIntent().getStringExtra("quantity").toString());
                    i.putExtra("gross_weight", getIntent().getStringExtra("gross_weight").toString());
                    i.putExtra("less_weight", getIntent().getStringExtra("less_weight").toString());
                    i.putExtra("purity", getIntent().getStringExtra("purity").toString());
                    i.putExtra("wastage", getIntent().getStringExtra("wastage").toString());
                    i.putExtra("laboure_rate", getIntent().getStringExtra("laboure_rate").toString());
                    i.putExtra("rate_on", getIntent().getStringExtra("rate_on").toString());
                    i.putExtra("laboure_amount", getIntent().getStringExtra("laboure_amount").toString());
*/

                    i.putExtra("fine", binding.tvTotalFine.getText().toString()+"");
                    i.putExtra("old_fine", binding.etOldFine.getText().toString()+"");
                    i.putExtra("balance_fine", binding.tvTotalFine.getText().toString()+"");
                    i.putExtra("old_amount", binding.etOldAmount.getText().toString()+"");
                    i.putExtra("comment_1", binding.etNarration.getText().toString()+"");
                    i.putExtra("mode_of_payment", selectedPayment);
                    i.putExtra("other_purity", selectedStandard+"");
                    i.putExtra("selectedPayment", selectedPayment+"");

                    startActivity(i);

                }


            }
        });
        searchPaymentListener();
        textWatcherForTotalFine();
        binding.spinnerStandard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                selectedStandard = Double.parseDouble(selected);
                // Optional: recalculate fine immediately
                double fine = Double.parseDouble(getIntent().getStringExtra("fine").toString());

//                String formatted = String.format(Locale.US, "%.3f", getIntent().getStringExtra("fine").toString());
//                binding.tvFine.setText(calculateFine(Double.parseDouble(formatted), selectedStandard)+"");
                binding.tvFine.setText(new DecimalFormat("0.000").format(calculateFine(Double.parseDouble(String.format(Locale.US, "%.3f", fine)), selectedStandard))+"");
                updateFine();

//                Log.e("fine.000", new DecimalFormat("0.000").format(calculateFine(Double.parseDouble(String.format(Locale.US, "%.3f", fine)), selectedStandard))+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // default to 100
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
            @Override
            public void afterTextChanged(Editable s) {
                updateFine();
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void updateFine() {
        try {
            if(binding.etOldFine.getText().toString().equals("")){
                binding.tvTotalFine.setText(String.format(Locale.getDefault(), "%.3f",  Double.parseDouble(binding.tvFine.getText().toString())));
            }else {

                double totalFine = Double.parseDouble(binding.etOldFine.getText().toString())+
                        Double.parseDouble(binding.tvFine.getText().toString());
                binding.tvTotalFine.setText(String.format(Locale.getDefault(), "%.3f", totalFine));
            }
        } catch (NumberFormatException e) {
            binding.tvTotalFine.setText("0.0"); // clear if input invalid
        }
    }

    public double calculateFine(double fine, double standard) {
        return (fine / standard)*100;
    }

    private void searchPaymentListener() {
        binding.searchPaymentMode.setOnClickListener(v -> binding.searchPaymentMode.showDropDown());
// Show dropdown on focus (with small delay to prevent first-click issue)
        binding.searchPaymentMode.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                v.postDelayed(() -> binding.searchPaymentMode.showDropDown(), 100);
            }
        });

        rateAppliedAdapter = new ArrayAdapter<String>(FinalDetailsActivity.this,
                android.R.layout.simple_dropdown_item_1line, rateAppliedList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position)); // Display product name

                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                textView.setText(getItem(position));
                return view;
            }
        };

        // Set adapter to AutoCompleteTextView
        binding.searchPaymentMode.setAdapter(rateAppliedAdapter);

        // Set threshold for when suggestions will show (1 char here)
        binding.searchPaymentMode.setThreshold(1);

        // Handle item click
        binding.searchPaymentMode.setOnItemClickListener((parent, view, position, id) -> {
            selectedPayment = rateAppliedAdapter.getItem(position);
           /* Toast.makeText(FinalDetailsActivity.this, "Selected: " +
                    selectedPayment, Toast.LENGTH_SHORT).show();*/
        });
    }
}
