package com.gtnewhorizon.structurelib.item;

import com.gtnewhorizon.structurelib.StructureLib;
import com.gtnewhorizon.structurelib.alignment.AlignmentUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

import static com.gtnewhorizon.structurelib.StructureLibAPI.MOD_ID;
import static net.minecraft.util.StatCollector.translateToLocal;

public class ItemSkewTool extends Item {
    public ItemSkewTool() {
        setMaxStackSize(1);
        setUnlocalizedName("structurelib.skewTool");
        setTextureName(MOD_ID + ":itemSkewTool");
        setCreativeTab(StructureLib.creativeTab);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return AlignmentUtility.handleSkew(player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List aList, boolean p_77624_4_) {
        aList.add(translateToLocal("item.structurelib.skewTool.desc.0"));//Triggers Front Rotation Interface
        aList.add(EnumChatFormatting.BLUE + translateToLocal("item.structurelib.skewTool.desc.1"));//Rotates only the front panel,
        aList.add(EnumChatFormatting.BLUE + translateToLocal("item.structurelib.skewTool.desc.2"));//which allows structure rotation.
    }
}