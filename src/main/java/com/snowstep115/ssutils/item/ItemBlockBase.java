package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockBase extends ItemBlock {
    protected ItemBlockBase(BlockBase block, String name) {
        super(block);

        setRegistryName(new ResourceLocation(SnowStepUtils.MODID, name));
        block.setRegistryName(getRegistryName());

        String unlocalizedName = SnowStepUtils.MODID + "." + name;
        setUnlocalizedName(unlocalizedName);
        block.setUnlocalizedName(unlocalizedName);
    }

    public void processBlock(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(block);
    }

    public void process(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @SideOnly(Side.CLIENT)
    public void process(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), null));
    }
}
