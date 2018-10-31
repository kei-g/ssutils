package com.snowstep115.ssutils.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandSetNBT extends CommandBase {
    @Override
    public String getName() {
        return "setnbt";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/setnbt NBT";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            if (args.length < 1) {
                return;
            }
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.isEmpty()) {
                return;
            }
            try {
                NBTTagCompound compound = JsonToNBT.getTagFromJson(args[0]);
                stack.setTagCompound(compound);
            } catch (NBTException exception) {
                player.sendStatusMessage(new TextComponentString(exception.getMessage()), false);
            }
        }
    }
}
