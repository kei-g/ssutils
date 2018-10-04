package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class GraniteCompressed extends ItemBlock {
    public GraniteCompressed() {
        super(new RockBlockCompressed("granite_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "granite_compressed"));
        setUnlocalizedName("ssutils.granite_compressed");
    }
}
