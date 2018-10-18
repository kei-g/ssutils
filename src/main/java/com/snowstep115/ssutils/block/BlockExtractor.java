package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntityExtractor;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class BlockExtractor extends Block {
    public BlockExtractor(String name) {
        super(Material.IRON);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        setUnlocalizedName(SnowStepUtils.MODID + "." + name);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setHardness(3.0f);
        setSoundType(SoundType.METAL);
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
