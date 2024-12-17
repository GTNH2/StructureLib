package com.gtnewhorizon.structurelib.structure.adders;

import lombok.AllArgsConstructor;
import lombok.val;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

@AllArgsConstructor
public class BlockInfo {
    public final Block block;
    public final int meta;
    public final TileEntity tileEntity;
    public final String extraData;

    public BlockInfo(Block block) {
        this.block = block;
        meta = -1;
        tileEntity = null;
        extraData = "";
    }

    public BlockInfo(Block block,String extraData) {
        this.block = block;
        meta = -1;
        tileEntity = null;
        this.extraData = extraData;
    }

    public BlockInfo(Block block, int meta) {
        this.block = block;
        this.meta = meta;
        tileEntity = null;
        extraData = "";
    }

    public BlockInfo(Block block, int meta,String extraData) {
        this.block = block;
        this.meta = meta;
        tileEntity = null;
        this.extraData = extraData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlockInfo) {
            val info = (BlockInfo) obj;
            return info.block == this.block && info.meta == this.meta && info.tileEntity == this.tileEntity;
        }
        return false;
    }
}
