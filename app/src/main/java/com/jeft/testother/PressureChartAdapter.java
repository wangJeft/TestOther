package com.jeft.testother;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PressureChartAdapter extends BaseQuickAdapter<PressureRecord, BaseViewHolder> {
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy#MM-dd hh:mm", Locale.getDefault());
    private int chartBgHeight;
    private final float pressureMax = 350f;
    private float interval;
    private String lastMonthDay;
    private String lastYear;

    public PressureChartAdapter(int chartBgHeight) {
        super(R.layout.item_chart_pressure_record);
        this.chartBgHeight = chartBgHeight;
        this.interval = chartBgHeight / pressureMax;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PressureRecord pressureRecord) {
        Log.d("Pressure", "pressureRecord.id: " + pressureRecord.id);
        String format = formatter.format(new Date(pressureRecord.recordTime));
        String[] split = format.split("#");
        String year = split[0];
        String monthDay = split[1];
        baseViewHolder.setText(R.id.tv_month_day, monthDay);
        baseViewHolder.setText(R.id.tv_year, year);
        /*if (!TextUtils.equals(lastMonthDay, monthDay)) {
            baseViewHolder.setText(R.id.tv_month_day, monthDay);
            lastMonthDay = monthDay;
        } else {
            baseViewHolder.setText(R.id.tv_month_day, "");
        }

        if (!TextUtils.equals(lastYear, year)) {
            baseViewHolder.setText(R.id.tv_year, year);
            lastYear = year;
        } else {
            baseViewHolder.setText(R.id.tv_year, "");
        }*/
        baseViewHolder.setText(R.id.tv_systolic, String.valueOf(pressureRecord.systolic));
        baseViewHolder.setText(R.id.tv_diastolic, String.valueOf(pressureRecord.diastolic));

        View view = baseViewHolder.getView(R.id.v_bar);
        View vSpace = baseViewHolder.getView(R.id.v_space);
        int pressureChartBg = PressureHelper.getPressureChartBg(pressureRecord.systolic, pressureRecord.diastolic);
        view.setBackgroundResource(pressureChartBg);

        float barHeight = (pressureRecord.systolic - pressureRecord.diastolic) * interval;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) barHeight;
        view.setLayoutParams(params);

        float spaceHeight = pressureRecord.diastolic * interval;
        ViewGroup.LayoutParams spaceLayoutParams = vSpace.getLayoutParams();
        spaceLayoutParams.height = (int) spaceHeight;
        vSpace.setLayoutParams(spaceLayoutParams);
    }
}
