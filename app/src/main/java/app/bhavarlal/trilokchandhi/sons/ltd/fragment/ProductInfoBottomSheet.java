package app.bhavarlal.trilokchandhi.sons.ltd.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

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

import app.bhavarlal.trilokchandhi.sons.ltd.common.NetworkUtils;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityProductInfoBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.CustomerResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInfoBottomSheet extends BottomSheetDialogFragment {

    ActivityProductInfoBinding binding;
    RetroInterface apiInterface;
    List<CustomerResponse.Datum> customerList = new ArrayList<>();
    ArrayAdapter<CustomerResponse.Datum> adapter;
    String customer_id = "";
    private boolean isItemSelectedManually = false;
    OrderListResponse.Datum order;

    public static ProductInfoBottomSheet newInstance() {
        return new ProductInfoBottomSheet();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ActivityProductInfoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharedPref.init(requireContext());
        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);

        if (OrderHolder.getOrder() != null) {
            order = OrderHolder.getOrder();
            binding.btnNext.setText("Save & Update");
            binding.etDate.setText(order.getOrderDateDmy());
            binding.searchAutoComplete.setText(order.getCustomer().getName());
            binding.txtAddress.setText(order.getCustomer().getAddress());
            binding.txtPhone.setText(order.getCustomer().getContact());
        } else {
            binding.etDate.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
        }

        binding.btnNext.setOnClickListener(view1 -> {
            if (binding.btnNext.getText().toString().contains("Update")) {
                order.getCustomer().setName(binding.searchAutoComplete.getText().toString());
                order.getCustomer().setAddress(binding.txtAddress.getText().toString());
                order.getCustomer().setContact(binding.txtPhone.getText().toString());
                order.setOrderDateDmy(binding.etDate.getText().toString());
                OrderHolder.setOrder(order);
                dismiss();
            } else {
                Intent i;
                if (OrderHolder.getOrder() != null) {
                    OrderListResponse.Datum datum = OrderHolder.getOrder();
                    if (datum.getOrderProduct().size() > 1) {
                        i = new Intent(getContext(), app.bhavarlal.trilokchandhi.sons.ltd.activity.EditInvoiceByPartActivity.class);
                    } else {
                        i = new Intent(getContext(), app.bhavarlal.trilokchandhi.sons.ltd.activity.InvoiceDetailActivity.class);
                        i.putExtra("position", 0);
                    }
                    i.putExtra("order", datum);
                } else {
                    i = new Intent(getContext(), app.bhavarlal.trilokchandhi.sons.ltd.activity.InvoiceDetailActivity.class);
                    i.putExtra("position", 0);
                }
                i.putExtra("date", binding.etDate.getText().toString());
                i.putExtra("customer", binding.searchAutoComplete.getText().toString());
                i.putExtra("customer_id", customer_id);
                i.putExtra("address", binding.txtAddress.getText().toString());
                startActivity(i);
            }
        });

        binding.etDate.setOnClickListener(v -> showOrderDatePickerDialog(binding.etDate));

        binding.searchAutoComplete.setOnClickListener(v -> binding.searchAutoComplete.showDropDown());
        binding.searchAutoComplete.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                new Handler().postDelayed(() -> binding.searchAutoComplete.showDropDown(), 100);
            }
        });

        binding.searchAutoComplete.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 300;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        requireActivity().runOnUiThread(() -> {
                            String query = s.toString().trim();
                            if (!query.isEmpty() && !isItemSelectedManually) {
                                List<CustomerResponse.Datum> filtered = getFilteredUsers(query);
                                adapter.clear();
                                adapter.addAll(filtered);
                                adapter.notifyDataSetChanged();
                                binding.searchAutoComplete.showDropDown();
                            }
                            isItemSelectedManually = false;
                        });
                    }
                }, DELAY);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_dropdown_item_1line, customerList);
        binding.searchAutoComplete.setAdapter(adapter);
        binding.searchAutoComplete.setThreshold(1);

        binding.searchAutoComplete.setOnItemClickListener((parent, v, position, id) -> {
            isItemSelectedManually = true;
            CustomerResponse.Datum selected = adapter.getItem(position);
            customer_id = selected.getId() + "";
            binding.txtAddress.setText(selected.getAddress());
            binding.txtPhone.setText(selected.getContact());
        });

        if (NetworkUtils.isInternetAvailable(requireContext())) {
            getCustomers();
        } else {
            Toast.makeText(requireContext(), "Please check connection!", Toast.LENGTH_SHORT).show();
        }
    }

    private List<CustomerResponse.Datum> getFilteredUsers(String query) {
        List<CustomerResponse.Datum> filtered = new ArrayList<>();
        for (CustomerResponse.Datum user : customerList) {
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(user);
            }
        }
        return filtered;
    }

    private void getCustomers() {
        Call<CustomerResponse> call = apiInterface.getCustomers("Bearer " + SharedPref.getString("token", ""));
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    customerList.clear();
                    customerList = response.body().getData();
                    // Sort alphabetically by name
                    Collections.sort(customerList, new Comparator<CustomerResponse.Datum>() {
                        @Override
                        public int compare(CustomerResponse.Datum b1, CustomerResponse.Datum b2) {
                            return b1.getName().compareToIgnoreCase(b2.getName());
                        }
                    });

//                    customerList.addAll(customerList);
//                    adapter.notifyDataSetChanged();

                    // Extract names into string list
                    List<String> customerNames = new ArrayList<>();
                    for (CustomerResponse.Datum customer : customerList) {
                        customerNames.add(customer.getName());
                    }
                    // Set to AutoCompleteTextView
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                            android.R.layout.simple_dropdown_item_1line, customerNames);
                    binding.searchAutoComplete.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(requireContext(), "Customer retrieve error..", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showOrderDatePickerDialog(TextView txtDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year1, monthOfYear, dayOfMonth1) -> {
                    String date = String.format(Locale.getDefault(), "%02d-%02d-%d", dayOfMonth1, monthOfYear + 1, year1);
                    txtDate.setText(date);
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}

