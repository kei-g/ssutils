package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntityExtractor;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockExtractor extends Block {
    private static final PropertyBool DUMMY = PropertyBool.create("dummy");

    public BlockExtractor(String name) {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        setUnlocalizedName(SnowStepUtils.MODID + "." + name);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setHardness(3.0f);
        setDefaultState(this.blockState.getBaseState().withProperty(DUMMY, true));
        setSoundType(SoundType.METAL);
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
        return new BlockStateContainer(this, new IProperty[] { DUMMY });
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(DUMMY, meta != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(DUMMY) ? 1 : 0;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityExtractor();
    }
}
