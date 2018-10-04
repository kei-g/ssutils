package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class WheatSeedsCompressed extends Item {
    public WheatSeedsCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "wheat_seeds_compressed"));
        setUnlocalizedName("ssutils.wheat_seeds_compressed");
    }
}
