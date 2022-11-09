package com.zz.mobilerentproject.view.paymentmodel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.bean.BasicData;
import com.zz.mobilerentproject.bean.WalletData;
import com.zz.mobilerentproject.databinding.ActivityMapScanBinding;
import com.zz.mobilerentproject.databinding.ActivityPaymentBinding;
import com.zz.mobilerentproject.http.HttpOrderService;
import com.zz.mobilerentproject.http.HttpRentService;
import com.zz.mobilerentproject.http.HttpWalletService;
import com.zz.mobilerentproject.http.RetrofitManager;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;
import com.zz.mobilerentproject.view.loginmodel.LoginViewActivity;
import com.zz.mobilerentproject.view.mainpage.ViewPageActivity;
import com.zz.mobilerentproject.view.ordermodel.CurOrderActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {

    private Bundle                  bundle;
    private Retrofit                retrofit;
    private UserModel               userModel;
    private static UserModelManager manager;
    private HttpWalletService       httpWalletService;
    private HttpRentService         httpRentService;
    private RetrofitManager         retrofitManager;
    private ActivityPaymentBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        bundle = this.getIntent().getExtras();//获取bundle对象
        retrofitManager = new RetrofitManager();
        retrofit = retrofitManager.getRetrofit();
        httpWalletService = retrofit.create(HttpWalletService.class);
        httpRentService = retrofit.create(HttpRentService.class);
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initOnClickListener();
    }

    private void initOnClickListener() {
        binding.backMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                RentEndRequest(userModel.user_email, ts);
            }
        });
    }

    private void RentEndRequest(String email, String endTime) {
        Call<BasicData> call = httpRentService.post_end(email, endTime);
        call.enqueue(new Callback<BasicData>() {
            @Override
            public void onResponse(Call<BasicData> call, Response<BasicData> response) {
                BasicData data = response.body();
                if(data.getCode().equals("200")) {
                    UpdateBalanceRequest(userModel.user_email, bundle.getString("price"));
                }
                else{
                    Toast.makeText(getApplicationContext(), "payment fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasicData> call, Throwable t) {

            }
        });
    }

    private void UpdateBalanceRequest(String email, String value) {
        Call<BasicData<WalletData>> call = httpWalletService.post(email, value);
        call.enqueue(new Callback<BasicData<WalletData>>() {
            @Override
            public void onResponse(Call<BasicData<WalletData>> call, Response<BasicData<WalletData>> response) {
                BasicData<WalletData> data = response.body();
                if(data.getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(), "payment success", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "payment fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BasicData<WalletData>> call, Throwable t) {

            }
        });
    }



}
