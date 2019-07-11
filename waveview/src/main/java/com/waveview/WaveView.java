package com.waveview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;
import com.waveview.balls.BallsView;
import com.waveview.waveview.R;
import pl.droidsonroids.gif.GifImageView;

public class WaveView extends RelativeLayout {

    public static final int MARGIN = -1;

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
    private boolean disableHorizontal;
    private boolean isOval;
    private float waveHeight;
    private int waveCount;
    private HorizontalWaveView horizontalWaveView;
    private BallsView ballsView;

    private RoundType roundType = RoundType.normal;
    private SelectableRoundedImageView imageViewRound;

    private void initAttrs(Context context, AttributeSet attrs) {
        this.context = context;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MainWaveView);
            try {
                waveHeight = typedArray.getDimension(R.styleable.MainWaveView_waveH, 30);
                width = (int)typedArray.getDimension(R.styleable.MainWaveView_waveW, context.getResources().getDimension(R.dimen.d_150));
                waveCount = typedArray.getInt(R.styleable.MainWaveView_waveCount, 20);
                res = typedArray.getResourceId(R.styleable.MainWaveView_waveRes, R.drawable.bg_white);
                disableHorizontal = typedArray.getBoolean(R.styleable.MainWaveView_disableHorizontal,false);
                isOval = typedArray.getBoolean(R.styleable.MainWaveView_isOval,true);
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
        horizontalWaveView = new HorizontalWaveView(context);
        horizontalWaveView.setLayoutParams(mainParams);
        horizontalWaveView.initDefaultView(R.color.ware, waveHeight);
        horizontalWaveView.setWaveNumber(waveCount);
        horizontalWaveView.startAnimation();
        mainParams.leftMargin = MARGIN *width/2;
        mainParams.rightMargin = MARGIN*width/2;
        frameRoundWave = new FrameLayout(context);
        framePhoto = new FrameLayout(context);
        framePhoto.setLayoutParams(mainParams);
        layoutParams = new FrameLayout.LayoutParams(width, width);
        layoutParams.gravity = Gravity.CENTER;

        addRounds(context, layoutParams);
        if (!disableHorizontal) {
            addView(horizontalWaveView);
        }

        ballsView = new BallsView(context);
        addView(ballsView);
        addView(framePhoto);
        invalidate();
    }

    void addRounds(Context context, FrameLayout.LayoutParams layoutParams) {
        framePhoto.removeAllViews();

        imageViewRound = new SelectableRoundedImageView(context);
        imageViewRound.setOval(isOval);
        Glide.with(context).load(res).into(imageViewRound);
        frameRoundWave.setLayoutParams(layoutParams);
        final FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(roundWidthHeight(width), roundWidthHeight(width));
        layoutParams1.gravity = Gravity.CENTER;
        layoutParams1.setMargins(50, 50, 50, 50);
        imageViewRound.setLayoutParams(layoutParams1);
        frameRoundWave.addView(imageViewRound);

        GifImageView roundWaveView = new GifImageView(context);
        roundWaveView.setLayoutParams(layoutParams);
        int res = R.drawable.normal;
        switch (roundType) {
            case normal:
                res = R.drawable.normal;
                break;
            case positive:
                res = R.drawable.positive;
                break;
            case negative:
                res = R.drawable.negative;
                break;
            case all:
                res = R.drawable.all;
                break;
        }

        roundWaveView.setBackgroundResource( res);
        framePhoto.addView(roundWaveView);
        framePhoto.addView(frameRoundWave);
        invalidate();
    }

    private int roundWidthHeight(int length){
        return length * 2/3;
    }

    public void setPhoto(String url){
        if (imageViewRound != null) {
            Glide.with(context).load(url).into(imageViewRound);
            invalidate();
        }
    }

    public void setPhoto(int res){
        if (imageViewRound != null) {
            Glide.with(context).load(res).into(imageViewRound);
            invalidate();
        }
    }

    public void onDestroy(){
        if (horizontalWaveView != null) {
            horizontalWaveView.stopAnimation();
        }
        if (ballsView != null) {
            ballsView.stopAnimation();
        }
    }
}
