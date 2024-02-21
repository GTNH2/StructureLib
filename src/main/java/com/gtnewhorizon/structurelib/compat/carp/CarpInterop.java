package com.gtnewhorizon.structurelib.compat.carp;


import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.Optional;


public abstract class CarpInterop {

    public static final String MOD_ID = "CarpentersBlocks";

    public boolean isACarpentersBlock(final Block block) {
        return false;
    }

    public Optional<Integer> getCarpentersHash(final Block block, final int metadata, final TileEntity tileEntity) {
        return Optional.empty();
    }

}
