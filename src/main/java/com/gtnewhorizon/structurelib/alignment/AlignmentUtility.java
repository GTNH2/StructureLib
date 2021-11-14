package com.gtnewhorizon.structurelib.alignment;

import com.gtnewhorizon.structurelib.alignment.enumerable.ABCDirection;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

import static com.gtnewhorizon.structurelib.alignment.Skew.LEN;
import static com.gtnewhorizon.structurelib.alignment.Skew.NONE;

public class AlignmentUtility {
    private AlignmentUtility(){
    }

    public static boolean handle(EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ){
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null || aPlayer instanceof FakePlayer) {
            return aPlayer instanceof EntityPlayerMP;
        }
        if (aPlayer instanceof EntityPlayerMP && tTileEntity instanceof IAlignmentProvider) {
            IAlignment alignment = ((IAlignmentProvider) tTileEntity).getAlignment();
            if (alignment != null) {
                if (aPlayer.isSneaking()) {
                    alignment.toolSetFlip(null);
                } else {
                    alignment.toolSetRotation(null);
                }
                return true;
            }
        }
        return false;
    }

    //todo
    public static boolean handleSkew(EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ){
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null || aPlayer instanceof FakePlayer) {
            return aPlayer instanceof EntityPlayerMP;
        }
        if (aPlayer instanceof EntityPlayerMP && tTileEntity instanceof IAlignmentProvider) {
            IAlignment alignment = ((IAlignmentProvider) tTileEntity).getAlignment();
            if (alignment != null) {
                if (aPlayer.isSneaking()) {
                    alignment.toolSetSkew(NONE);
                } else {
                    ExtendedFacing extendedFacing = alignment.getExtendedFacing();
                    double[] abcHit=new double[3];
                    extendedFacing.getOffsetABC(new double[]{hitX - .5d, hitY - .5d, hitZ - .5d}, abcHit);
                    double[] skews=new double[LEN];
                    ABCDirection abcDirection = extendedFacing.getWorldInRelative(ForgeDirection.getOrientation(side));
                    switch (abcDirection){
                        case LEFT:
                            skews[0]=-abcHit[1];
                            skews[1]=-abcHit[2];
                            break;
                        case RIGHT:
                            skews[0]=abcHit[1];
                            skews[1]=abcHit[2];
                            break;
                        case DOWN:
                            skews[2]=-abcHit[0];
                            skews[3]=abcHit[2];
                            break;
                        case UP:
                            skews[2]=abcHit[0];
                            skews[3]=-abcHit[2];
                            break;
                        case BACK:
                            skews[4]=-abcHit[0];
                            skews[5]=-abcHit[1];
                            break;
                        case FORWARD:
                            skews[4]=abcHit[0];
                            skews[5]=abcHit[1];
                            break;
                        case UNKNOWN:
                            alignment.toolSetSkew(NONE);
                            return true;
                        default:
                            throw new RuntimeException("I like unreachable code.");
                    }
                    alignment.toolSetSkew(Skew.getSkew().modifySkew(skews));
                }
                return true;
            }
        }
        return false;
    }
}
