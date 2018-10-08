package com.snowstep115.ssutils.item;

import java.util.LinkedList;

import com.snowstep115.ssutils.SnowStepUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
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
        private int sx, sy, sz, y, z;

        public Instance(int sx, int sy, int sz, IFinalizer finalizer) {
            this.finalizer = finalizer;
            this.sx = ((sx + 15) & ~15) - 16;
            this.sy = sy + 1;
            this.sz = ((sz + 15) & ~15) - 16;
            y = 0;
            z = 0;
        }

        public void run(World world) {
            for (int x = 0; x < 16; x++) {
                BlockPos pos = new BlockPos(sx + x, sy - y, sz + z);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                if (0 <= block.getBlockHardness(state, world, pos)) {
                    world.destroyBlock(pos, false);
                    block.dropBlockAsItem(world, pos, state, FORTUNE_LEVEL);
                }
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
            instances.add(new Instance((int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ, new IFinalizer() {
                public void run(IInstance instance) {
                    synchronized (instances) {
                        instances.remove(instance);
                    }
                }
            }));
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, ItemStack.EMPTY);
    }
}
