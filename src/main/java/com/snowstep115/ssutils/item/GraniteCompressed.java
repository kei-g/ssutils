package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class GraniteCompressed extends Item {
    public GraniteCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "granite_compressed"));
        setUnlocalizedName("ssutils.granite_compressed");
    }
}
