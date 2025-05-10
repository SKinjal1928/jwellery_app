package app.bhavarlal.trilokchandhi.sons.ltd.retrofit;

import app.bhavarlal.trilokchandhi.sons.ltd.model.CustomerResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.DeliveryVoucherReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.DeliveryVoucherResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseListResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseRequest;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseUpdateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ExpenseUpdateResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.LoginResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderGenerateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.OrderUpdateReq;
import app.bhavarlal.trilokchandhi.sons.ltd.model.ProductResponse;
import app.bhavarlal.trilokchandhi.sons.ltd.model.StockResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetroInterface {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<LoginResponse> loginApi(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/refresh-token")
    Call<ResponseBody> refreshTokenOnUnAuth(@Field("refresh_token") String refresh_token);

    @POST("/customer/list")
    Call<CustomerResponse> getCustomers(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("/product/list")
    Call<ProductResponse> getProductInfoApi(@Field("auth") String auth);

    @POST("/delivery-voucher/Get-One")
    Call<DeliveryVoucherResponse> getVoucher(@Header("Authorization") String token,
                                             @Body DeliveryVoucherReq request);

    @POST("/delivery-voucher/stock")
    Call<StockResponse> getDeliveryVoucherStock(@Header("Authorization") String token,
                                                @Body ExpenseListReq request);

    @POST("/expense/create")
    Call<ResponseBody> sendExpenseApi(@Header("Authorization") String token,
                                      @Body ExpenseRequest request);

    @POST("/expense/list")
    Call<ExpenseListResponse> getExpenseList(@Header("Authorization") String token,
                                             @Body ExpenseListReq request);

    @POST("/expense/update")
    Call<ExpenseUpdateResponse> updateExpenseApi(@Header("Authorization") String token,
                                                 @Body ExpenseUpdateReq request);

    @FormUrlEncoded
    @POST("/expense/delete")
    Call<ResponseBody> deleteExpense(@Header("Authorization") String token,
                                     @Field("id") String id);

    @FormUrlEncoded
    @POST("/expense/expense-pdf")
    Call<ResponseBody> expenseReport(@Header("Authorization") String token,
                                     @Body ExpenseListReq request);




    @POST("/order/create")
    Call<ResponseBody> createOrder(@Header("Authorization") String token,
                                   @Body OrderGenerateReq request);

    @POST("/order/update")
    Call<ResponseBody> updateOrder(@Header("Authorization") String token,
                                   @Body OrderUpdateReq request);

    @POST("/order/list")
    Call<ExpenseListResponse> getOrderList(@Header("Authorization") String token,
                                           @Body ExpenseListReq request);

    @FormUrlEncoded
    @POST("/order/Order-pdf")
    Call<ResponseBody> orderSalesReport(@Header("Authorization") String token,
                                        @Body ExpenseListReq request);

    @POST("/order/Get-One")
    Call<ExpenseListResponse> getOrderById(@Header("Authorization") String token,
                                           @Body ExpenseListReq request);

    @POST("/expense/get-one")
    Call<ExpenseListResponse> getExpenseById(@Header("Authorization") String token,
                                           @Body ExpenseListReq request);

}
