package com.xample.accountbook.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.xample.accountbook.db.AccountDao;
import com.xample.accountbook.entity.AccountItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChartManager {
    private  Activity mContext;
    ArrayList<Integer> mOriginColors = new ArrayList<>();

    public ChartManager(Activity ctx) {
        this.mContext = ctx;
        mOriginColors.add(Color.rgb(205, 205, 205));
        mOriginColors.add(Color.rgb(114, 188, 223));
        mOriginColors.add(Color.rgb(255, 123, 124));
        mOriginColors.add(Color.rgb(255, 228, 196));
        mOriginColors.add(Color.rgb(123, 104, 238));
        mOriginColors.add(Color.rgb(154, 205, 50));
        mOriginColors.add(Color.rgb(238, 238, 0));
        mOriginColors.add(Color.rgb(255, 193, 193));

    }

    public void showPieChartAccount(PieChart pieChart, String date) {
        //获取数据
        AccountDao dbManager = new AccountDao(mContext);

        List<AccountItem> incomeAccountList = dbManager.getOutlayStaticList(date);

        ArrayList<String> xValues = new ArrayList<>();
        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < incomeAccountList.size(); i++) {
            xValues.add(incomeAccountList.get(i).getCategory());
            yValues.add(new Entry((float) incomeAccountList.get(i).getMoney(),
                    i,incomeAccountList.get(i).getCategory()));
            colors.add(mOriginColors.get(i%mOriginColors.size()));
        }

        // y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,"支出");
        // 设置饼图之间的距离
        pieDataSet.setSliceSpace(0f);
        pieDataSet.setColors(colors);

        // 设置数据
        PieData pieData = new PieData(xValues,pieDataSet);
        pieData.setValueTextSize(14f);
        pieData.setHighlightEnabled(true);
        pieChart.setData(pieData);

        // 设置动画
        pieChart.animateXY(1000,1000);
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px);
    }
}
