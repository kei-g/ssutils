package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class StoneCompressed extends Item {
    public StoneCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "stone_compressed"));
        setUnlocalizedName("ssutils.stone_compressed");
    }
}
