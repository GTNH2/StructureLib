package com.gtnewhorizon.structurelib.compat.carp;


import com.carpentersblocks.tileentity.TEBase;
import cpw.mods.fml.common.Optional;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.minecraft.block.Block;
import com.carpentersblocks.block.BlockCoverable;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;


public class CarpInteropImpl extends CarpInterop {

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static final class InternalCarpData {

        private final Block block;
        private final int metadata;
        private final int cbMetadata;
        private final Item item;
        private final int itemMeta;

    };

    @Override
    @Optional.Method(modid = MOD_ID)
    public boolean isACarpentersBlock(final Block block) {
        return block instanceof BlockCoverable;
    }

    @Override
    @Optional.Method(modid = MOD_ID)
    public java.util.Optional<Integer> getCarpentersHash(final Block block, final int metadata, final TileEntity tileEntity) {
        if (tileEntity instanceof TEBase) {
            val te = (TEBase) tileEntity;
            val carpMeta = te.getData();
            val coverStack = te.getAttribute((byte) 6);
            if (coverStack != null) {
                val item = coverStack.getItem();
                return java.util.Optional.of(new InternalCarpData(block, metadata, carpMeta, item, coverStack.getItemDamage()).hashCode());
            }
        }
        return super.getCarpentersHash(block, metadata, tileEntity);
    }

}
