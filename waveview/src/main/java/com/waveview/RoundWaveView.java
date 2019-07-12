package com.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.waveview.balls.BallsView;
import com.waveview.waveview.R;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class RoundWaveView extends RelativeLayout {
    public static final int MARGIN = -1;
    private Context context;
    private RoundType roundType = RoundType.normal;
    private boolean isBigSize = false;
    private boolean isRect = false;

    public RoundWaveView(Context context) {
        this(context, null);
    }

    public RoundWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainWaveView);
            try {
                roundType = RoundType.getFromInt(typedArray.getInt(R.styleable.MainWaveView_roundWaveType, roundType.value));
                isBigSize = typedArray.getBoolean(R.styleable.MainWaveView_isBigSize, false);
                isRect = typedArray.getBoolean(R.styleable.MainWaveView_isRect, false);
                post(new Runnable() {
                    @Override
                    public void run() {
                        int width = getWidth();
                        init(width);
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isRect) {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
    public void init(int width) {
        LayoutParams mainParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        if (isBigSize) {
            int w = width / 2 + 100;
            int h = w * 2 / 3;
            mainParams.setMargins(MARGIN * w, MARGIN * h + 50, MARGIN * w, MARGIN * h - 30);
        }

        FrameLayout framePhoto = new FrameLayout(context);
        framePhoto.setLayoutParams(mainParams);

        GifImageView roundWaveView = new GifImageView(context);
        roundWaveView.setBackgroundResource(roundType.res);

        FrameLayout.LayoutParams frameLayout = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        roundWaveView.setLayoutParams(frameLayout);
        framePhoto.addView(roundWaveView);

        addView(framePhoto);
        invalidate();
    }

}
