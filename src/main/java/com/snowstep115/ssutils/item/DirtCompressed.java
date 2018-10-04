package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DirtCompressed extends Item {
    public DirtCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "dirt_compressed"));
        setUnlocalizedName("ssutils.dirt_compressed");
    }
}
