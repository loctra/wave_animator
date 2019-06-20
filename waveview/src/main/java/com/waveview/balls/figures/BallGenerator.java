package com.waveview.balls.figures;

import android.graphics.Paint;
import android.graphics.PointF;

import java.util.Random;

public class BallGenerator implements Generator {

  private float size = 20;
  private float fromSize = size;
  private float toSize = size;
  private Random random = new Random();

  @Override
  public BaseBall generate(PointF startPoint, Paint paint) {
    return new Ball(startPoint, paint, (int) getSizeInRange(fromSize, toSize));
  }

  private float getSizeInRange(float from ,float to){
    return random.nextFloat() * (from - to) + to;
  }
}
