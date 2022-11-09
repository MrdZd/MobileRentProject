package com.zz.mobilerentproject.view.walletmodel;

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
import com.zz.mobilerentproject.bean.UserData;
import com.zz.mobilerentproject.bean.WalletData;
import com.zz.mobilerentproject.databinding.ActivityCurOrderBinding;
import com.zz.mobilerentproject.databinding.ActivityWalletBinding;
import com.zz.mobilerentproject.http.HttpLoginService;
import com.zz.mobilerentproject.http.HttpWalletService;
import com.zz.mobilerentproject.http.RetrofitManager;
import com.zz.mobilerentproject.util.UserModel;
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

public class WalletActivity extends AppCompatActivity {

    private String                  balance;
    private Retrofit                retrofit;
    private UserModel               userModel;
    private static UserModelManager manager;
    private HttpWalletService       httpWalletService;
    private RetrofitManager         retrofitManager;
    private ActivityWalletBinding   binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        retrofitManager = new RetrofitManager();
        retrofit = retrofitManager.getRetrofit();
        httpWalletService = retrofit.create(HttpWalletService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initData();
        initOnClickListener();
    }

    private void initData() {
        GetBalanceRequest(userModel.user_email);
    }

    private void initOnClickListener() {
        binding.walletBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rechargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateBalanceRequest(userModel.user_email, "5");
            }
        });
    }

    private void GetBalanceRequest(String email) {
        Call<BasicData<WalletData>> call = httpWalletService.post(email);
        call.enqueue(new Callback<BasicData<WalletData>>() {
            @Override
            public void onResponse(Call<BasicData<WalletData>> call, Response<BasicData<WalletData>> response) {
                BasicData<WalletData> data = response.body();
                balance = data.getData().getBalance().toString();
                binding.balance.setText(balance);
            }

            @Override
            public void onFailure(Call<BasicData<WalletData>> call, Throwable t) {

            }
        });
    }

    private void UpdateBalanceRequest(String email, String value) {

        Call<BasicData<WalletData>> call = httpWalletService.post(email, value);
        call.enqueue(new Callback<BasicData<WalletData>>() {
            @Override
            public void onResponse(Call<BasicData<WalletData>> call, Response<BasicData<WalletData>> response) {
                BasicData<WalletData> data = response.body();
                balance = data.getData().getBalance().toString();
                binding.balance.setText(balance);
            }

            @Override
            public void onFailure(Call<BasicData<WalletData>> call, Throwable t) {

            }
        });
    }

}
