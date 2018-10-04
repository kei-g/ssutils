package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.BlockCompressed;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;

public class ItemBlockBase extends ItemBlock {
    protected ItemBlockBase(BlockCompressed block, String name) {
        super(block);

        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        block.setRegistryName(getRegistryName());

        String unlocalizedName = SnowStepUtils.MODID + "." + name;
        setUnlocalizedName(unlocalizedName);
        block.setUnlocalizedName(unlocalizedName);
    }

    public void process(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(block);
    }

    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), null));
    }
}
