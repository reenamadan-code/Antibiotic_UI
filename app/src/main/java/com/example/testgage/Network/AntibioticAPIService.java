package com.example.testgage.Network;

import com.example.testgage.Model.Metric;
import com.example.testgage.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Body;
import retrofit2.http.Query;


public interface AntibioticAPIService {
    @GET("users/{email}")
    Call<User> getUser(@Path("email") String email, @Header("Authorization") String auth);

    @GET("metrics/{metricId}")
    Call<List<Metric>> getMetrics(@Path("metricId") String metricId, @Query("from") String from, @Query("to") String to, @Header("Authorization") String auth);

    @POST("users")
    Call<User> createUser(@Body User user, @Header("Authorization") String auth);

    @POST("metrics")
    Call<Metric> postMetric(@Body Metric metric, @Header("Authorization") String auth);
}
