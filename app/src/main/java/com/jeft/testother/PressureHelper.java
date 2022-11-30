package com.jeft.testother;

import android.content.Context;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PressureHelper {
    public static final int PRESSURE_LOW = 0;
    public static final int PRESSURE_NORMAL = 1;
    public static final int PRESSURE_HIGH = 2;
    public static final int PRESSURE_HIGH_1 = 3;
    public static final int PRESSURE_HIGH_2 = 4;
    public static final int PRESSURE_HIGH_3 = 5;

    @IntDef(value = {PRESSURE_LOW, PRESSURE_NORMAL, PRESSURE_HIGH, PRESSURE_HIGH_1, PRESSURE_HIGH_2, PRESSURE_HIGH_3})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PressureType {
    }
    public static int getPressureLevel(int systolic, int diastolic) {
        if (systolic < 90 || diastolic < 60) {
            return PRESSURE_LOW;
        } else if ((systolic >= 90 && systolic <= 119) && (diastolic >= 60 && diastolic <= 79)) {
            return PRESSURE_NORMAL;
        } else if ((systolic >= 120 && systolic <= 129) && (diastolic >= 60 && diastolic <= 79)) {
            return PRESSURE_HIGH;
        } else if ((systolic >= 130 && systolic <= 139) || (diastolic >= 80 && diastolic <= 89)) {
            return PRESSURE_HIGH_1;
        } else if ((systolic >= 140 && systolic <= 180) || (diastolic >= 90 && diastolic <= 120)) {
            return PRESSURE_HIGH_2;
        } else {
            return PRESSURE_HIGH_3;
        }
    }

    public static int getPressureChartBg(int systolic, int diastolic) {
        int level = getPressureLevel(systolic, diastolic);
        switch (level) {
            case PressureHelper.PRESSURE_LOW:
                return R.drawable.bg_view_bar_low;
            case PressureHelper.PRESSURE_NORMAL:
                return R.drawable.bg_view_bar_normal;
            case PressureHelper.PRESSURE_HIGH:
                return R.drawable.bg_view_bar_high;
            case PressureHelper.PRESSURE_HIGH_1:
                return R.drawable.bg_view_bar_high_1;
            case PressureHelper.PRESSURE_HIGH_2:
                return R.drawable.bg_view_bar_high_2;
            case PressureHelper.PRESSURE_HIGH_3:
                return R.drawable.bg_view_bar_high_3;
        }
        return R.drawable.bg_view_bar_normal;
    }

    public static String getPressureDesc(int systolic, int diastolic, Context context) {
        int level = getPressureLevel(systolic, diastolic);
        switch (level) {
            case PressureHelper.PRESSURE_LOW:
                return "低血压";
            case PressureHelper.PRESSURE_NORMAL:
                return "正常";
            case PressureHelper.PRESSURE_HIGH:
                return "偏高";
            case PressureHelper.PRESSURE_HIGH_1:
                return "高血压 一级";
            case PressureHelper.PRESSURE_HIGH_2:
                return "高血压 二级";
            case PressureHelper.PRESSURE_HIGH_3:
                return "高血压 三级";
        }
        return "";
    }
}
