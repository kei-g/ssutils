package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RedFlowerCompressed extends Item {
    public RedFlowerCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "red_flower_compressed"));
        setUnlocalizedName("ssutils.red_flower_compressed");
    }
}
