package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
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
        ModItems.EXTRACTOR.process(event);
        ModItems.HARUKA_AXE.process(event);
        ModItems.INSERTER.process(event);
        ModItems.RED_FLOWER_COMPRESSED.process(event);
        ModItems.SNOW_TELEPORTER.process(event);
        ModItems.SNOWCHEST.process(event);
        ModItems.TRASHCAN.process(event);
        ModItems.WHEAT_SEEDS_COMPRESSED.process(event);
        ModItems.YUKIHO.process(event);
    }
}
