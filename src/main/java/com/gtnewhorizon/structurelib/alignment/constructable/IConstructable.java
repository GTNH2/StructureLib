package com.gtnewhorizon.structurelib.alignment.constructable;

import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.util.Vec3Impl;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

/**
 * Created by Tec on 24.03.2017.
 */
public interface IConstructable {
    void construct(ItemStack stackSize, boolean hintsOnly);

    @SideOnly(Side.CLIENT)
    String[] getStructureDescription(ItemStack stackSize);

    /**
     *
     * @return the active structure definition in use
     */
    default IStructureDefinition<?> getActiveStructure() {
        return null;
    }

    /**
     *
     * @return the orientation of the controller
     */
    default ExtendedFacing getExtendedFacing() {
        return null;
    }

    /**
     *
     * @return the structure offset (A, B, C) used to calculate the position to start scanning at
     */
    default Vec3Impl getStructureOffset() {
        return null;
    }
}

