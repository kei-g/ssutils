package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CobblestoneCompressed extends Item {
    public CobblestoneCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "cobblestone_compressed"));
        setUnlocalizedName("ssutils.cobblestone_compressed");
    }
}
