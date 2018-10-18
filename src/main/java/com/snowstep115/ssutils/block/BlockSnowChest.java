package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.container.ContainerSnowChest;
import com.snowstep115.ssutils.network.GuiHandler;
import com.snowstep115.ssutils.tileentity.TileEntitySnowChest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockSnowChest extends BlockBase {
    private static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockSnowChest(String name) {
        super(Material.IRON, name, 3.0f, SoundType.METAL);
        setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getFront(meta);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumFacing facing = state.getValue(FACING);
        int meta = facing.getIndex();
        return meta;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack) {
        EnumFacing facing = placer.getHorizontalFacing().getOpposite();
        state = state.withProperty(FACING, facing);
        world.setBlockState(pos, state);
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!stack.hasTagCompound()) {
            return;
        }
        NBTTagCompound tag = stack.getTagCompound();
        NBTTagList itemsTag = tag.getTagList("items", NBT.TAG_COMPOUND);
        if (itemsTag == null) {
            return;
        }
        TileEntity tile = world.getTileEntity(pos);
        if (tile == null) {
            return;
        }
        NBTTagCompound tileTag = tile.serializeNBT();
        tileTag.setTag("items", itemsTag);
        tile.readFromNBT(tileTag);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntitySnowChest) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_OPEN,
                    SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            TileEntitySnowChest snowChest = (TileEntitySnowChest) tile;
            player.openContainer = snowChest.createContainer(player.inventory, player);
            player.openGui(SnowStepUtils.INSTANCE, GuiHandler.OPEN_GUI_SNOWCHEST_ID, world, pos.getX(), pos.getY(),
                    pos.getZ());
        }
        return true;
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile,
            ItemStack stack) {
        if (tile instanceof TileEntitySnowChest) {
            TileEntitySnowChest snowchest = (TileEntitySnowChest) tile;
            snowchest.spawnAsEntity();
            world.removeTileEntity(pos);
        } else {
            super.harvestBlock(world, player, pos, state, tile, stack);
        }
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntitySnowChest();
    }
}
