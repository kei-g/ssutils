package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class BlockBase extends Block {
    protected BlockBase(Material material, float hardness) {
        super(material);
        setCreativeTab(SnowStepUtils.CREATIVE_TAB);
        setHardness(hardness);
    }
}
