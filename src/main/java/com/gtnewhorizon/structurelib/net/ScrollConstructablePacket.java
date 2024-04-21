package com.gtnewhorizon.structurelib.net;

import com.gtnewhorizon.structurelib.item.ItemConstructableTrigger;
import com.gtnewhorizon.structurelib.item.ItemDebugStructureWriter;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class ScrollConstructablePacket implements IMessage, IMessageHandler<ScrollConstructablePacket, IMessage> {
    public ScrollConstructablePacket() {}

    byte metaData;

    public ScrollConstructablePacket(byte metaData) {
        this.metaData = metaData;
    }


    @Override
    public void fromBytes(ByteBuf buf) {
        metaData = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(metaData);
    }

    @Override
    public IMessage onMessage(ScrollConstructablePacket message, MessageContext ctx) {
        ItemStack itemStack = ctx.getServerHandler().playerEntity.inventory.getCurrentItem();
        if (itemStack == null || !(itemStack.getItem() instanceof ItemConstructableTrigger)) {
            return null;
        }
        if (message.metaData < 0 || message.metaData > 1) return null;
        itemStack.setItemDamage(message.metaData);
        return null;
    }
}
