package com.waveview;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.joooonho.SelectableRoundedImageView;
import com.waveview.waveview.R;

public class WaveView extends RelativeLayout {

    public static final int MARGIN = -30;

    public WaveView(Context context) {
        super(context);
    }

    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private Context context;

    private FrameLayout.LayoutParams layoutParams;
    private LayoutParams mainParams;

    private FrameLayout framePhoto;
    private FrameLayout frameRoundWave;

    private int width = 0;
    private int res = R.drawable.bg_white;
    private float waveHeight;

    private RoundType roundType = RoundType.normal;
    private SelectableRoundedImageView imageView;

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
                width = (int)typedArray.getDimension(R.styleable.MainWaveView_waveW, context.getResources().getDimension(R.dimen.d_150));
                res = typedArray.getResourceId(R.styleable.MainWaveView_waveRes, R.drawable.bg_white);
                roundType = RoundType.getFromInt(typedArray.getInt(R.styleable.MainWaveView_roundWaveType, roundType.value));
                init();

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (typedArray != null)
                    typedArray.recycle();
            }
        }
    }

    public void init() {
        mainParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mainParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        HorizontalWaveView horizontalWaveView = new HorizontalWaveView(context);
        horizontalWaveView.setLayoutParams(mainParams);
        horizontalWaveView.initDefaultView(R.color.ware, waveHeight);
        horizontalWaveView.startAnimation();
        mainParams.leftMargin = MARGIN;
        mainParams.rightMargin = MARGIN;

        frameRoundWave = new FrameLayout(context);
        framePhoto = new FrameLayout(context);
        framePhoto.setLayoutParams(mainParams);
        layoutParams = new FrameLayout.LayoutParams(width, width);
        layoutParams.gravity = Gravity.CENTER;

        ImageView roundWaveView2 = new ImageView(context);
        roundWaveView2.setLayoutParams(mainParams);
        Glide.with(context).asGif().load(R.drawable.particle_rect).into(roundWaveView2);

        addView(roundWaveView2);
        addRounds(context, layoutParams);
        addView(horizontalWaveView);
        addView(framePhoto);
    }

    void addRounds(Context context, FrameLayout.LayoutParams layoutParams) {
        framePhoto.removeAllViews();

        imageView = new SelectableRoundedImageView(context);
        imageView.setOval(true);
        Glide.with(context).load(res).into(imageView);
        frameRoundWave.setLayoutParams(layoutParams);
        final FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(roundWidthHeight(width), roundWidthHeight(width));
        layoutParams1.gravity = Gravity.CENTER;
        imageView.setLayoutParams(layoutParams1);
        frameRoundWave.addView(imageView);

        ImageView roundWaveView = new ImageView(context);
        roundWaveView.setLayoutParams(layoutParams);

        switch (roundType) {
            case normal:
                Glide.with(context).asGif().load(R.drawable.normal).into(roundWaveView);
                break;
            case positive:
                Glide.with(context).asGif().load(R.drawable.positive).into(roundWaveView);
                break;
            case negative:
                Glide.with(context).asGif().load(R.drawable.negative).into(roundWaveView);
                break;
        }

        framePhoto.addView(roundWaveView);
        framePhoto.addView(frameRoundWave);
    }

    private int roundWidthHeight(int length){
        return length *2/3;
    }

    public void setPhoto(String url){
        if (imageView != null) {
            Glide.with(context).load(url).into(imageView);
            invalidate();
        }
    }

    public void setPhoto(int res){
        if (imageView != null) {
            Glide.with(context).load(res).into(imageView);
            invalidate();
        }
    }
}
