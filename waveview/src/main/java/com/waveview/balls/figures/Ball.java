package com.waveview.balls.figures;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class Ball extends BaseBall {

  private int radius;
  private int route;

  public Ball(PointF startPoint, Paint paint, int radius) {
    super(startPoint, paint);
    this.radius = radius;
  }

  public void draw(Canvas canvas, PointF drawPoint){
    canvas.drawCircle(drawPoint.x, drawPoint.y, radius, paint);
  }

  public void setRadius(int radius) {
    this.radius = radius;
  }

  public int getRadius() {
    return radius;
  }

  public int getRoute() {
    return route;
  }

  public void setRoute(int route) {
    this.route = route;
  }
}
