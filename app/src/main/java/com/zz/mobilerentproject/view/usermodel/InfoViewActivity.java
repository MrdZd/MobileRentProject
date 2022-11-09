package com.zz.mobilerentproject.view.usermodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.databinding.ActivityInformationBinding;
import com.zz.mobilerentproject.databinding.ActivityPaymentBinding;
import com.zz.mobilerentproject.util.UserModel;
import com.zz.mobilerentproject.util.UserModelManager;

public class InfoViewActivity extends AppCompatActivity {

    private        UserModel            userModel;
    private static UserModelManager     manager;
    private ActivityInformationBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInformationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        manager = UserModelManager.getInstance();
        userModel = manager.getUserModel();
        initView();
    }

    private void initView() {
        binding.userInfoName.setText(userModel.user_name);
        binding.userInfoEmail.setText(userModel.user_email);
    }
}
