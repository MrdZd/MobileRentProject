package com.zz.mobilerentproject.view.registermodel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.FeedbackData;
import com.zz.mobilerentproject.bean.RegisterData;
import com.zz.mobilerentproject.bean.UserData;
import com.zz.mobilerentproject.databinding.ActivityLoginBinding;
import com.zz.mobilerentproject.databinding.ActivityRegisterBinding;
import com.zz.mobilerentproject.http.HttpLoginService;
import com.zz.mobilerentproject.http.HttpRegisterService;
import com.zz.mobilerentproject.http.RetrofitManager;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.loginmodel.LoginViewActivity;
import com.zz.mobilerentproject.view.mainpage.ViewPageActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterViewActivity extends AppCompatActivity {

    private Retrofit                    retrofit;
    private HttpRegisterService         httpRegisterService;
    private RetrofitManager             retrofitManager;
    private ActivityRegisterBinding     binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        retrofitManager = new RetrofitManager();
        retrofit = retrofitManager.getRetrofit();
        httpRegisterService = retrofit.create(HttpRegisterService.class);
        initOnClickListener();
    }

    private void initOnClickListener() {
        binding.btnRegisterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequest(binding.firstname.getText().toString(), binding.lastname.getText().toString(),
                        binding.userRegisterEmail.getText().toString(), binding.registerPassword.getText().toString());
            }
        });
    }

    private void HttpRequest(String firstname, String lastname, String email, String password) {
        RegisterData registerData = new RegisterData(firstname, lastname, email, password, "USER", "");
        Call<RegisterData> call = httpRegisterService.post(registerData);
        call.enqueue(new Callback<RegisterData>() {

            @Override
            public void onResponse(Call<RegisterData> call, Response<RegisterData> response) {
                RegisterData data = response.body();
                if(data.getCode().equals("200")){
                    Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Input Error, Please check", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterData> call, Throwable t) {

            }

        });
    }



}
