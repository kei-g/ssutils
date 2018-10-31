package com.snowstep115.ssutils.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CommandGetNBT extends CommandBase {
    @Override
    public String getName() {
        return "getnbt";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/getnbt";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.isEmpty()) {
                return;
            }
            if (!stack.hasTagCompound()) {
                return;
            }
            NBTTagCompound compound = stack.getTagCompound();
            player.sendStatusMessage(new TextComponentString(compound.toString()), false);
        }
    }
}
