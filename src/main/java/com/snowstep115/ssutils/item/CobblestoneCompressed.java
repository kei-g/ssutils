package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class CobblestoneCompressed extends ItemBlock {
    public CobblestoneCompressed() {
        super(new RockBlockCompressed("cobblestone_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "cobblestone_compressed"));
        setUnlocalizedName("ssutils.cobblestone_compressed");
    }
}
