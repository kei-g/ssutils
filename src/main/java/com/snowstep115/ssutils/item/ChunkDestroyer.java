package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import java.util.function.Consumer;
import java.util.LinkedList;
import java.util.Random;
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
import net.minecraft.util.math.ChunkPos;
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
        private final IBlockState air;
        private final IFinalizer finalizer;
        private final boolean isSlimeChunk;
        private final EntityPlayer player;
        private final IBlockState stoneslab;
        private final int sx, sy, sz;
        private final IBlockState torch;

        private Consumer<World> delegate;
        private int y, z;

        public Instance(World world, EntityPlayer player, IFinalizer finalizer) {
            this.air = Block.getBlockFromName("minecraft:air").getDefaultState();
            BlockPos bpos = player.getPosition();
            ChunkPos cpos = new ChunkPos(bpos);
            Random rnd = new Random(world.getSeed() + (long) (cpos.x * cpos.x * 0x4c1906) + (long) (cpos.x * 0x5ac0db)
                    + (long) (cpos.z * cpos.z) * 0x4307a7L + (long) (cpos.z * 0x5f24f) ^ 0x3ad8025f);
            this.finalizer = finalizer;
            this.isSlimeChunk = rnd.nextInt(10) == 0;
            this.player = player;
            this.stoneslab = Block.getBlockFromName("minecraft:stone_slab").getStateFromMeta(8);
            this.sx = cpos.getXStart();
            this.sy = bpos.getY() + 1;
            this.sz = cpos.getZStart();
            this.torch = Block.getBlockFromName("minecraft:torch").getDefaultState();

            this.delegate = (w) -> {
                guard(w);
            };
            this.y = 0;
            this.z = 0;
        }

        private void construct(World world) {
            for (int y = 11; y <= 38; y += 3) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        BlockPos pos = new BlockPos(this.sx + x, y, this.sz + z);
                        world.setBlockState(pos, this.stoneslab);
                    }
                }
            }
            for (int y = 12; y <= 39; y += 3) {
                for (int x = 3; x <= 12; x += 3) {
                    for (int z = 3; z <= 12; z += 3) {
                        BlockPos pos = new BlockPos(this.sx + x, y, this.sz + z);
                        world.setBlockState(pos, this.torch);
                    }
                }
            }
            this.finalizer.run(this);
        }

        private void destroy(World world) {
            for (int x = 0; x < 16; x++) {
                BlockPos pos = new BlockPos(this.sx + x, this.sy - this.y, this.sz + this.z);
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();
                world.setBlockState(pos, this.air);
                block.dropBlockAsItem(world, pos, state, FORTUNE_LEVEL);
            }
            if (this.z < 16) {
                this.z++;
            }
            if (this.z == 16) {
                this.z = 0;
                this.y++;
            }
            if (this.y == this.sy) {
                if (this.isSlimeChunk) {
                    this.delegate = (w) -> {
                        construct(w);
                    };
                } else {
                    finalizer.run(this);
                }
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
            this.delegate = (w) -> {
                destroy(w);
            };
        }

        private void replaceIfLavaOrWater(World world, int x, int y, int z, IBlockState stone) {
            BlockPos pos = new BlockPos(x, y, z);
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            ResourceLocation res = block.getRegistryName();
            String name = res.getResourcePath();
            if (name.equals("flowing_lava") || name.equals("flowing_water") || name.equals("lava")
                    || name.equals("water")) {
                world.setBlockState(pos, stone);
            }
        }

        public void run(World world) {
            this.delegate.accept(world);
        }
    }

    private static LinkedList<IInstance> finalizing = new LinkedList<IInstance>();
    private static LinkedList<IInstance> instances = new LinkedList<IInstance>();

    @SubscribeEvent
    public static void worldTick(WorldTickEvent event) {
        synchronized (instances) {
            for (IInstance instance : instances) {
                instance.run(event.world);
            }
            for (IInstance instance : finalizing) {
                instances.remove(instance);
            }
            finalizing.clear();
        }
    }

    public ChunkDestroyer() {
        super("chunkdestroyer");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        synchronized (instances) {
            instances.add(new Instance(worldIn, playerIn, new IFinalizer() {
                public void run(IInstance instance) {
                    finalizing.add(instance);
                }
            }));
        }
        ItemStack item = playerIn.getHeldItem(handIn);
        item.shrink(1);
        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
