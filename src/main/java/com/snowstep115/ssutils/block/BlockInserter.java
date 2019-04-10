package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.tileentity.TileEntityInserter;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInserter extends BlockBase {
    public BlockInserter(String name) {
        super(Material.IRON, name, 3.0f, SoundType.METAL);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityInserter();
    }
}
