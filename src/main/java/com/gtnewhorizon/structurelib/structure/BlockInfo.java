package com.gtnewhorizon.structurelib.structure;

import lombok.val;
import net.minecraft.block.Block;

public class BlockInfo {
    public final Block block;
    public final int meta;

    public BlockInfo(Block block) {
        this.block = block;
        meta = -1;
    }

    public BlockInfo(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }

    public String getLocalisedNameKey() {
        if (meta == -1) {
            return block.getUnlocalizedName() + ".name";
        } else {
            return block.getUnlocalizedName() + "." + meta+".name";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockInfo) {
            val info = (BlockInfo) obj;
            return info.block == this.block && info.meta == this.meta;
        }
        return false;
    }
}
