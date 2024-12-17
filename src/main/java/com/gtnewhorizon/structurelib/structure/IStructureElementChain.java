package com.gtnewhorizon.structurelib.structure;

import com.gtnewhorizon.structurelib.structure.adders.BlockInfo;
import lombok.val;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Use StructureUtility to instantiate
 */
public interface IStructureElementChain<T> extends IStructureElement<T> {
	IStructureElement<T>[] fallbacks();

	@Override
	default boolean check(T t, World world, int x, int y, int z) {
		for (IStructureElement<T> fallback : fallbacks()) {
			if (fallback.check(t, world, x, y, z)) {
				return true;
			}
		}
		return false;
	}

	@Override
	default boolean spawnHint(T t, World world, int x, int y, int z, ItemStack trigger) {
		for (IStructureElement<T> fallback : fallbacks()) {
			if (fallback.spawnHint(t, world, x, y, z, trigger)) {
				return true;
			}
		}
		return false;
	}

	@Override
	default boolean placeBlock(T t, World world, int x, int y, int z, ItemStack trigger) {
		for (IStructureElement<T> fallback : fallbacks()) {
			if (fallback.placeBlock(t, world, x, y, z, trigger)) {
				return true;
			}
		}
		return false;
	}

	@Override
	default BlockInfo[] getBlockInfo(T t, World world, int x, int y, int z) {
		ArrayList<BlockInfo> list = new ArrayList<>();
		for (val element : fallbacks()) {
            list.addAll(Arrays.asList(element.getBlockInfo(t, world, x, y, z)));
		}
		return list.toArray(new BlockInfo[0]);
	}
}
