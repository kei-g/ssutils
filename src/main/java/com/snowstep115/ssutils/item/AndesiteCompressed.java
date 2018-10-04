package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class AndesiteCompressed extends Item {
    public AndesiteCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "andesite_compressed"));
        setUnlocalizedName("ssutils.andesite_compressed");
    }
}
