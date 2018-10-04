package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class StoneCompressed extends ItemBlock {
    public StoneCompressed() {
        super(new RockBlockCompressed("stone_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "stone_compressed"));
        setUnlocalizedName("ssutils.stone_compressed");
    }
}
