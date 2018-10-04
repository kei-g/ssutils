package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class AndesiteCompressed extends ItemBlock {
    public AndesiteCompressed() {
        super(new RockBlockCompressed("andesite_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "andesite_compressed"));
        setUnlocalizedName("ssutils.andesite_compressed");
    }
}
