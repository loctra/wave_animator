package com.waveview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.waveview.waveview.R;

public class HorizontalWaveView extends View {

    private Path mPath;
    private Paint mPaint;

    private float frequency = 1.0f;
    private float IdleAmplitude = 0.00f;
    private int waveNumber = 2;
    private float phaseShift = -0.03f;
    private float initialPhaseOffset = 0.0f;
    private float waveHeight;
    private float waveVerticalPosition = 2;
    private int waveColor;
    private float phase;
    private float amplitude;
    private float level = 1.0f;

    ObjectAnimator mAmplitudeAnimator;

    public HorizontalWaveView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context, null);
    }

    public HorizontalWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    public HorizontalWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalWaveView);
        frequency = a.getFloat(R.styleable.HorizontalWaveView_waveFrequency, frequency);
        IdleAmplitude = a.getFloat(R.styleable.HorizontalWaveView_waveIdleAmplitude, IdleAmplitude);
        phaseShift = a.getFloat(R.styleable.HorizontalWaveView_wavePhaseShift, phaseShift);
        initialPhaseOffset = a.getFloat(R.styleable.HorizontalWaveView_waveInitialPhaseOffset, initialPhaseOffset);
        waveHeight = a.getDimension(R.styleable.HorizontalWaveView_waveHeight, waveHeight);
        waveColor = a.getColor(R.styleable.HorizontalWaveView_waveColor, waveColor);
        waveVerticalPosition = a.getFloat(R.styleable.HorizontalWaveView_waveVerticalPosition, waveVerticalPosition);
        waveNumber = a.getInteger(R.styleable.HorizontalWaveView_waveAmount, waveNumber);

        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(ContextCompat.getColor(context,R.color.pink));

        a.recycle();
        initAnimation();
    }

    private void initAnimation() {
        if (mAmplitudeAnimator == null) {
            mAmplitudeAnimator = ObjectAnimator.ofFloat(this, "amplitude", 1f);
            mAmplitudeAnimator.setDuration(1000);
            mAmplitudeAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        }
        if (!mAmplitudeAnimator.isRunning()) {
            mAmplitudeAnimator.start();
        }
    }

    public void initDefaultView(int color, float waveHeight) {
        setWaveColor(ContextCompat.getColor(getContext(), color));
        setWaveNumber(20);
        setWaveHeight(waveHeight);
        setWaveVerticalPosition(2);
        setStrokeWidth(1.2f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        updatePath();
    }

    private void updatePath() {
        mPath.reset();

        phase += phaseShift;
        amplitude = Math.max(level, IdleAmplitude);

        for (int i = waveNumber - 1; i >= 0; i--) {
            float halfHeight = getHeight() / waveVerticalPosition;
            float width = getWidth();
            float mid = width / 2.0f;

            float maxAmplitude = halfHeight - (halfHeight - waveHeight);

            float progress = 1.0f - (float) i / waveNumber;
            float normedAmplitude = (1.5f * progress - 0.5f) * amplitude;

            for (int x = 0; x < width; x++) {
                float scaling = (float) (-Math.pow(1 / mid * (x - mid), 2) + 1);
                float y = (float) (
                        scaling *
                                maxAmplitude *
                                normedAmplitude *
                                Math.sin(2 * Math.PI * (x / width) * frequency + phase + initialPhaseOffset) +
                                halfHeight);
                if (x == 0) {
                    mPath.moveTo(width, y);
                } else {
                    mPath.lineTo(width - x +i*3, y);
                }
            }
        }
    }

    private void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
        invalidate();
    }

    private float getAmplitude() {
        return this.amplitude;
    }

    public void stopAnimation() {
        if (mAmplitudeAnimator != null) {
            mAmplitudeAnimator.removeAllListeners();
            mAmplitudeAnimator.end();
            mAmplitudeAnimator.cancel();
        }
    }

    public void startAnimation() {
        if (mAmplitudeAnimator != null) {
            mAmplitudeAnimator.start();
        }
    }

    public void setWaveColor(int waveColor) {
        mPaint.setColor(waveColor);
        invalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        mPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
        invalidate();
    }

    public void setWaveHeight(float waveHeight) {
        this.waveHeight = waveHeight;
        invalidate();
    }

    public void setWaveVerticalPosition(float waveVerticalPosition) {
        this.waveVerticalPosition = waveVerticalPosition;
        invalidate();
    }
}