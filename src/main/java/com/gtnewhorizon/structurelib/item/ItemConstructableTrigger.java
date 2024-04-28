package com.gtnewhorizon.structurelib.item;

import com.gtnewhorizon.structurelib.StructureLib;
import com.gtnewhorizon.structurelib.alignment.constructable.ConstructableUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

import static com.gtnewhorizon.structurelib.StructureLibAPI.MOD_ID;
import static net.minecraft.util.EnumChatFormatting.BLUE;
import static net.minecraft.util.StatCollector.translateToLocal;

public class ItemConstructableTrigger extends Item {

    @SideOnly(Side.CLIENT)
    private IIcon debugModeIcon;

    public ItemConstructableTrigger() {
        setUnlocalizedName("structurelib.constructableTrigger");
        setTextureName(MOD_ID + ":itemConstructableTrigger");
        setHasSubtypes(true);
        setCreativeTab(StructureLib.creativeTab);
    }

    public enum Mode {
        Default, Debug
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == 0) return super.getItemStackDisplayName(itemStack);
        return String.format("%s (%s)", super.getItemStackDisplayName(itemStack),
                StatCollector.translateToLocal("item.structurelib.constructableTrigger.debug"));
    }


    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return ConstructableUtility.handle(stack, player, world, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.debugModeIcon = iconRegister.registerIcon(MOD_ID + ":itemConstructableTriggerDebug");
    }

    @Override
    public IIcon getIconIndex(ItemStack itemStack) {
        return itemStack.getItemDamage() == 1 ? debugModeIcon : itemIcon;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addInformation(ItemStack aStack, EntityPlayer ep, List aList, boolean boo) {
        aList.add(translateToLocal("item.structurelib.constructableTrigger.desc.0"));//Triggers Constructable Interface
        aList.add(BLUE + translateToLocal("item.structurelib.constructableTrigger.desc.1"));//Shows multiblock construction details,
        aList.add(BLUE + translateToLocal("item.structurelib.constructableTrigger.desc.2"));//just Use on a multiblock controller.
        aList.add(BLUE + translateToLocal("item.structurelib.constructableTrigger.desc.3"));//(Sneak Use in creative to build)
        aList.add(BLUE + translateToLocal("item.structurelib.constructableTrigger.desc.4"));//Quantity affects tier/mode/type
    }
}
