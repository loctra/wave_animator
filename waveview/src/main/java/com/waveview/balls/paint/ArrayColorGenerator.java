package com.waveview.balls.paint;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.waveview.waveview.R;

import java.util.Random;

public class ArrayColorGenerator implements PaintGenerator {
  private int[] colors = {R.color.red, R.color.blue, R.color.yellow,R.color.green,R.color.violet};

  @Override
  public Paint generate(Context context) {
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(ContextCompat.getColor(context, colors[new Random().nextInt(5)]));
    return paint;
  }
}
