package app.bhavarlal.trilokchandhi.sons.ltd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import app.bhavarlal.trilokchandhi.sons.ltd.R;
import app.bhavarlal.trilokchandhi.sons.ltd.adapter.ItemProductAdapter;
import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import app.bhavarlal.trilokchandhi.sons.ltd.databinding.ActivityEditInvoiceBinding;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderHolder;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderUpdateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetroInterface;
import app.bhavarlal.trilokchandhi.sons.ltd.retrofit.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInvoiceByPartActivity extends AppCompatActivity {
    ActivityEditInvoiceBinding binding;
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
        binding = ActivityEditInvoiceBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        SharedPref.init(this);

        apiInterface = RetrofitClient.getRetrofitInstance().create(RetroInterface.class);
        /*setupCard(R.id.cardHoursWorked, "Hours Worked", R.drawable.invoice);
        setupCard(R.id.cardPayPeriod, "Pay Period", R.drawable.ic_arrow);
        setupCard(R.id.cardWageDetails, "Wage Details", R.drawable.ic_rupee);*/
        order = OrderHolder.getOrder();

        binding.layoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditInvoiceByPartActivity.this, ProductInfoActivity.class);
                i.putExtra("order", order);
                startActivity(i);
            }
        });
        binding.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order != null) {
                    Intent i;
//                    if (order.getOrderProduct().size() > 1) {
                        i = new Intent(EditInvoiceByPartActivity.this, ProductListingActivity.class);
                        i.putExtra("order", order);
                        startActivity(i);

//                    }
                }
            }
        });

        binding.layoutPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order != null) {
//                    if (order.getOrderProduct().size() > 1) {

                        Intent i = new Intent(EditInvoiceByPartActivity.this, PaymentDetailsActivity.class);
                        i.putExtra("laboure_amount", l_amt_total + "");

                        i.putExtra("fine", (((fine_total / Double.parseDouble(order.getOtherPurity()))*100) + Double.parseDouble(order.getOldFine()))+ "" + "");
                        i.putExtra("order", order);
                        i.putExtra("mode_of_payment", order.getModeOfPayment() + "");

                        i.putExtra("laboure_amount", l_amt_total + "");
                        i.putExtra("old_amount", order.getOldAmount() + "");
                        startActivity(i);


//                    }
                }
            }
        });

        binding.layoutBillDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (order != null) {
//                    if (order.getOrderProduct().size() > 1) {
                        Intent i = new Intent(EditInvoiceByPartActivity.this, FinalDetailsActivity.class);
                        i.putExtra("laboure_amount", l_amt_total + "");
                        i.putExtra("fine", fine_total+ "");
                        i.putExtra("order", order);
                        startActivity(i);

//                    }
                }
            }
        });

        /*for (int i = 0; i < order.getOrderProduct().size(); i++) {
            *//*fin_total = fine_total + old_fine(from finalDetailsActivity)*//*
            fine_total = fine_total + Double.parseDouble(order.getOrderProduct().get(i).getFine());
            l_amt_total = l_amt_total + Double.parseDouble(order.getOrderProduct().get(i).getLaboureAmount());
        }*/

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateOrderInvoice();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        OrderHolder.getOrder();// Always refresh from source
        fine_total = 0.0;
        l_amt_total = 0.0;
        order = OrderHolder.getOrder();
        if(OrderHolder.getOrder().getOrderProduct() != null){
            for (int i = 0; i < order.getOrderProduct().size(); i++) {
                fine_total = fine_total + Double.parseDouble(order.getOrderProduct().get(i).getFine());
                l_amt_total = l_amt_total + Double.parseDouble(order.getOrderProduct().get(i).getLaboureAmount());
            }
        }

        Log.e("updated", OrderHolder.getOrder().getOrderDateDmy() + "");
    }

    private void generateOrderInvoice() {
        List<OrderUpdateReq.Product> productList = new ArrayList<>();

        for (OrderListResponse.OrderProduct op : order.getOrderProduct()) {
            OrderUpdateReq.Product product = new OrderUpdateReq.Product();

            // Example mapping – adjust based on actual fields
            product.id = String.valueOf(op.getProductId());        // if you have getProductId()
            product.quantity = op.getQuantity();          // if you have getQuantity()
            product.purity = op.getPurity();                // etc.
            product.less_weight = op.getLessWeight();
            product.gross_weight = op.getGrossWeight();
            product.wastage = op.getWastage();
            product.laboure_amount = op.getLaboureAmount();
            product.laboure_rate = op.getLaboureRate();
            product.rate_on = op.getRateOn();
            product.fine = op.getFine();


            // Add to list
            productList.add(product);
        }


        OrderUpdateReq request;
        if ( order.getChequeDate() == null) {
            request = new OrderUpdateReq(order.getId(), SharedPref.getString("user_id", ""),
                    SharedPref.getString("delivery_id", ""),
                    order.getCustomer().getId().toString(),
                    order.getOrderDateDmy().toString(),
                    order.getOldFine() == null ? 0.0 :Double.parseDouble(order.getOldFine().toString()),
                    order.getOldAmount() == null ? 0.0 :Double.parseDouble(order.getOldAmount()),
                    "",
                    order.getMetalRWeight() == null ? 0.0 :Double.parseDouble(order.getMetalRWeight()),
                    order.getMetalRPurity() == null ? 0.0 :Double.parseDouble(order.getMetalRPurity()),
                    order.getMetalRFine() == null ? 0.0 :Double.parseDouble(order.getMetalRFine()),
                    order.getBalanceFine() == null ? 0.0 :order.getBalanceFine(),
                    order.getAmount() == null ? 0.0 :Double.parseDouble(order.getAmount()),
                    Double.parseDouble("3"),
                    (order.getReceivedAmount() == null || order.getReceivedAmount().toString().isEmpty()) ? 0.0 : Double.parseDouble(order.getReceivedAmount().toString()),
                    (order.getRoundOffAmount() == null || order.getRoundOffAmount().toString().isEmpty()) ? 0.0 : Double.parseDouble(order.getRoundOffAmount().toString()),
                    (order.getBalanceAmount() == null || order.getBalanceAmount().toString().isEmpty()) ? 0.0 : Double.parseDouble(order.getBalanceAmount().toString()),
                    order.getChequeNo() == null ? "" :order.getChequeNo().toString(), null,
                    order.getChequeBankName() == null ? "" :order.getChequeBankName().toString(),
                    order.getComment1().toString(),
                    order.getComment2().toString(),
                    Double.parseDouble(order.getOtherPurity()),
                    order.getModeOfPayment().toString(),
                    order.getGoldRate().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getGoldRate().toString()),
                    order.getTotalAmount().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getTotalAmount().toString()),
                    order.getPaymentType(),
                    productList);
        } else {
            request = new OrderUpdateReq(order.getId(), SharedPref.getString("user_id", ""),
                    SharedPref.getString("delivery_id", ""),
                    order.getCustomer().getId().toString(),
                    order.getOrderDateDmy().toString(),
                    Double.parseDouble(order.getOldFine().toString()),
                    Double.parseDouble(order.getOldAmount()),
                    "",
                    Double.parseDouble(order.getMetalRWeight()),
                    Double.parseDouble(order.getMetalRPurity()),
                    Double.parseDouble(order.getMetalRFine()),
                    order.getBalanceFine(),
                    Double.parseDouble(order.getAmount()),
                    Double.parseDouble("3"),
                    order.getReceivedAmount().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getReceivedAmount().toString()),
                    order.getRoundOffAmount().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getRoundOffAmount().toString()),
                    order.getBalanceAmount().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getBalanceAmount().toString()),
                    order.getChequeNo().toString(), order.getChequeDate().toString(),
                    order.getChequeBankName().toString(),
                    order.getComment1().toString(),
                    order.getComment2().toString(),
                    Double.parseDouble(order.getOtherPurity()),
                    order.getModeOfPayment().toString(),
                    order.getGoldRate().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getGoldRate().toString()),
                    order.getTotalAmount().toString().isEmpty() ? 0.0 : Double.parseDouble(order.getTotalAmount().toString()),
                    order.getPaymentType(),
                    productList);
        }


        Log.e("Token", SharedPref.getString("token", "") + "");
        Call<ResponseBody> call = apiInterface.updateOrder("Bearer " + SharedPref.getString("token", ""), request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Log.d("OrderResponse", response.body().string());
                        if (response.isSuccessful()) {
                            Toast.makeText(EditInvoiceByPartActivity.this, "Order added successfully", Toast.LENGTH_SHORT).show();
//                            finish();
                            startActivity(new Intent(EditInvoiceByPartActivity.this, DashboardActivity.class));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("OrderError", "Code: " + response.code());
                    try {

                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        if (jsonObject.has("message")) {
                            String errorMessage = jsonObject.getString("message");
                            Toast.makeText(EditInvoiceByPartActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EditInvoiceByPartActivity.this, "An unknown error occurred.", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(EditInvoiceByPartActivity.this, "Error parsing error message.", Toast.LENGTH_LONG).show();
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


}