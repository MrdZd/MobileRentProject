package com.zz.mobilerentproject.http;

import com.zz.mobilerentproject.bean.RegisterData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpRegisterService {

    @POST("user/add")
    Call<RegisterData> post(@Body RegisterData registerData);

}
