package com.zz.mobilerentproject.view.ordermodel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.zz.mobilerentproject.R;
import com.zz.mobilerentproject.databinding.ActivityCurOrderBinding;
import com.zz.mobilerentproject.databinding.ActivityHisOrderBinding;


public class HistoryOrderActivity extends AppCompatActivity {

    private Bundle                  bundle;
    private ActivityHisOrderBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHisOrderBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        bundle = this.getIntent().getExtras();//获取bundle对象
        initView();
        initOnClickListener();
    }

    private void initView() {
        binding.hisOrderTime.setText(bundle.getString("time"));
        binding.hisOrderPrice.setText(bundle.getString("price"));
        binding.endPlace.setText(bundle.getString("address"));
    }

    private void initOnClickListener() {

    }

}
