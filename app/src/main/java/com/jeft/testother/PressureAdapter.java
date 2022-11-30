package com.jeft.testother;

import android.content.Context;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PressureAdapter extends BaseQuickAdapter<PressureRecord, BaseViewHolder> {
    public static final DateFormat format = new SimpleDateFormat("MM-dd,HH:mm", Locale.getDefault());

    public PressureAdapter() {
        super(R.layout.item_pressure_record);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PressureRecord pressureRecord) {
        baseViewHolder.setText(R.id.tv_diastolic, String.valueOf(pressureRecord.diastolic));
        baseViewHolder.setText(R.id.tv_systolic, String.valueOf(pressureRecord.systolic));
        int pressureLevel = PressureHelper.getPressureLevel(pressureRecord.systolic, pressureRecord.diastolic);
        Context context = getContext();
        baseViewHolder.setText(R.id.tv_datetime, format.format(new Date(pressureRecord.recordTime)));
        baseViewHolder.setText(R.id.tv_pulse, String.valueOf(pressureRecord.pulse));
        String pressureDesc = PressureHelper.getPressureDesc(pressureRecord.systolic, pressureRecord.diastolic, context);
        baseViewHolder.setText(R.id.tv_pressure_desc, pressureDesc);
    }
}