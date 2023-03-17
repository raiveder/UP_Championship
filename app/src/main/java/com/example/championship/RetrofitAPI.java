package com.example.championship;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("user/login/")
    Call<Users> getToken(@Body Users data);

    @GET("feelings/")
    Call<FeelingsLists> getFeelings();

    @GET("quotes/")
    Call<QuotesLists> getQuotes();
}