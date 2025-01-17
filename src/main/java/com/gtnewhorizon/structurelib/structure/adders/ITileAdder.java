package com.gtnewhorizon.structurelib.structure.adders;

import net.minecraft.tileentity.TileEntity;

public interface ITileAdder<T> {
	/**
	 * Callback to add hatch, needs to check if tile is valid (and add it)
	 *
	 * @param tileEntity tile
	 * @return managed to add hatch (structure still valid)
	 */
	boolean apply(T t, TileEntity tileEntity);

	default String[] getName(T t) {
		return new String[]{"UNKNOWN_TILE"};
	}
}
