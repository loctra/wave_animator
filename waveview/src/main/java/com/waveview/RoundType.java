package com.waveview;

import com.waveview.waveview.R;

public enum RoundType {
    normal(0, R.drawable.normal),
    positive(1, R.drawable.positive),
    negative(2,R.drawable.negative),
    all(3, R.drawable.all);

    public int value;
    public int res;

    RoundType(int value, int res) {
        this.value = value;
        this.res = res;
    }

    public static RoundType getFromInt(int value) {
        for (RoundType direction : values()) {
            if (direction.value == value) {
                return direction;
            }
        }
        return normal;
    }
}
