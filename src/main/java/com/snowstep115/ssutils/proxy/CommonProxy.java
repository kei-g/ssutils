package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/***
 * クライアントとサーバー共通でアイテム、ブロック、ツールの読み込みます。
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        SnowStepUtils.LOGGER.info("CommonProxy.preInit");
    }

    public void init(FMLInitializationEvent event) {
        SnowStepUtils.LOGGER.info("CommonProxy.init");
    }

    public void postInit(FMLPostInitializationEvent event) {
        SnowStepUtils.LOGGER.info("CommonProxy.postInit");
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        SnowStepUtils.LOGGER.info("CommonProxy.registerBlocks");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        SnowStepUtils.LOGGER.info("CommonProxy.registerItems");
        event.getRegistry().register(ModItems.ANDESITE_COMPRESSED);
        event.getRegistry().register(ModItems.COBBLESTONE_COMPRESSED);
        event.getRegistry().register(ModItems.DIORITE_COMPRESSED);
        event.getRegistry().register(ModItems.DIRT_COMPRESSED);
        event.getRegistry().register(ModItems.GRANITE_COMPRESSED);
        event.getRegistry().register(ModItems.NETHERRACK_COMPRESSED);
        event.getRegistry().register(ModItems.STONE_COMPRESSED);
        event.getRegistry().register(ModItems.WHEAT_SEEDS_COMPRESSED);
    }
}
