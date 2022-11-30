package com.jeft.testother;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    private RecyclerView rvBar;
    private RecyclerView rvRecord;
    private PressureChartAdapter chartAdapter;
    private PressureAdapter pressureAdapter;
    private ConstraintLayout clChartBg;
    private RelativeLayout rlMarkerView;
    private TextView tvContent;
    private Runnable runnable = () -> rlMarkerView.setVisibility(View.INVISIBLE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clChartBg = findViewById(R.id.cl_chart_bg);
        rlMarkerView = findViewById(R.id.rl_marker_view);
        tvContent = findViewById(R.id.tvContent);
        rvRecord = findViewById(R.id.rv_record);
        List<PressureRecord> recordList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            recordList.add(new PressureRecord(i, System.currentTimeMillis() + i * 10000, 40 + i * 10, 50 + i * 15, 70));
        }


        pressureAdapter = new PressureAdapter();
        pressureAdapter.setOnItemClickListener((adapter, view, position) -> {
            PressureRecord record = (PressureRecord) adapter.getItem(position);
            // TODO: 2022-11-30 wangh 跳转到记录页面

        });
        rvRecord.setAdapter(pressureAdapter);
        pressureAdapter.removeAllFooterView();
        if (recordList.size() <= 3) {
            pressureAdapter.setNewInstance(recordList);
        } else {
            List<PressureRecord> subList = recordList.subList(0, 3);
            pressureAdapter.setNewInstance(subList);
            View footView = LayoutInflater.from(this).inflate(R.layout.layout_pressure_see_all, null);
            pressureAdapter.addFooterView(footView);
            footView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2022-11-30 wangh 跳转到记录页面
                }
            });
        }
        clChartBg.post(() -> {
            int chartBgHeight = clChartBg.getMeasuredHeight();
            Log.d("Pressure", "chartBgHeight: " + chartBgHeight);
            Collections.reverse(recordList);
            initChartAdapter(recordList, chartBgHeight);
        });
    }

    private void initChartAdapter(List<PressureRecord> recordList, int chartBgHeight) {
        if (chartAdapter == null) {
            rvBar = findViewById(R.id.rv_bar);
            chartAdapter = new PressureChartAdapter(chartBgHeight);
            rvBar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            rvBar.setAdapter(chartAdapter);
            rvBar.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                rlMarkerView.setVisibility(View.INVISIBLE);
            });
            chartAdapter.setOnItemClickListener((adapter, view, position) -> {
                PressureRecord pressureRecord = (PressureRecord) adapter.getItem(position);
                String pressureDesc = PressureHelper.getPressureDesc(pressureRecord.systolic, pressureRecord.diastolic, MainActivity.this);
                tvContent.setText(pressureDesc);
                View tvSystolic = view.findViewById(R.id.tv_systolic);
                moveMarkerView(tvSystolic, tvSystolic.getMeasuredWidth());
            });
        }
        chartAdapter.setNewInstance(recordList);
        rvBar.scrollToPosition(recordList.size() - 1);
    }

    private void moveMarkerView(View target, int targetWidth) {
        rlMarkerView.removeCallbacks(runnable);
        int[] targetIndex = new int[2];
        int[] parentIndex = new int[2];
        target.getLocationInWindow(targetIndex);
        rlMarkerView.setVisibility(View.VISIBLE);
        clChartBg.getLocationInWindow(parentIndex);
        int targetY = targetIndex[1] - parentIndex[1] - rlMarkerView.getHeight();
        int targetX = targetIndex[0] - parentIndex[0] - (rlMarkerView.getWidth() / 2 - targetWidth / 2);
        rlMarkerView.setX(targetX);
        rlMarkerView.setY(targetY);
        rlMarkerView.postDelayed(runnable, 3000);
    }
}