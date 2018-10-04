package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DioriteCompressed extends Item {
    public DioriteCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "diorite_compressed"));
        setUnlocalizedName("ssutils.diorite_compressed");
    }
}
