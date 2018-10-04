package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class RedFlowerCompressed extends Item {
    public RedFlowerCompressed() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "red_flower_compressed"));
        setUnlocalizedName("ssutils.red_flower_compressed");
    }

    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
