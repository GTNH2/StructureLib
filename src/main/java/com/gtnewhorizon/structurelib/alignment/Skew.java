package com.gtnewhorizon.structurelib.alignment;

/**
 * Follows this matrix mixing:
 * A B
 * A C
 * B A
 * B C
 * C A
 * C B
 */
public class Skew {
    public static final int LEN=6;
    public static final Skew NONE=new Skew(true);

    private final float[] skews;
    private final boolean isNotApplied;

    private Skew(boolean isNotApplied) {
        this.isNotApplied = isNotApplied;
        this.skews = new float[LEN];
    }

    private Skew(float... skews) {
        this.isNotApplied=false;
        this.skews=skews;
    }

    public static Skew getSkew(float... skews){
        if(skews==null){
            return NONE;
        }
        if(skews.length!=LEN){
            throw new IllegalArgumentException("Bad skew m8!");
        }
        for (float f : skews) {
            if(f!=0){
                return new Skew(skews);
            }
        }
        return NONE;
    }

    public static Skew getSkewUnsafe(float[] skews){
        return new Skew(skews);
    }

    public Skew modifySkew(Skew skew){
        if(skew==null || skew==this){
            return this;
        }
        float[] skewsArr=new float[LEN];
        for (int i = 0; i < LEN; i++) {
            skewsArr[i]=this.skews[i]+skew.skews[i];
        }
        return getSkew(skewsArr);
    }

    public Skew modifySkew(double[] skews){
        if(skews==null){
            return this;
        }
        if(skews.length!=LEN){
            throw new IllegalArgumentException("Bad skew m8!");
        }
        float[] skewsArr=new float[LEN];
        for (int i = 0; i < LEN; i++) {
            skewsArr[i]=this.skews[i]+(float) skews[i];
        }
        return getSkew(skewsArr);
    }

    public boolean isNotApplied(){
        return isNotApplied;
    }

    public void skewCoordinates(int[] in, int[] out) {
        out[0]=in[0]+(int)Math.floor(in[1]*skews[0])+(int)Math.floor(in[2]*skews[1]);
        out[1]=in[1]+(int)Math.floor(in[0]*skews[2])+(int)Math.floor(in[2]*skews[3]);
        out[2]=in[2]+(int)Math.floor(in[0]*skews[4])+(int)Math.floor(in[1]*skews[5]);
    }

    public int[] asFloatsInIntFormat(){
        int[] ints=new int[LEN];
        for (int i = 0; i < LEN; i++) {
            ints[i]=Float.floatToRawIntBits(skews[i]);
        }
        return ints;
    }
}
