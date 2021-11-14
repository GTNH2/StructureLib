package com.gtnewhorizon.structurelib.alignment;

import com.gtnewhorizon.structurelib.alignment.enumerable.SkewType;

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
    public static final Skew NONE=new Skew(SkewType.NONE);

    private final float[] skews;
    private final SkewType type;

    private Skew(SkewType isNotApplied) {
        this.type = isNotApplied;
        this.skews = new float[LEN];
    }

    private Skew(float... skews) {
        this.skews=skews;
        this.type=SkewType.get(skews[0]!=0||skews[1]!=0,skews[2]!=0||skews[3]!=0,skews[4]!=0||skews[5]!=0);
    }

    public static Skew getSkew(){
        return NONE;
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

    public SkewType getType() {
        return type;
    }

    public boolean isValid() {
        return getType().isValid();
    }

    public boolean isNeat() {
        return getType().isNeat();
    }

    public boolean isNone(){
        return getType().isNone();
    }

    /**
     * Do not modify what you get or else I will be mad.
     * @return the internal state.
     */
    float[] getSkewsUnsafe() {
        return skews;
    }

    float[] getSkews() {
        float[] copiedArray = new float[LEN];
        System.arraycopy(skews, 0, copiedArray, 0, LEN);
        return copiedArray;
    }

    public void skewCoordinates(int[] in, int[] out) {
        out[0]=in[0]+(int)Math.floor(in[1]*skews[0]+0.5)+(int)Math.floor(in[2]*skews[1]+0.5);
        out[1]=in[1]+(int)Math.floor(in[0]*skews[2]+0.5)+(int)Math.floor(in[2]*skews[3]+0.5);
        out[2]=in[2]+(int)Math.floor(in[0]*skews[4]+0.5)+(int)Math.floor(in[1]*skews[5]+0.5);
    }

    public void skewCoordinates2(int[] in, int[] out) {
        int[] skf=new int[]{
                (int)(in[1]>=0?Math.floor(in[1]*skews[0]+0.5):-(Math.floor(-in[1]*skews[0]+0.5))),
                (int)(in[2]>=0?Math.floor(in[2]*skews[1]+0.5):-(Math.floor(-in[2]*skews[1]+0.5))),
                (int)(in[0]>=0?Math.floor(in[0]*skews[2]+0.5):-(Math.floor(-in[0]*skews[2]+0.5))),
                (int)(in[2]>=0?Math.floor(in[2]*skews[3]+0.5):-(Math.floor(-in[2]*skews[3]+0.5))),
                (int)(in[0]>=0?Math.floor(in[0]*skews[4]+0.5):-(Math.floor(-in[0]*skews[4]+0.5))),
                (int)(in[1]>=0?Math.floor(in[1]*skews[5]+0.5):-(Math.floor(-in[1]*skews[5]+0.5))),
        };
        out[0]=in[0]+skf[0]+skf[1];
        out[1]=in[1]+skf[2]+skf[3];
        out[2]=in[2]+skf[4]+skf[5];
    }

    public int[] asFloatsInIntFormat(){
        int[] ints=new int[LEN];
        for (int i = 0; i < LEN; i++) {
            ints[i]=Float.floatToRawIntBits(skews[i]);
        }
        return ints;
    }
}
