package com.gtnewhorizon.structurelib.alignment.enumerable;

import com.gtnewhorizon.structurelib.util.Vec3Impl;
import net.minecraftforge.common.util.ForgeDirection;

public enum ABCDirection {
    LEFT(ForgeDirection.EAST),
    RIGHT(ForgeDirection.WEST),
    DOWN(ForgeDirection.UP),
    UP(ForgeDirection.DOWN),
    BACK(ForgeDirection.SOUTH),
    FORWARD(ForgeDirection.NORTH),
    UNKNOWN(ForgeDirection.UNKNOWN);

    private final ForgeDirection forgeDirection;
    private final Vec3Impl       axisVector;
    public static final ABCDirection[] VALUES=values();
    private static final ABCDirection[] MAPPING=new ABCDirection[]{UP,DOWN,FORWARD,BACK,RIGHT,LEFT,UNKNOWN};

    ABCDirection(ForgeDirection forgeDirection) {
        this.forgeDirection = forgeDirection;
        axisVector=new Vec3Impl(forgeDirection.offsetX,forgeDirection.offsetY,forgeDirection.offsetZ);
    }

    public ForgeDirection getForgeDirection() {
        return forgeDirection;
    }

    public Vec3Impl getAxisVector() {
        return axisVector;
    }

    public static ABCDirection forForgeDirection(ForgeDirection direction){
        if(direction==null){
            return null;
        }
        return MAPPING[direction.ordinal()];
    }

    public ABCDirection getOpposite(){
        return VALUES[ForgeDirection.OPPOSITES[ordinal()]];
    }
}
