package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.ModItems;
import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class Yukiho extends ItemPickaxe {
    public static final int FORTUNE_LEVEL = 3;
    public static final String NAME = "yukiho";

    public Yukiho() {
        super(ToolMaterial.DIAMOND);

        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, NAME));
        setUnlocalizedName(SnowStepUtils.MODID + "." + NAME);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
    }

    public void process(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    private Vec3i getVector(BlockPos pos, Entity entity) {
        double dx = (double) pos.getX() - entity.posX;
        double dy = (double) pos.getY() - entity.posY;
        double dz = (double) pos.getZ() - entity.posZ;
        double ax = Math.abs(dx);
        double ay = Math.abs(dy);
        double az = Math.abs(dz);
        if (ax < ay) {
            if (az < ay) {
                return dy < 0 ? new Vec3i(0, -1, 0) : new Vec3i(0, 1, 0);
            }
        } else {
            if (az < ax) {
                return dx < 0 ? new Vec3i(-1, 0, 0) : new Vec3i(1, 0, 0);
            }
        }
        return dz < 0 ? new Vec3i(0, 0, -1) : new Vec3i(0, 0, 1);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
            EntityLivingBase entity) {
        boolean result = super.onBlockDestroyed(stack, world, state, pos, entity);
        setDamage(stack, 0);
        Vec3i v = getVector(pos, entity);
        for (int i = 0; i < 15; i++) {
            pos = pos.add(v);
            IBlockState st = world.getBlockState(pos);
            Block block = st.getBlock();
            if (block.getBlockHardness(st, world, pos) < 0) { // unbreakable
                break;
            }
            world.destroyBlock(pos, false);
            block.dropBlockAsItem(world, pos, st, FORTUNE_LEVEL);
        }
        return result;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        for (int x = -16; x < 16; x++) {
            for (int y = -16; y < 16; y++) {
                for (int z = -16; z < 16; z++) {
                    BlockPos pos = new BlockPos(playerIn.posX + x, playerIn.posY + y, playerIn.posZ + z);
                    IBlockState state = worldIn.getBlockState(pos);
                    Block block = state.getBlock();
                    String name = block.getRegistryName().getResourcePath();
                    if (name.equals("log")) {
                        worldIn.destroyBlock(pos, true);
                    } else if (name.equals("leaves")) {
                        worldIn.destroyBlock(pos, false);
                        block.dropBlockAsItem(worldIn, pos, state, FORTUNE_LEVEL);
                    }
                }
            }
        }
        ItemStack item = playerIn.getHeldItem(handIn);
        return ActionResult.newResult(EnumActionResult.SUCCESS, item);
    }
}
