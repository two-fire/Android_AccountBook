package com.xample.accountbook.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xample.accountbook.R;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.entity.AccountCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private Button buttonAddIncomeCategory, buttonAddOutlayCategory;
    private String[] mFrom = {"icon","title"};
    private int[] mTo = {R.id.imageViewCategory,R.id.textViewCategory};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        buttonAddIncomeCategory = (Button)this.findViewById(R.id.buttonAddIncomeCategory);
        buttonAddOutlayCategory = (Button)this.findViewById(R.id.buttonAddOutlayCategory);

        buttonAddIncomeCategory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonAddIncomeCategoryOnClick();
            }

        });

        buttonAddOutlayCategory.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                buttonAddOutlayCategoryOnClick();
            }

        });

        refreshData();

    }
    private void refreshData() {
        AccountDao dbManager = new AccountDao(this);
        //显示到界面
        GridView gridView = (GridView)this.findViewById(R.id.gridView1);
        //Adapter
        // List<AccountCategory> incomeCategoryList = getTestDataIncome();
        List<AccountCategory> incomeCategoryList = dbManager.getIncomeType();
        List<Map<String, Object>> incomeList = new ArrayList<>();
        for (AccountCategory c: incomeCategoryList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", c.getIcon());
            map.put("title", c.getCategory());
            incomeList.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, incomeList,
                R.layout.category_item, mFrom, mTo);
        gridView.setAdapter(adapter);


        //显示到界面
        GridView gridView2 = (GridView)this.findViewById(R.id.gridView2);
        //Adapter
        //List<AccountCategory> outlayCategoryList = getTestDataOutlay();
        List<AccountCategory> outlayCategoryList = dbManager.getOutlayType();
        List<Map<String, Object>> outlayList = new ArrayList<>();
        for (AccountCategory c: outlayCategoryList){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("icon", c.getIcon());
            map.put("title", c.getCategory());
            outlayList.add(map);
        }

        SimpleAdapter adapter2 = new SimpleAdapter(this, outlayList,
                R.layout.category_item, mFrom, mTo);
        gridView2.setAdapter(adapter2);
    }
    private void buttonAddIncomeCategoryOnClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.input_category);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = input.getText().toString();
                addIncomeCategory(category,R.drawable.home_icon);
                refreshData();
            }
        });
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    protected void addIncomeCategory(String category,int icon) {
        AccountDao dbManager = new AccountDao(this);
        dbManager.addIncomeCategory(category,icon);

    }

    private void buttonAddOutlayCategoryOnClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.input_category);
        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String category = input.getText().toString();
                addOutlayCategory(category,R.drawable.entertainment_icon);
                refreshData();
            }
        });
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    protected void addOutlayCategory(String category,int icon) {
        AccountDao dbManager = new AccountDao(this);
        dbManager.addOutlayCategory(category,icon);
    }
    private List<AccountCategory> getTestDataIncome() {
        List<AccountCategory> incomeList = new ArrayList<>();
        incomeList.add(new AccountCategory(1,"工资",
                R.drawable.fund_icon));
        incomeList.add(new AccountCategory(2,"奖金",
                R.drawable.insurance_icon));
        incomeList.add(new AccountCategory(3,"兼职收入",
                R.drawable.baby_icon));
        return incomeList;
    }
    private List<AccountCategory> getTestDataOutlay() {
        List<AccountCategory> outlayList=new ArrayList<>();
        outlayList.add(new AccountCategory(1,"交通",R.drawable.traffic_icon));
        outlayList.add(new AccountCategory(2,"食物",R.drawable.breakfast_icon));
        outlayList.add(new AccountCategory(3,"图书",R.drawable.book_icon));
        outlayList.add(new AccountCategory(3,"电影",R.drawable.film_icon));
        return outlayList;
    }
}