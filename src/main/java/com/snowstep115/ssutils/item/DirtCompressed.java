package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.GroundBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class DirtCompressed extends ItemBlock {
    public DirtCompressed() {
        super(new GroundBlockCompressed("dirt_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "dirt_compressed"));
        setUnlocalizedName("ssutils.dirt_compressed");
    }
}
