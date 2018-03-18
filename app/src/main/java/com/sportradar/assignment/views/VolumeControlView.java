package com.sportradar.assignment.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.Space;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sportradar.assignment.R;

public class VolumeControlView extends LinearLayout {

    private int lines;
    private float volumePercent;
    private int color;

    private LinearLayout root;
    private TextView volumeLabel;


    private OnClickListener onBarClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            setVolumeForBar((Integer) view.getTag());
        }
    };


    public VolumeControlView(Context context) {
        super(context);
        init(null);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public VolumeControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public VolumeControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        inflate(getContext(), R.layout.view_volume_control, this);

        this.root =  findViewById(R.id.root);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.VolumeControlView, 0, 0);
        try {
            this.lines = a.getInt(R.styleable.VolumeControlView_scale_lines, 10);
            this.volumePercent = a.getFloat(R.styleable.VolumeControlView_volume_percent, 50);
            this.color = a.getColor(R.styleable.VolumeControlView_scale_color, ContextCompat.getColor(getContext(), R.color.view_volume_control_scale_active));
        } catch (Exception e) {
            a.recycle();
        }

        clearView();
        showScale();
        this.setVolumePercent(this.volumePercent);
    }

    /**
     * Removes all subviews from root view
     */
    private void clearView() {
        //just in case we remove all listeners
        for(int i = 0; i < this.root.getChildCount(); i++) {
            View view = this.root.getChildAt(i);
            view.setOnClickListener(null);
        }
        root.removeAllViewsInLayout();
    }


    /**
     * Add the view to the root element so that scale is shown
     */
    private void showScale() {
        //we want views to be numbered from bottom to top
        for(int i = this.lines - 1; i >= 0; i--) {
            LayoutParams spaceParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 0.5f);
            Space space = new Space(getContext());
            space.setLayoutParams(spaceParams);
            this.root.addView(space);

            LayoutParams scaleBarParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            View scaleBar = new View(getContext());
            scaleBar.setLayoutParams(scaleBarParams);
            scaleBar.setBackgroundColor(Color.BLACK);
            scaleBar.setOnClickListener(this.onBarClickListener);
            scaleBar.setTag(i);
            this.root.addView(scaleBar);
        }


        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float dp = 5f;
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);

        LayoutParams labelParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        labelParams.setMargins(0, pixels, 0, pixels);

        TextView label = new TextView(getContext());
        label.setLayoutParams(labelParams);
        label.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        this.volumeLabel = label;
        this.updateVolumeLabel();
        this.root.addView(label);

    }

    /**
     * Sets the volume and updates scale views for selected bar
     * @param barNumber zero base bar number from bottom to top
     */
    private void setVolumeForBar(int barNumber) {
        //so we don't have any rounding errors, the top most bar is always 100%
        if (barNumber == this.lines -1) {
            if (barNumber == this.lines - 1) {
                this.setVolumePercent(100);
            }
        } else {
            float percentPerBar =  100 / (this.lines - 1);
            this.setVolumePercent(barNumber * percentPerBar);
        }
    }

    /**
     * Colors all scale bars inactive
     */
    private void colorAllBarsInactive() {
        for(int i = 0; i < this.root.getChildCount(); i++) {
            View view = this.root.getChildAt(i);
            if(view.getTag() != null) {
                view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.view_volume_control_scale_inactive));
            }
        }
    }

    /**
     * Colors scale bars with "active" color
     * @param activeBars number of active bars that will be colored
     */
    private void colorActiveBars(int activeBars) {
        for(int i = 0; i < this.root.getChildCount(); i++) {
            View view = this.root.getChildAt(i);
            if(view.getTag() != null && view.getTag() instanceof Integer) {
                int tag = (Integer) view.getTag();
                if(tag > activeBars) {
                    view.setBackgroundColor(this.color);
                }
            }
        }
    }

    /**
     * Updates bottom Volume label based on current set volume
     */
    private void updateVolumeLabel() {
        String percent = String.format("%.2f", this.getVolumePercent());
        String labelText = getContext().getResources().getString(R.string.view_volume_control_volume_label, percent);
        this.volumeLabel.setText(labelText);
    }

    /**
     * Updates scale bars with active/inactive color for current volume
     */
    private void updateVolumePercent() {
        this.updateVolumeLabel();

        //update colors of active/inactive bars
        float percentPerBar = 100 / (this.lines - 1);

        this.colorAllBarsInactive();

        int numberOfActiveBars = (int) Math.ceil( this.volumePercent / percentPerBar);
        this.colorActiveBars(numberOfActiveBars);
    }

    /**
     *
     * @return number of bars in scale
     */
    public int getLines() {
        return lines;
    }

    /**
     * Sets the number of bars in scale and updates the scale views
     * @param lines
     */
    public void setLines(int lines) {
        this.lines = lines;
        this.clearView();
        this.showScale();
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    /**
     * Sets the volume percent and updates the scale views
     * @param volumePercent
     */
    public void setVolumePercent(float volumePercent) {
        this.volumePercent = volumePercent;
        updateVolumePercent();
    }


    /**
     *
     * @return the current active color of scale bars
     */
    public int getColor() {
        return color;
    }

    /**
     * Sets the color of active bars in scale and updates the views
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        //this clears the color and updates the bars with new selected color
        this.updateVolumePercent();
    }

}
