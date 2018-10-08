package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import java.util.LinkedList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;

@Mod.EventBusSubscriber
public class ChunkDestroyer extends ItemBase {
    private static final int FORTUNE_LEVEL = 3;

    private interface IInstance {
        void run(World world);
    }

    private interface IFinalizer {
        void run(IInstance instance);
    }

    private class Instance implements IInstance {
        private final IFinalizer finalizer;
        private final EntityPlayer player;
        private int sx, sy, sz, y, z;

        public Instance(EntityPlayer player, IFinalizer finalizer) {
            this.finalizer = finalizer;
            this.player = player;
            this.sx = (((int) player.posX + 15) & ~15) - 16;
            this.sy = (int) player.posY + 1;
            this.sz = (((int) player.posZ + 15) & ~15) - 16;
            this.y = 0;
            this.z = 0;
        }

        private void replaceIfLavaOrWater(World world, int x, int y, int z, IBlockState stone) {
            BlockPos pos = new BlockPos(x, y, z);
            IBlockState state = world.getBlockState(pos);
            if (state == null) {
                player.sendStatusMessage(new TextComponentString(String.format("%s => unable to getBlockState", pos)), false);
                return;
            }
            Block block = state.getBlock();
            if (block == null) {
                player.sendStatusMessage(new TextComponentString(String.format("%s => unable to getBlock", pos)), false);
                return;
            }
            ResourceLocation res = block.getRegistryName();
            if (res == null) {
                player.sendStatusMessage(new TextComponentString(String.format("%s => unable to getRegistryName", pos)), false);
                return;
            }
            String name = res.getResourcePath();
            if (name == null) {
                player.sendStatusMessage(new TextComponentString(String.format("%s => %s, unable to getResourcePath", pos, res)), false);
                return;
            }
            if (name.equals("flowing_lava") || name.equals("flowing_water") || name.equals("lava")
                    || name.equals("water")) {
                world.setBlockState(pos, stone);
            }
        }

        private void guard(World world) {
            IBlockState stone = Block.getBlockFromName("minecraft:stone").getDefaultState();
            for (int y = 0; y < sy; y++) {
                for (int i = 0; i < 16; i++) {
                    replaceIfLavaOrWater(world, sx + i, sy - y, sz - 1, stone);
                    replaceIfLavaOrWater(world, sx + i, sy - y, sz + 16, stone);
                    replaceIfLavaOrWater(world, sx - 1, sy - y, sz + i, stone);
                    replaceIfLavaOrWater(world, sx + 16, sy - y, sz + i, stone);
                }
            }
        }

        public void run(World world) {
            if (y == 0 && z == 0) {
                guard(world);
            }
            for (int x = 0; x < 16; x++) {
                BlockPos pos = new BlockPos(sx + x, sy - y, sz + z);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                world.destroyBlock(pos, false);
                block.dropBlockAsItem(world, pos, state, FORTUNE_LEVEL);
            }
            if (z < 16) {
                z++;
            }
            if (z == 16) {
                z = 0;
                y++;
            }
            if (y == sy) {
                finalizer.run(this);
            }
        }
    }

    private static LinkedList<IInstance> instances = new LinkedList<IInstance>();

    @SubscribeEvent
    public static void worldTick(WorldTickEvent event) {
        synchronized (instances) {
            for (IInstance instance : instances) {
                instance.run(event.world);
            }
        }
    }

    public ChunkDestroyer() {
        super("chunk_destroyer");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        synchronized (instances) {
            instances.add(new Instance(playerIn, new IFinalizer() {
                public void run(IInstance instance) {
                    synchronized (instances) {
                        instances.remove(instance);
                    }
                }
            }));
        }
        ItemStack item = playerIn.getHeldItem(handIn);
        item.shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
