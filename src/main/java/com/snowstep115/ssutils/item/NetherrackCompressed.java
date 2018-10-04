package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.RockBlockCompressed;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class NetherrackCompressed extends ItemBlock {
    public NetherrackCompressed() {
        super(new RockBlockCompressed("cobblestone_compressed"));
        block.setHardness(0.5f);
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "netherrack_compressed"));
        setUnlocalizedName("ssutils.netherrack_compressed");
    }
}
