package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.network.GuiHandler;
import com.snowstep115.ssutils.tileentity.TileEntityTrashCan;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTrashCan extends BlockBase {
    public BlockTrashCan(String name) {
        super(Material.IRON, name, 1.5f, SoundType.METAL);
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
        if (tile instanceof TileEntityTrashCan) {
            TileEntityTrashCan trashcan = (TileEntityTrashCan) tile;
            player.openContainer = trashcan.createContainer(player.inventory, player);
            player.openGui(SnowStepUtils.INSTANCE, GuiHandler.OPEN_GUI_TRASHCAN_ID, world, 0, 0, 0);
        }
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityTrashCan();
    }
}
