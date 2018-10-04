package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockCompressed extends Block {
    public BlockCompressed(Material material, float hardness, String name) {
        super(material);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        setUnlocalizedName("ssutils." + name);
        setHardness(hardness);
    }
}
