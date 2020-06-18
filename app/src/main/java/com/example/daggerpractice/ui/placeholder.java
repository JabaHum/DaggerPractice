package com.example.daggerpractice.ui;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface placeholder {
    @GET
    Call<ResponseBody> getFakeStuff();
}
