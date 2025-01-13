package com.gtnewhorizon.structurelib.alignment.constructable;

import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.util.Vec3Impl;

public interface IDeconstructable {
    /**
     *
     * @return the active structure definition in use
     */
    IStructureDefinition<?> getActiveStructure();

    /**
     *
     * @return the orientation of the controller
     */
    ExtendedFacing getExtendedFacing();

    /**
     *
     * @return the structure offset (A, B, C) used to calculate the position to start scanning at
     */
    Vec3Impl getStructureOffset();
}