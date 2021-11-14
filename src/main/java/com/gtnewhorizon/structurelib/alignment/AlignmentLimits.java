package com.gtnewhorizon.structurelib.alignment;

import com.gtnewhorizon.structurelib.alignment.enumerable.Flip;
import com.gtnewhorizon.structurelib.alignment.enumerable.Rotation;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.function.Predicate;

class AlignmentLimits implements IAlignmentLimits {

    protected final boolean[] validStates;
    protected final Predicate<Skew> predicate;

    AlignmentLimits(boolean[] validStates, Predicate<Skew> predicate) {
        this.validStates = validStates;
        this.predicate = predicate;
    }

    @Override
    public boolean isNewExtendedFacingValid(ForgeDirection direction, Rotation rotation, Flip flip) {
        return validStates[IAlignment.getAlignmentIndex(direction, rotation, flip)];
    }

    @Override
    public boolean isNewSkewValid(Skew skew) {
        return predicate.test(skew);
    }
}
