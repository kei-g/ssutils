package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        SnowStepUtils.LOGGER.info("ClientProxy.preInit");
    }

    /***
     * ブロックやアイテムのモデル（テクスチャは何を使うとかテクスチャの向きとか定義したファイル）を読み込みます。<br>
     * 
     * @param event
     */
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        SnowStepUtils.LOGGER.info("ClientProxy.registerModels");
        ModelLoader.setCustomModelResourceLocation(ModItems.ANDESITE_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "andesite_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.COBBLESTONE_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "cobblestone_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.DIORITE_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "diorite_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.DIRT_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "dirt_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.GRANITE_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "granite_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.NETHERRACK_COMPRESSED, 0,new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "netherrack_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.STONE_COMPRESSED, 0,new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "stone_compressed"), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ModItems.WHEAT_SEEDS_COMPRESSED, 0, new ModelResourceLocation(new ResourceLocation(SnowStepUtils.MODID, "wheat_seeds_compressed"), "inventory"));
    }
}
