package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
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
        ModItems.ANDESITE_COMPRESSED.process(event);
        ModItems.COBBLESTONE_COMPRESSED.process(event);
        ModItems.DIORITE_COMPRESSED.process(event);
        ModItems.DIRT_COMPRESSED.process(event);
        ModItems.GRANITE_COMPRESSED.process(event);
        ModItems.NETHERRACK_COMPRESSED.process(event);
        ModItems.RED_FLOWER_COMPRESSED.process(event);
        ModItems.STONE_COMPRESSED.process(event);
        ModItems.WHEAT_SEEDS_COMPRESSED.process(event);
    }
}
