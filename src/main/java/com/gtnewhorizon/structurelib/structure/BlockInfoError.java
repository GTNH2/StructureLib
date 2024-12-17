package com.gtnewhorizon.structurelib.structure;

import net.minecraft.block.Block;

public class BlockInfoError extends BlockInfo {
    String error;
    public BlockInfoError(String error) {
        super(null,-1);
        this.error = error;
    }

    @Override
    public String getLocalisedNameKey() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockInfoError)) return false;
        return this.error.equals(((BlockInfoError) obj).error);
    }
}
