package com.gtnewhorizon.structurelib.util;

import com.gtnewhorizon.structurelib.StructureLib;
import com.gtnewhorizon.structurelib.item.ItemConstructableTrigger;
import com.gtnewhorizon.structurelib.item.ItemDebugStructureWriter;
import com.gtnewhorizon.structurelib.item.ItemDebugStructureWriter.Mode;
import com.gtnewhorizon.structurelib.net.ScrollConstructablePacket;
import com.gtnewhorizon.structurelib.net.UpdateDebugWriterModePacket;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;

public class EventHandler {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        if (event.button < 0) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;

            if (player != null && player.isSneaking()) {
                ItemStack itemStack = player.getHeldItem();

                if (itemStack != null && itemStack.getItem() instanceof ItemDebugStructureWriter) {
                    if (event.dwheel != 0) {
                        int value = ItemDebugStructureWriter.readModeFromNBT(itemStack).ordinal() + Math.max(-1, Math.min(event.dwheel, 1)) * -1;

                        if (value > ItemDebugStructureWriter.Mode.values().length - 1)
                            value = 0;
                        if (value < 0)
                            value = ItemDebugStructureWriter.Mode.values().length - 1;

                        Mode mode = ItemDebugStructureWriter.Mode.values()[value];
                        ItemDebugStructureWriter.writeModeToNBT(itemStack, mode);

                        StructureLib.net.sendToServer(new UpdateDebugWriterModePacket(itemStack));

                        event.setCanceled(true);
                    }
                } else if (itemStack != null && itemStack.getItem() instanceof ItemConstructableTrigger) {
                    if (event.dwheel != 0) {
                        if (itemStack.getItemDamage() == 0) {
                            itemStack.setItemDamage(1);
                            StructureLib.net.sendToServer(new ScrollConstructablePacket((byte) 1));
                        } else {
                            itemStack.setItemDamage(0);
                            StructureLib.net.sendToServer(new ScrollConstructablePacket((byte) 0));
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
