package com.gtnewhorizon.structurelib.structure;

import com.gtnewhorizon.structurelib.StructureLib;
import com.gtnewhorizon.structurelib.alignment.enumerable.ExtendedFacing;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;

import static com.gtnewhorizon.structurelib.StructureLib.DEBUG_MODE;

public interface IStructureDefinition<T> {
	/**
	 * Used internally
	 *
	 * Might, but is not required, to throw {@link java.util.NoSuchElementException} if the given structure piece is not found.
	 *
	 * @param name same name as for other methods here
	 * @return the array of elements to process
	 * @throws java.util.NoSuchElementException if the given structure piece is not found and the moon phase perfectly matches
	 */
	IStructureElement<T>[] getStructureFor(String name);

	default boolean check(T object, String piece, World world, ExtendedFacing extendedFacing,
						  int basePositionX, int basePositionY, int basePositionZ,
						  int basePositionA, int basePositionB, int basePositionC,
						  boolean forceCheckAllBlocks, FailedCallback<T> callBack) {
		return iterate(object, null, getStructureFor(piece), world, extendedFacing, basePositionX, basePositionY, basePositionZ,
				basePositionA, basePositionB, basePositionC, false, forceCheckAllBlocks,callBack);
	}

	default boolean hints(T object, ItemStack trigger, String piece, World world, ExtendedFacing extendedFacing,
						  int basePositionX, int basePositionY, int basePositionZ,
						  int basePositionA, int basePositionB, int basePositionC, FailedCallback<T> callBack) {
		return iterate(object, trigger, getStructureFor(piece), world, extendedFacing, basePositionX, basePositionY, basePositionZ,
				basePositionA, basePositionB, basePositionC, true, null,callBack);
	}

	default boolean build(T object, ItemStack trigger, String piece, World world, ExtendedFacing extendedFacing,
						  int basePositionX, int basePositionY, int basePositionZ,
						  int basePositionA, int basePositionB, int basePositionC, FailedCallback<T> callBack) {
		return iterate(object, trigger, getStructureFor(piece), world, extendedFacing, basePositionX, basePositionY, basePositionZ,
				basePositionA, basePositionB, basePositionC, false, null,callBack);
	}

	default boolean buildOrHints(T object, ItemStack trigger, String piece, World world, ExtendedFacing extendedFacing,
								 int basePositionX, int basePositionY, int basePositionZ,
								 int basePositionA, int basePositionB, int basePositionC,
								 boolean hintsOnly, FailedCallback<T> callBack) {
		return iterate(object, trigger, getStructureFor(piece), world, extendedFacing, basePositionX, basePositionY, basePositionZ,
				basePositionA, basePositionB, basePositionC, hintsOnly, null,callBack);
	}

