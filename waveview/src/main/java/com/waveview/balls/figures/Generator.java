package com.waveview.balls.figures;

import android.graphics.Paint;
import android.graphics.PointF;

public interface Generator {
  BaseBall generate(PointF startPoint, Paint paint);
}
