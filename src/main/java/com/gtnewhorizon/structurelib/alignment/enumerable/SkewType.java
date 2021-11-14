package com.gtnewhorizon.structurelib.alignment.enumerable;

public enum SkewType {
    NONE(false, false, false),
    A(true, false, false),
    B(false, true, true),
    C(false, false, true),//3
    AB(true, true, false),
    BC(false, true, true),
    CA(true, false, true),//6
    ALL(true, true, true),
    ;

    private final boolean a, b, c, valid, neat;

    SkewType(boolean a, boolean b, boolean c) {
        this.a = a;
        this.b = b;
        this.c = c;
        valid = ordinal() != 7;
        neat = ordinal() <= 3;
    }

    public boolean isA() {
        return a;
    }

    public boolean isB() {
        return b;
    }

    public boolean isC() {
        return c;
    }

    //Should not have blocks overlapping at all?
    public boolean isValid() {
        return valid;
    }

    //Should not have empty holes?
    public boolean isNeat() {
        return neat;
    }

    //Nothing is done
    public boolean isNone(){
        return this==NONE;
    }

    public static SkewType get(boolean a, boolean b, boolean c) {
        if (a) {
            if (b) {
                if (c) {
                    return ALL;
                }
                return AB;
            }
            if (c) {
                return CA;
            }
            return A;
        }
        if (b) {
            if (c) {
                return BC;
            }
            return B;
        }
        if (c) {
            return C;
        }
        return NONE;
    }
}
