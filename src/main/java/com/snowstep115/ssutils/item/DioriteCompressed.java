package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class DioriteCompressed extends ItemBlock {
    public DioriteCompressed() {
        super(new RockBlockCompressed("cobblestone_compressed"));
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "diorite_compressed"));
        setUnlocalizedName("ssutils.diorite_compressed");
    }
}
