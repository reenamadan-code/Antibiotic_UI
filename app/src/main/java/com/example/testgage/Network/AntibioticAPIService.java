package com.example.testgage.Network;

import com.example.testgage.Model.Metric;
import com.example.testgage.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;


public interface AntibioticAPIService {
    @GET("users/{email}")
    Call<User> loadUser(@Path("email") String email, @Header("Authorization") String auth);

    @POST("users")
    Call<User> createUser(@Body User user, @Header("Authorization") String auth);

    @POST("metrics")
    Call<Metric> postMetric(@Body Metric metric, @Header("Authorization") String auth);
}
