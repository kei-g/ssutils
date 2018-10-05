package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class Yukiho extends Item {
    public Yukiho() {
        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, "yukiho"));
        setUnlocalizedName("ssutils.yukiho");
    }

    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
