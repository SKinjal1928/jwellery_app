package app.bhavarlal.trilokchandhi.sons.ltd.retrofit;

import android.content.Context;

import java.io.IOException;

import app.bhavarlal.trilokchandhi.sons.ltd.common.SharedPref;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private String token;
    private Context context;


    public TokenInterceptor(String token, Context context) {
        this.token = token;
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = SharedPref.getString("token", "");

        // Attach Authorization Header
        Request newRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(newRequest);
    }

}
