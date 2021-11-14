package com.gtnewhorizon.structurelib.alignment;

import com.gtnewhorizon.structurelib.alignment.enumerable.ABCDirection;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.ForgeDirection;

import javax.annotation.Nullable;

import static com.gtnewhorizon.structurelib.alignment.Skew.LEN;
import static com.gtnewhorizon.structurelib.alignment.Skew.NONE;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.ofBlock;

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

    static TA ta=new TA();
    private static class TA extends TileEntity implements IAlignmentProvider{
        public Skew skew=NONE;

        @Nullable
        @Override
        public IAlignment getAlignment() {
            return new IAlignment() {
                @Override
                public ExtendedFacing getExtendedFacing() {
                    return ExtendedFacing.DEFAULT;
                }

                @Override
                public void setExtendedFacing(ExtendedFacing alignment) {

                }

                @Override
                public IAlignmentLimits getAlignmentLimits() {
                    return UNLIMITED_AND_SKEW;
                }

                @Override
                public void setSkew(Skew skew) {
                    TA.this.skew=skew;
                }

                @Override
                public Skew getSkew() {
                    return skew;
                }
            };
        }
    }

    //todo
    public static boolean handleSkew(EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ){
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if(tTileEntity==null){
            tTileEntity=ta;
        }
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
                            skews[0]=abcHit[1];
                            skews[1]=abcHit[2];
                            break;
                        case RIGHT:
                            skews[0]=-abcHit[1];
                            skews[1]=-abcHit[2];
                            break;
                        case DOWN:
                            skews[2]=-abcHit[0];
                            skews[3]=-abcHit[2];
                            break;
                        case UP:
                            skews[2]=abcHit[0];
                            skews[3]=abcHit[2];
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
                            alignment.toolSetSkew(null);
                            return true;
                        default:
                            throw new RuntimeException("I like unreachable code.");
                    }
                    alignment.toolSetSkew(alignment.getSkew().withAdditionalSkew(skews));

                    StructureDefinition.builder().addShape("main",new String[][]{
                            {"01110","11111", "11~11", "11111","01110",},
                            {"11111","11111", "11111", "11111","11111",},
                            {"11111","11111", "11011", "11111","11111",},
                            {"11111","11111", "11111", "11111","11111",},
                            {"01110","11111", "11111", "11111","01110",},
                    }).addElement('1', ofBlock(Blocks.stone, 0)).addElement('0', ofBlock(Blocks.coal_block, 0)).build().hints(
                            null,null,"main", Minecraft.getMinecraft().theWorld,ExtendedFacing.DEFAULT, ta.skew, aX,aY,aZ,2,2,0);
                }
                return true;
            }
        }
        return false;
    }
}
