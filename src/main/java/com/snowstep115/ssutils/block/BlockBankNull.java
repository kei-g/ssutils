package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.ModItems;
import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.network.GuiHandler;
import com.snowstep115.ssutils.tileentity.TileEntityBankNull;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public class BlockBankNull extends BlockBase {
    public BlockBankNull(String name) {
        super(Material.IRON, name, 3.0f, SoundType.METAL);
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityBankNull) {
            TileEntityBankNull bankNull = (TileEntityBankNull) tile;
            player.openContainer = bankNull.createContainer(player.inventory, player);
            player.openGui(SnowStepUtils.INSTANCE, GuiHandler.OPEN_GUI_BANK_NULL_ID, world, pos.getX(), pos.getY(),
                    pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if (!stack.hasTagCompound()) {
            return;
        }
        NBTTagCompound compound = stack.getTagCompound();
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
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
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile,
            ItemStack stack) {
        if (tile instanceof TileEntityBankNull) {
            TileEntityBankNull bankNull = (TileEntityBankNull) tile;
            if (stack.getItem() == ModItems.YUKIHO) {
                bankNull.dropAll();
            } else {
                bankNull.spawnAsEntity();
            }
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
        return new TileEntityBankNull();
    }
}
