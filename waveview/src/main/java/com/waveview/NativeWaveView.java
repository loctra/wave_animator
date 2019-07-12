package com.waveview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.waveview.waveview.R;
import com.joooonho.SelectableRoundedImageView;

@Deprecated
public class NativeWaveView extends RelativeLayout {


    public NativeWaveView(Context context) {
        super(context);
    }

    public NativeWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public NativeWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private final int[] countWave = {3, 4, 12};
    private Context context;

    private FrameLayout.LayoutParams layoutParams;
    private RelativeLayout.LayoutParams mainParams;

    private FrameLayout framePhoto;
    private FrameLayout frameRoundWave;

    private int width = 0;
    private float waveHeight;

    private RoundType roundType = RoundType.normal;

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainWaveView);
            try {
                waveHeight = typedArray.getDimension(R.styleable.MainWaveView_waveH, 30);
                roundType = RoundType.getFromInt(typedArray.getInt(R.styleable.MainWaveView_roundWaveType, roundType.value));
                post(new Runnable() {
                    @Override
                    public void run() {
                        width = getWidth() * 2/3;
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

    public void init(int width) {
        mainParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        HorizontalWaveView horizontalWaveView = new HorizontalWaveView(context);
        horizontalWaveView.setLayoutParams(mainParams);
        horizontalWaveView.initDefaultView(R.color.ware, waveHeight);
        horizontalWaveView.startAnimation();
        mainParams.leftMargin = -20;
        mainParams.rightMargin = -30;

        width = mainParams.width;

        frameRoundWave = new FrameLayout(context);
        framePhoto = new FrameLayout(context);
        framePhoto.setLayoutParams(mainParams);
        layoutParams = new FrameLayout.LayoutParams(width, width);
        layoutParams.gravity = Gravity.CENTER;

        addRounds(context, layoutParams);
        addView(horizontalWaveView);
        addView(framePhoto);

        // animations scale
        ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                framePhoto,
                PropertyValuesHolder.ofFloat("scaleX", 1.1f),
                PropertyValuesHolder.ofFloat("scaleY", 1.1f));
        scaleDown.setDuration(4000);
        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);
        scaleDown.start();
    }

    void addRounds(Context context, FrameLayout.LayoutParams layoutParams) {
        framePhoto.removeAllViews();

        SelectableRoundedImageView imageView = new SelectableRoundedImageView(context);
        imageView.setOval(true);
        imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bg_white));
        frameRoundWave.setLayoutParams(layoutParams);
        final FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(width * 2 / 3, width * 2 / 3);
        layoutParams1.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams1);
        frameRoundWave.addView(imageView);

        NativeRoundWaveView nativeRoundWaveView1 = new NativeRoundWaveView(context);
        NativeRoundWaveView nativeRoundWaveView2 = new NativeRoundWaveView(context);
        nativeRoundWaveView1.setLayoutParams(layoutParams);
        nativeRoundWaveView2.setLayoutParams(layoutParams);

        switch (roundType) {
            case normal:
                nativeRoundWaveView1.setColor(new int[]{R.color.normal_1_1,R.color.normal_1_2});
                nativeRoundWaveView2.setColor(new int[]{R.color.normal_2_1,R.color.normal_2_2});
                nativeRoundWaveView1.setPointCount(countWave[0]);
                nativeRoundWaveView2.setPointCount(countWave[0]);
                break;
            case positive:
                nativeRoundWaveView1.setColor(new int[]{R.color.positive_1_1,R.color.positive_1_2});
                nativeRoundWaveView2.setColor(new int[]{R.color.positive_2_1,R.color.positive_2_2});
                nativeRoundWaveView1.setPointCount(countWave[1]);
                nativeRoundWaveView2.setPointCount(countWave[1]);
                break;
            case negative:
                nativeRoundWaveView1.setColor(new int[]{R.color.negative_1_1,R.color.negative_1_2});
                nativeRoundWaveView2.setColor(new int[]{R.color.negative_2_1,R.color.negative_2_2});
                nativeRoundWaveView1.setPointCount(countWave[2]);
                nativeRoundWaveView2.setPointCount(countWave[2]);
                break;
        }

        framePhoto.addView(nativeRoundWaveView1);
        framePhoto.addView(nativeRoundWaveView2);
        framePhoto.addView(frameRoundWave);
    }
}
