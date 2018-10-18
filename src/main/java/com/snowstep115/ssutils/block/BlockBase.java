package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block {
    protected BlockBase(Material material, String name, float hardness, SoundType soundType) {
        super(material);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        setUnlocalizedName(SnowStepUtils.MODID + "." + name);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setHardness(hardness);
        setSoundType(soundType);
    }
}
