package app.bhavarlal.trilokchandhi.sons.ltd.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
//    private static String BASE_URL = "https://erp.oltymes.com/api/";
    private static String BASE_URL = "https://jewellerybackend-j98i.onrender.com";
    public static Retrofit getRetrofitInstance() {
//        TokenInterceptor interceptor=new TokenInterceptor();
//
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .build();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Logs request/response body

        // Add the interceptor to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client) // Set OkHttpClient with logging
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
