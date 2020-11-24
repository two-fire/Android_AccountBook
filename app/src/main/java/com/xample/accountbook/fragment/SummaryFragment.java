package com.xample.accountbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.xample.accountbook.R;
import com.xample.accountbook.db.AccountDao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SummaryFragment extends Fragment {
    private View mRootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_summary, container, false);
        initView();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        // 显示金额
        AccountDao dbManager = new AccountDao(getContext());
        TextView textViewSummary = mRootView.findViewById(R.id.textViewSummary);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String currentDateMonth = sdf.format(new Date());
        double summary = dbManager.getIncomeSum(currentDateMonth) -
                dbManager.getOutlaySum(currentDateMonth);
        textViewSummary.setText(String.valueOf(summary));

        // 饼图绘制
        PieChart pieChart = mRootView.findViewById(R.id.pie_chart);
        ChartManager chartManager = new ChartManager(this.getActivity());
        chartManager.showPieChartAccount(pieChart,currentDateMonth);

    }
}