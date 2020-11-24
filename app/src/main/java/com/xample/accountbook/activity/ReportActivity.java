package com.xample.accountbook.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.xample.accountbook.R;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.entity.AccountItem;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ImageButton imageButtonQuery = findViewById(R.id.imageButtonQuery);
        imageButtonQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    //  查询实现
    private void query(){
        // 获取用户输入的起始日期和结束日期，选择支出还是收入
        EditText editTextBeginDate = findViewById(R.id.editTextBeginDate);
        EditText editTextEndDate = findViewById(R.id.editTextEndDate);
        RadioGroup radioGroup = findViewById(R.id.radioGroupType);

        String beginDate = editTextBeginDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();
        // 获取数据访问对象，查询数据
        AccountDao dbManager = new AccountDao(this);
        List<AccountItem> list;
        if (radioGroup.getCheckedRadioButtonId() == R.id.radioButtonIncome) {
            list = dbManager.queryIncomeList(beginDate,endDate);
        } else {
            list = dbManager.queryOutlayList(beginDate,endDate);
        }
        // 创建适配器，设置到ListView
        ArrayAdapter<AccountItem> adapter = new ArrayAdapter<AccountItem>(this,android.R.layout.simple_list_item_1,list);
        ListView listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);
    }
}