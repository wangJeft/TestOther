package com.jeft.testother;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class StackedBarsMarkerView extends MarkerView {

    private TextView tvContent;
    private RelativeLayout rlMvRoot;
    public StackedBarsMarkerView(Context context) {
        super(context, R.layout.custom_marker_view);

        tvContent = findViewById(R.id.tvContent);
        rlMvRoot = findViewById(R.id.rl_marker_view);
    }

    // runs every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        if (e instanceof BarEntry) {

            BarEntry be = (BarEntry) e;

            if (be.getYVals() != null) {
                if (highlight.getStackIndex() != 1) {
                    rlMvRoot.setVisibility(View.GONE);
                } else {
                    rlMvRoot.setVisibility(View.VISIBLE);
                    tvContent.setText(Utils.formatNumber(be.getYVals()[highlight.getStackIndex()], 0, true));
                }
                // draw the stack value
            } else {
                tvContent.setText(Utils.formatNumber(be.getY(), 0, true));
            }
        } else {

            tvContent.setText(Utils.formatNumber(e.getY(), 0, true));
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
