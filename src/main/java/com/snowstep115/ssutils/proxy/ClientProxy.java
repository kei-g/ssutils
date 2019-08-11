package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.init.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getMinecraft().world;
    }

    /***
     * ブロックやアイテムのモデル（テクスチャは何を使うとかテクスチャの向きとか定義したファイル）を読み込みます。<br>
     * 
     * @param event
     */
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        Items.STONE_COMPRESSED.process(event);
        Items.STONE_DOUBLE_COMPRESSED.process(event);
        Items.GRANITE_COMPRESSED.process(event);
        Items.GRANITE_DOUBLE_COMPRESSED.process(event);
        Items.DIORITE_COMPRESSED.process(event);
        Items.DIORITE_DOUBLE_COMPRESSED.process(event);
        Items.ANDESITE_COMPRESSED.process(event);
        Items.ANDESITE_DOUBLE_COMPRESSED.process(event);
        Items.COBBLESTONE_COMPRESSED.process(event);
        Items.COBBLESTONE_DOUBLE_COMPRESSED.process(event);
        Items.DIRT_COMPRESSED.process(event);
        Items.DIRT_DOUBLE_COMPRESSED.process(event);
        Items.NETHERRACK_COMPRESSED.process(event);
        Items.NETHERRACK_DOUBLE_COMPRESSED.process(event);
        Items.BANK_NULL.process(event);
        Items.CHUNK_DESTROYER.process(event);
        Items.DISTILLER.process(event);
        Items.EXTRACTOR.process(event);
        Items.HARUKA_AXE.process(event);
        Items.HEAT_EXCHANGER.process(event);
        Items.INSERTER.process(event);
        Items.RED_FLOWER_COMPRESSED.process(event);
        Items.SNOW_TELEPORTER.process(event);
        Items.SNOWCHEST.process(event);
        Items.TRASHCAN.process(event);
        Items.WHEAT_SEEDS_COMPRESSED.process(event);
        Items.YUKIHO.process(event);
    }
}
