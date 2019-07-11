package com.waveview.balls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import com.waveview.balls.figures.Ball;
import com.waveview.balls.figures.BallGenerator;
import com.waveview.balls.paint.ArrayColorGenerator;
import com.waveview.waveview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BallsView extends View {
    public static final int MAX_BALLS = 4;
    public static final int REPEAT_TIME  = 10000;
    private float width = 0;
    private float height = 0;
    private List<Ball> balls = new ArrayList<>();

    private ObjectAnimator animator;
    private float amplitude;

    public BallsView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context, null);
    }

    public BallsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    public BallsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HorizontalWaveView);
        post(new Runnable() {
            @Override
            public void run() {
                width = getWidth();
                height = getHeight();
                initBalls();
                initAnimation();
            }
        });
        a.recycle();
    }

    private void initAnimation() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(this, "amplitude", 1f);
            animator.setDuration(REPEAT_TIME);
            animator.setRepeatCount(ObjectAnimator.INFINITE);
        }
        if (!animator.isRunning()) {
            animator.start();
        }

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                initBalls();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < balls.size() - 1; i++) {
            drawBalls(canvas, balls.get(i), i);
        }
    }

    public void drawBalls(Canvas canvas, Ball ball, int position) {
        switch (ball.getRoute()) {
            case 1:
                ball.getDrawPoint().y -= position - 1;
                ball.getDrawPoint().x += 3;
                break;
            case 2:
                ball.getDrawPoint().y -= position +1;
                ball.getDrawPoint().x -= 2;
                break;
            case 3:
                ball.getDrawPoint().y += 3;
                ball.getDrawPoint().x -= position-2;
                break;
            case 4:
                ball.getDrawPoint().y += position + 2;
                ball.getDrawPoint().x += position - 3;
                break;
            case 5:
                ball.getDrawPoint().y -= 1;
                ball.getDrawPoint().x += 2;
                break;
            case 6:
                ball.getDrawPoint().y -= 2;
                ball.getDrawPoint().x -= 1;
                break;
            case 7:
                ball.getDrawPoint().y += 3;
                ball.getDrawPoint().x -= 2;
                break;
            case 8:
                ball.getDrawPoint().y += 2;
                ball.getDrawPoint().x += 1;
                break;
        }
        ball.draw(canvas);
    }

    public void stopAnimation() {
        if (animator != null) {
            animator.removeAllListeners();
            animator.end();
            animator.cancel();
        }
    }

    private void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
        invalidate();
    }

    private float getAmplitude() {
        return this.amplitude;
    }

    private void initBalls() {
        balls.clear();
        int count = getSizeInRange(4, MAX_BALLS);
        for (int i = 0; i < count; i++) {
            PointF pointF = new PointF(width / 2, height / 2);
            balls.add(generateBallFrom(pointF));
        }
    }

    private Ball generateBallFrom(PointF point) {
        BallGenerator ballGenerator = new BallGenerator();
        Ball ball = (Ball) ballGenerator.generate(point, new ArrayColorGenerator().generate(getContext()));
        int x = getSizeInRange(1, 8);
        ball.setRoute(x);
        if (width < 500) {
            ball.setRadius(getSizeInRange(2, 5));
        } else {
            ball.setRadius(getSizeInRange(5, 10));
        }
        return ball;
    }

    private int getSizeInRange(int from, int to) {
        Random rn = new Random();
        return rn.nextInt(to) + from;
    }
}