	static <T> boolean iterate(T object, ItemStack trigger, IStructureElement<T>[] elements, World world, ExtendedFacing extendedFacing,
							   int basePositionX, int basePositionY, int basePositionZ,
							   int basePositionA, int basePositionB, int basePositionC,
							   boolean hintsOnly, Boolean checkBlocksIfNotNullForceCheckAllIfTrue, FailedCallback<T> callBack) {
		if (world.isRemote ^ hintsOnly) {
			return false;
		}

		//change base position to base offset
		basePositionA = -basePositionA;
		basePositionB = -basePositionB;
		basePositionC = -basePositionC;

		int[] abc = new int[]{basePositionA, basePositionB, basePositionC};
		int[] xyz = new int[3];

		if (checkBlocksIfNotNullForceCheckAllIfTrue != null) {
			if (checkBlocksIfNotNullForceCheckAllIfTrue) {
				for (IStructureElement<T> element : elements) {
					if (element.isNavigating()) {
						abc[0] = (element.resetA() ? basePositionA : abc[0]) + element.getStepA();
						abc[1] = (element.resetB() ? basePositionB : abc[1]) + element.getStepB();
						abc[2] = (element.resetC() ? basePositionC : abc[2]) + element.getStepC();
					} else {
						extendedFacing.getWorldOffset(abc, xyz);
						xyz[0] += basePositionX;
						xyz[1] += basePositionY;
						xyz[2] += basePositionZ;

						if (world.blockExists(xyz[0], xyz[1], xyz[2])) {
							if (!element.check(object, world, xyz[0], xyz[1], xyz[2])) {
								if (callBack != null) {
									callBack.onFail(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
								}
								if (DEBUG_MODE) {
									StructureLib.LOGGER.info("Multi [" + basePositionX + ", " + basePositionY + ", " + basePositionZ + "] failed @ " +
											Arrays.toString(xyz) + " " + Arrays.toString(abc));
								}
								if (callBack == null || callBack.stopOnFail())
									return false;
							}
						} else {
							if (callBack != null) {
								callBack.onFail(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
							}
							if (DEBUG_MODE) {
								StructureLib.LOGGER.info("Multi [" + basePositionX + ", " + basePositionY + ", " + basePositionZ + "] !blockExists @ " +
										Arrays.toString(xyz) + " " + Arrays.toString(abc));
							}
							if (callBack == null || callBack.stopOnFail())
								return false;
						}
						abc[0] += 1;
					}
				}
			} else {
				for (IStructureElement<T> element : elements) {
					if (element.isNavigating()) {
						abc[0] = (element.resetA() ? basePositionA : abc[0]) + element.getStepA();
						abc[1] = (element.resetB() ? basePositionB : abc[1]) + element.getStepB();
						abc[2] = (element.resetC() ? basePositionC : abc[2]) + element.getStepC();
					} else {
						extendedFacing.getWorldOffset(abc, xyz);
						xyz[0] += basePositionX;
						xyz[1] += basePositionY;
						xyz[2] += basePositionZ;

						if (world.blockExists(xyz[0], xyz[1], xyz[2])) {
							if (!element.check(object, world, xyz[0], xyz[1], xyz[2])) {
								if (callBack != null) {
									callBack.onFail(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
								}
								if (DEBUG_MODE) {
									StructureLib.LOGGER.info("Multi [" + basePositionX + ", " + basePositionY + ", " + basePositionZ + "] failed @ " +
											Arrays.toString(xyz) + " " + Arrays.toString(abc));
								}
								if (callBack == null || callBack.stopOnFail())
									return false;
							}
						} else {
							if (callBack != null) {
								callBack.onFail(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
							}
							if (DEBUG_MODE) {
								StructureLib.LOGGER.info("Multi [" + basePositionX + ", " + basePositionY + ", " + basePositionZ + "] !blockExists @ " +
										Arrays.toString(xyz) + " " + Arrays.toString(abc));
							}
						}
						abc[0] += 1;
					}
				}
			}
		} else {
			if (hintsOnly) {
				for (IStructureElement<T> element : elements) {
					if (element.isNavigating()) {
						abc[0] = (element.resetA() ? basePositionA : abc[0]) + element.getStepA();
						abc[1] = (element.resetB() ? basePositionB : abc[1]) + element.getStepB();
						abc[2] = (element.resetC() ? basePositionC : abc[2]) + element.getStepC();
					} else {
						extendedFacing.getWorldOffset(abc, xyz);
						xyz[0] += basePositionX;
						xyz[1] += basePositionY;
						xyz[2] += basePositionZ;

						element.spawnHint(object, world, xyz[0], xyz[1], xyz[2], trigger);
						if (callBack != null) {
							callBack.onHintCheck(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
						}

						abc[0] += 1;
					}
				}
			} else {
				for (IStructureElement<T> element : elements) {
					if (element.isNavigating()) {
						abc[0] = (element.resetA() ? basePositionA : abc[0]) + element.getStepA();
						abc[1] = (element.resetB() ? basePositionB : abc[1]) + element.getStepB();
						abc[2] = (element.resetC() ? basePositionC : abc[2]) + element.getStepC();
					} else {
						extendedFacing.getWorldOffset(abc, xyz);
						xyz[0] += basePositionX;
						xyz[1] += basePositionY;
						xyz[2] += basePositionZ;

						if (world.blockExists(xyz[0], xyz[1], xyz[2])) {
							element.placeBlock(object, world, xyz[0], xyz[1], xyz[2], trigger);
						}
						if (callBack != null) {
							callBack.onHintCheck(xyz[0], xyz[1], xyz[2],abc[0],abc[1],abc[2],world,object,element);
						}
						abc[0] += 1;
					}
				}
			}
		}
		return true;
	}

	static <T> StructureDefinition.Builder<T> builder() {
		return StructureDefinition.builder();
	}

	interface FailedCallback<T> {
		void onFail(int x, int y, int z,int a,int b,int c,World world,T object ,IStructureElement<T> ExpectedElement);
		default void onHintCheck(int x, int y, int z, int a, int b, int c, World world, T object , IStructureElement<T> element){}
		default boolean stopOnFail() {
			return true;
		}
	}
}
