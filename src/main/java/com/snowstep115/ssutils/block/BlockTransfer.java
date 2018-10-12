package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.tileentity.TileEntityTransfer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTransfer extends BlockBase implements ITileEntityProvider {
    public BlockTransfer() {
        super(Material.IRON, 1.5f);
        setSoundType(SoundType.METAL);
    }

    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTransfer();
    }
}
