package com.gtnewhorizon.structurelib.commands;

import com.gtnewhorizon.structurelib.StructureLibAPI;
import com.gtnewhorizon.structurelib.util.Vec3Impl;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;

import java.util.List;

public class CommandPos extends SubCommand {
    public enum Variant {
        pos1, pos2
    }
    public CommandPos(Variant variant) {
        super(variant.name());
        this.variant = variant;
    }

    private final Variant variant;

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0 || args.length == 3) {
            int xOffset = 0;
            int yOffset = 0;
            int zOffset = 0;

            if (args.length == 3) {
                try {
                    xOffset = Integer.parseInt(args[0]);
                    yOffset = Integer.parseInt(args[1]);
                    zOffset = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    throw new WrongUsageException("Optional arguments should be a positional offset represented by three integers.");
                }
            }

            CommandData.corners(this.variant.ordinal(),
                                new Vec3Impl(sender.getPlayerCoordinates().posX + xOffset,
                                             sender.getPlayerCoordinates().posY + yOffset,
                                             sender.getPlayerCoordinates().posZ + zOffset),
                                sender.getEntityWorld());

            StructureLibAPI.hintParticle(sender.getEntityWorld(),
                                         CommandData.corners()[variant.ordinal()].get0(),
                                         CommandData.corners()[variant.ordinal()].get1(),
                                         CommandData.corners()[variant.ordinal()].get2(),
                                         StructureLibAPI.getBlockHint(),
                                         this.variant.ordinal());
        } else if (args.length == 1 && "help".equalsIgnoreCase(args[0])) {
            printHelp(sender, null);
        } else {
            throw new WrongUsageException("Invalid number of arguments. Run \"structurelib " + this.variant.name() + " help\" for more details");
        }
    }

    @Override
    public void printHelp(ICommandSender sender, String subCommand) {
        sender.addChatMessage(new ChatComponentText("Command" + this.variant.name() + " help"));
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }
}