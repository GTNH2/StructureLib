package com.gtnewhorizon.structurelib.alignment.enumerable;

import static java.lang.Math.abs;

public enum SkewMode {
    ABC(5, 0, 1, 2),
    ACB(3, 0, 2, 1),
    BAC(4, 1, 0, 2),
    BCA(1, 1, 2, 0),
    CAB(2, 2, 0, 1),
    CBA(0, 2, 1, 0),
    ;

    private final int opposite;

    private final int first, second, third;
    public static final SkewMode[] VALUES = values();

    SkewMode(int opposite, int first, int second, int third) {
        this.opposite=opposite;
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static SkewMode byIndex(int index) {
        return VALUES[abs(index % VALUES.length)];
    }

    public int getIndex(){
        return ordinal();
    }

    public SkewMode getOpposite(){
        return VALUES[opposite];
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }
}
