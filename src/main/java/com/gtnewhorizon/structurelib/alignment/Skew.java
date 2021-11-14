package com.gtnewhorizon.structurelib.alignment;

import com.gtnewhorizon.structurelib.alignment.enumerable.SkewMode;
import com.gtnewhorizon.structurelib.util.Vec3Impl;

import java.util.Arrays;
import java.util.HashSet;

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
    public static final Skew NONE=new Skew(SkewMode.ABC,new float[LEN]);

    private final float[] skews;
    private final SkewMode mode;
    private final boolean isFirst,isSecond,isThird,isNone;

    private Skew(SkewMode skewMode,float... skews) {
        this.skews=skews;
        this.mode=skewMode;
        isFirst=skews[0]!=0 || skews[1]!=0;
        isSecond=skews[2]!=0 || skews[3]!=0;
        isThird=skews[4]!=0 || skews[5]!=0;
        isNone=!(isFirst||isSecond||isThird);
    }

    public static Skew getSkew(SkewMode mode,float... skews){
        if(skews==null){
            return NONE;
        }
        if(skews.length!=LEN){
            throw new IllegalArgumentException("Bad skew m8!");
        }
        return new Skew(mode,skews);
    }

    public Skew withAdditionalSkew(double[] skews){
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
        return getSkew(mode,skewsArr);
    }

    public Skew withSkewMode(SkewMode mode){
        return new Skew(mode,skews);
    }

    /**
     * Do not modify what you get or else I will be mad.
     * @return the internal state.
     */
    public float[] getSkewsUnsafe() {
        return skews;
    }

    public float[] getSkews() {
        float[] copiedArray = new float[LEN];
        System.arraycopy(skews, 0, copiedArray, 0, LEN);
        return copiedArray;
    }

    public SkewMode getMode() {
        return mode;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isSecond() {
        return isSecond;
    }

    public boolean isThird() {
        return isThird;
    }

    public boolean isNone() {
        return isNone;
    }

    public int getFirst() {
        return mode.getFirst();
    }

    public int getSecond() {
        return mode.getSecond();
    }

    public int getThird() {
        return mode.getThird();
    }

    public void skewCoordinates(int[] arr) {
        if(isFirst()){
            skewAxis(arr,getFirst());
        }
        if(isSecond()){
            skewAxis(arr,getSecond());
        }
        if(isThird()){
            skewAxis(arr,getThird());
        }
    }

    public void skewCoordinates(int[] in, int[] out) {
        System.arraycopy(in,0,out,0,in.length);

        if(isFirst()){
            skewAxis(out,getFirst());
        }
        if(isSecond()){
            skewAxis(out,getSecond());
        }
        if(isThird()){
            skewAxis(out,getThird());
        }
    }

    public void skewAxis(int[] in, int[] out, int axis){
        switch (axis){
            case 0:skewA(in,out);return;
            case 1:skewB(in,out);return;
            case 2:skewC(in,out);return;
            default: throw new RuntimeException("Invalid axis m8!");
        }
    }

    public void skewAxis(int[] arr, int axis){
        switch (axis){
            case 0:skewA(arr);return;
            case 1:skewB(arr);return;
            case 2:skewC(arr);return;
            default: throw new RuntimeException("Invalid axis m8!");
        }
    }

    public void skewA(int[] in, int[] out){
        out[0] = in[0] + (int) Math.floor(in[1] * skews[0] + 0.5) + (int) Math.floor(in[2] * skews[1] + 0.5);
        out[1] = in[1];
        out[2] = in[2];
    }

    public void skewB(int[] in, int[] out){
        out[0] = in[0];
        out[1] = (int) Math.floor(in[0] * skews[2] + 0.5) + in[1] + (int) Math.floor(in[2] * skews[3] + 0.5);
        out[2] = in[2];
    }

    public void skewC(int[] in, int[] out){
        out[0] = in[0];
        out[1] = in[1];
        out[2] = (int) Math.floor(in[0] * skews[4] + 0.5) + (int) Math.floor(in[1] * skews[5] + 0.5) + in[2];
    }

    public void skewA(int[] arr){
        arr[0] = arr[0] + (int) Math.floor(arr[1] * skews[0] + 0.5) + (int) Math.floor(arr[2] * skews[1] + 0.5);
    }

    public void skewB(int[] arr){
        arr[1] = (int) Math.floor(arr[0] * skews[2] + 0.5) + arr[1] + (int) Math.floor(arr[2] * skews[3] + 0.5);
    }

    public void skewC(int[] arr){
        arr[2] = (int) Math.floor(arr[0] * skews[4] + 0.5) + (int) Math.floor(arr[1] * skews[5] + 0.5) + arr[2];
    }

    static {
        //validate();
    }

    private static void validate() {
        HashSet<Vec3Impl> set  = new HashSet<>();
        int[]             abc  = new int[3];
        int[]             d    = new int[3];
        long              iter = 0;
        for (float i = -2; i <= 2; i += 0.5f)
            for (float j = -2; j <= 2; j += 0.5f)
                for (float k = -2; k <= 2; k += 0.5f)
                    for (float l = -2; l <= 2; l += 0.5f)
                        for (float m = -2; m <= 2; m += 0.5f)
                            for (float n = -2; n <= 2; n += 0.5f)
                                for (SkewMode mode : SkewMode.values()) {
                                    Skew skew = new Skew(mode,i, j, k, l, m, n);

                                    set.clear();
                                    for (int a = -5; a <= 5; a++) {
                                        abc[0] = a;
                                        for (int b = -5; b <= 5; b++) {
                                            abc[1] = b;
                                            for (int c = -5; c <= 5; c++) {
                                                abc[2] = c;
                                                skew.skewCoordinates(abc, d);
                                                set.add(new Vec3Impl(d[0], d[1], d[2]));
                                            }
                                        }
                                    }

                                    if (set.size() != 1331) {
                                        System.out.println(iter + " " + skew);
                                    }

                                    iter++;
                                }

    }

    public int[] toArrayOfKindaInts(){
        int[] ints=new int[LEN+1];
        for (int i = 0; i < LEN; i++) {
            ints[i]=Float.floatToRawIntBits(skews[i]);
        }
        ints[LEN]= mode.ordinal();
        return ints;
    }

    public static Skew fromArrayOfKindsInts(int[] mSkew) {
        float[] skews=new float[LEN];
        for (int i = 0; i < LEN; i++) {
            skews[i]=Float.intBitsToFloat(mSkew[i]);
        }
        return new Skew(SkewMode.byIndex(mSkew[LEN]),skews);
    }

    @Override
    public String toString() {
        return "Skew{" +
                "skews=" + Arrays.toString(skews) +
                ", mode=" + mode +
                '}';
    }
}
