package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class NetherrackCompressed extends Item {
    public NetherrackCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "netherrack_compressed"));
        setUnlocalizedName("ssutils.netherrack_compressed");
    }
}
