package com.waveview;

public enum RoundType {
    normal(0),
    positive(1),
    negative(2);

    public int value;

    RoundType(int value) {
        this.value = value;
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
