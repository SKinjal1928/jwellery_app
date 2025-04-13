package app.bhavarlal.trilokchandhi.sons.ltd.retrofit;

import app.bhavarlal.trilokchandhi.sons.ltd.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetroInterface {

    @FormUrlEncoded
    @POST("/auth/login")
    Call<LoginResponse> loginApi(@Field("username") String username, @Field("password") String password);


    @FormUrlEncoded
    @POST("/customer/list")
    Call<LoginResponse> getCustomerApi(@Field("auth") String auth);
//    Call<LoginResponse> loginApi(@Body LoginReq user);
/*

    @GET("fetch-shipment-data/{ship_id}")
    Call<FetchShipmentResponse> pendingDataList(@Header("Authorization") String token,
                                                @Path("ship_id") String ship_id);


    @GET("fetch-shipment-order/{ship_id}")
    Call<FetchShipmentResponse> fetchShipmentOrder(@Header("Authorization") String token,
                                                @Path("ship_id") String ship_id);

    @GET("fetch-product")
    Call<FetchProductList> fetchProductList(@Header("Authorization") String token);

    @GET("fetch-open-order")
    Call<FetchOrderList> fetchOrderList(@Header("Authorization") String token);

    @GET("fetch-shipment")
    Call<FetchShipmentList> fetchShipmentList(@Header("Authorization") String token);

    @POST("create-customer")
    Call<CreateCustomer> createCustomer(@Body CustomerReq createCustomer, @Header("Authorization") String token);

    @POST("create-order")
    Call<CreateOrder> createOrder(@Body ProductReq createCustomer, @Header("Authorization") String token);

    @GET("get-shipment-product/{ship_id}")
    Call<app.textile.oltyems.model.FetchProductList> fetchShipmentProduct(@Header("Authorization") String token,
                                                                          @Path("ship_id") String ship_id);
*/
}
