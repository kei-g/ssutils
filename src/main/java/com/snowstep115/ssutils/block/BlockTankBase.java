package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.tileentity.TileEntityTankBase;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTankBase extends BlockBase {
    BlockTankBase(Material material, String name, float hardness, SoundType soundType) {
        super(material, name, hardness, soundType);
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile,
            ItemStack stack) {
        if (tile instanceof TileEntityTankBase) {
            TileEntityTankBase tank = (TileEntityTankBase) tile;
            tank.dropAll();
            world.removeTileEntity(pos);
        } else {
            super.harvestBlock(world, player, pos, state, tile, stack);
        }
    }
}
