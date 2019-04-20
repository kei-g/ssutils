package com.snowstep115.ssutils.command;

import java.util.HashMap;
import java.util.function.Consumer;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CommandWalkBiome extends CommandBase {
    @Override
    public String getName() {
        return "walkbiome";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/walkbiome radius";
    }

    private void walk(int i, BlockPos pos, Consumer<BlockPos> consumer) {
        for (int j = -i; j <= i; j++) {
            consumer.accept(pos.add(j, 0, -i));
            consumer.accept(pos.add(i, 0, j));
            consumer.accept(pos.add(-j, 0, i));
            consumer.accept(pos.add(-i, 0, -j));
        }
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            return;
        }
        int radius = parseInt(args[0]);
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            BlockPos basePos = player.getPosition();
            World world = server.getWorld(0);
            HashMap<String, BlockPos> map = new HashMap<String, BlockPos>();
            for (int i = 1; i <= radius; i++) {
                walk(i, basePos, (pos) -> {
                    Biome biome = world.getBiome(pos);
                    String name = biome.getBiomeName();
                    if (map.containsKey(name)) {
                        BlockPos p = map.get(name);
                        if (basePos.distanceSq(pos) < basePos.distanceSq(p)) {
                            map.put(name, pos);
                        }
                    } else {
                        map.put(name, pos);
                    }
                });
            }
            for (String name : map.keySet()) {
                BlockPos pos = map.get(name);
                String text = String.format("%d, %d => %s", pos.getX(), pos.getZ(), name);
                player.sendStatusMessage(new TextComponentString(text), false);
            }
        }
    }
}
