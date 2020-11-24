package com.xample.accountbook.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xample.accountbook.R;
import com.xample.accountbook.activity.AccountEditActivity;
import com.xample.accountbook.adapter.AccountItemAdapter;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.entity.AccountItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncomeFragment extends Fragment {

    View mRootView;
    ListView listView;
    Button buttonAdd;
    public IncomeFragment() {}
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_income, container, false);
        initView();
        return mRootView;
    }

    private void initView() {
        buttonAdd = mRootView.findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAddOnClick();
            }
        });
        ListView listView = (ListView)mRootView.findViewById(R.id.listView1);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                deleteItem(id);
                return true;
            }

        });
        refreshData();
    }

    private void buttonAddOnClick() {
        Intent in = new Intent(this.getActivity(), AccountEditActivity.class);
        in.putExtra("isIncome",true);
        //startActivity(in);
        startActivityForResult(in,1);
    }

    //刷新界面
    private void refreshData() {
        AccountDao  dbManager = new AccountDao(getContext());
        List<AccountItem>   incomeAccountList = dbManager.getIncomeList();

        AccountItemAdapter adapter = new AccountItemAdapter(incomeAccountList,getActivity());
        ListView listView = (ListView) mRootView.findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        TextView textViewIncomeSummary = (TextView) mRootView.findViewById(R.id.textViewIncomeSummary);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currentMonth = sdf.format(new Date());
        double incomeSum = dbManager.getIncomeSum(currentMonth);
        textViewIncomeSummary.setText(String.valueOf(incomeSum));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("accountbook","onActivityResult");
        refreshData();
    }

    protected void deleteItem(final long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(R.string.delete_confirm_title);
        builder.setMessage(R.string.delete_confirm_msg);

        builder.setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AccountDao dbManager = new AccountDao(getContext());
                dbManager.deleteIncome(id);
                refreshData();
            }
        });
        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private List<AccountItem> geTestData() {
        List<AccountItem> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AccountItem item = new AccountItem();
            item.setId(i);
            item.setCategory("兼职收入");
            item.setMoney(i*100);
            item.setDate("2019-01-0" + i);
            result.add(item);
        }
        return result;
    }


}