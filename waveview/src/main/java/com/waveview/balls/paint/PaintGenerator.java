package com.waveview.balls.paint;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

public interface PaintGenerator {
  Paint generate(Context context);
}
