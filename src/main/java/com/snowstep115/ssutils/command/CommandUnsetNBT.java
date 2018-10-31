package com.snowstep115.ssutils.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class CommandUnsetNBT extends CommandBase {
    @Override
    public String getName() {
        return "unsetnbt";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/unsetnbt";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.isEmpty()) {
                return;
            }
            stack.setTagCompound(null);
        }
    }
}
