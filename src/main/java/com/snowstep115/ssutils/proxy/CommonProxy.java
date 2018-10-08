package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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
        ModItems.init();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModItems.STONE_COMPRESSED.processBlock(event);
        ModItems.STONE_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.GRANITE_COMPRESSED.processBlock(event);
        ModItems.GRANITE_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.DIORITE_COMPRESSED.processBlock(event);
        ModItems.DIORITE_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.ANDESITE_COMPRESSED.processBlock(event);
        ModItems.ANDESITE_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.COBBLESTONE_COMPRESSED.processBlock(event);
        ModItems.COBBLESTONE_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.DIRT_COMPRESSED.processBlock(event);
        ModItems.DIRT_DOUBLE_COMPRESSED.processBlock(event);
        ModItems.NETHERRACK_COMPRESSED.processBlock(event);
        ModItems.NETHERRACK_DOUBLE_COMPRESSED.processBlock(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.STONE_COMPRESSED.process(event);
        ModItems.STONE_DOUBLE_COMPRESSED.process(event);
        ModItems.GRANITE_COMPRESSED.process(event);
        ModItems.GRANITE_DOUBLE_COMPRESSED.process(event);
        ModItems.DIORITE_COMPRESSED.process(event);
        ModItems.DIORITE_DOUBLE_COMPRESSED.process(event);
        ModItems.ANDESITE_COMPRESSED.process(event);
        ModItems.ANDESITE_DOUBLE_COMPRESSED.process(event);
        ModItems.COBBLESTONE_COMPRESSED.process(event);
        ModItems.COBBLESTONE_DOUBLE_COMPRESSED.process(event);
        ModItems.DIRT_COMPRESSED.process(event);
        ModItems.DIRT_DOUBLE_COMPRESSED.process(event);
        ModItems.NETHERRACK_COMPRESSED.process(event);
        ModItems.NETHERRACK_DOUBLE_COMPRESSED.process(event);
        ModItems.CHUNK_DESTROYER.process(event);
        ModItems.HARUKA_AXE.process(event);
        ModItems.RED_FLOWER_COMPRESSED.process(event);
        ModItems.WHEAT_SEEDS_COMPRESSED.process(event);
        ModItems.YUKIHO.process(event);
    }
}
