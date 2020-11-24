package com.xample.accountbook.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xample.accountbook.R;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.entity.AccountCategory;
import com.xample.accountbook.entity.AccountItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountEditActivity extends AppCompatActivity {
    private List<AccountCategory> categoryList;
    private TextView textViewSelectedType;
    private EditText editTextMoney;
    private EditText editTextRemark;
    private boolean isIncome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        isIncome = this.getIntent().getBooleanExtra("isIncome",true);
        textViewSelectedType = findViewById(R.id.textViewSelectedType);
        editTextMoney = findViewById(R.id.editTextMoney);
        editTextRemark = findViewById(R.id.editTextRemark);
        if (isIncome) {
            textViewSelectedType.setText("工资");
        } else {
            textViewSelectedType.setText("交通");
        }
        editTextMoney.setText("100");
        initView();
        Button buttonOk = (Button)this.findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonOkOnClick();

            }

        });

        editTextMoney.requestFocus();

    }


    private void initView() {
        AccountDao dbManager = new AccountDao(this);
        if (isIncome) {
            //getTextDataIncome();
            categoryList = dbManager.getIncomeType();
        } else {
            //getTestDataOutlay();
            categoryList = dbManager.getOutlayType();
        }
        //显示到界面
        GridView gridView = (GridView)this.findViewById(R.id.gridView1);
        //Adapter
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,categoryList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridViewOnItemClick(position);
            }
        });
    }
    private List<AccountCategory> getTestDataIncome() {
        categoryList = new ArrayList<>();
        categoryList.add(new AccountCategory(1,"工资",R.drawable.fund_icon));
        categoryList.add(new AccountCategory(2,"奖金",R.drawable.insurance_icon));
        categoryList.add(new AccountCategory(3,"兼职收入",R.drawable.baby_icon));
        return categoryList;
    }

    private List<AccountCategory> getTestDataOutlay() {
        categoryList=new ArrayList<>();
        categoryList.add(new AccountCategory(1,"交通",R.drawable.traffic_icon));
        categoryList.add(new AccountCategory(2,"食物",R.drawable.breakfast_icon));
        categoryList.add(new AccountCategory(3,"图书",R.drawable.book_icon));
        categoryList.add(new AccountCategory(3,"电影",R.drawable.film_icon));
        return categoryList;
    }


    private void gridViewOnItemClick(int position) {
        textViewSelectedType.setText(categoryList.get(position).toString());
    }
    protected void buttonOkOnClick() {
        AccountItem item = new AccountItem();

        item.setCategory(textViewSelectedType.getText().toString());
        item.setRemark(editTextRemark.getText().toString());
        item.setMoney(Double.parseDouble(editTextMoney.getText().toString()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        item.setDate(sdf.format(new Date()));

        AccountDao dbManager= new AccountDao(this);
        if (isIncome){
            dbManager.addOutlay(item);
        }
        else{
            dbManager.addOutlay(item);
        }
        this.setResult(1);
        this.finish();
    }
}
