package com.jeft.testother;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;


public class CustomRender extends BarChartRenderer {

    private final BarChart barChart;
    private Context context;
    private boolean isLTR;
    public final Handler mHanler;

    public CustomRender(Context context, boolean isLTR, BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
        this.context = context;
        this.isLTR = isLTR;
        BarDataProvider mChart1 = this.mChart;
        barChart = (BarChart) mChart1;
        mHanler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == 0) {
                    CustomRender.this.barChart.highlightValue(null, false);
                }
                return false;
            }
        });
    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {
        float[] mArr;
        BarData barData = this.barChart.getBarData();
        if (indices.length == 0) {
            return;
        }
        Highlight highlight = indices[0];
        IBarDataSet dataSetByIndex = barData.getDataSetByIndex(highlight.getDataIndex());
        BarEntry entryForXValue = dataSetByIndex.getEntryForXValue(highlight.getX(), highlight.getY());
        if (isInBoundsX(entryForXValue, dataSetByIndex) && (mArr = entryForXValue.getYVals()) != null) {
            if (!(mArr.length == 0)) {
                prepareBarHighlight(entryForXValue.getX(), mArr[0] + mArr[1], mArr[0], barData.getBarWidth() / 2, this.mChart.getTransformer(this.isLTR ? YAxis.AxisDependency.RIGHT : YAxis.AxisDependency.LEFT));
                RectF mBarRect = this.mBarRect;
                float centerX = mBarRect.centerX();
                float top = mBarRect.top;
                highlight.setDraw(centerX, top);
                this.mHanler.removeCallbacksAndMessages(null);
                this.mHanler.sendEmptyMessageDelayed(0, 2000L);
            }
        }
    }
}
