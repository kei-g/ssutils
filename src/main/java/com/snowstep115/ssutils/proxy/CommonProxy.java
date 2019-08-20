package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.init.Items;
import com.snowstep115.ssutils.network.GuiHandler;
import com.snowstep115.ssutils.tileentity.TileEntityBankNull;
import com.snowstep115.ssutils.tileentity.TileEntityDistiller;
import com.snowstep115.ssutils.tileentity.TileEntityExtractor;
import com.snowstep115.ssutils.tileentity.TileEntityHeatExchanger;
import com.snowstep115.ssutils.tileentity.TileEntityInserter;
import com.snowstep115.ssutils.tileentity.TileEntitySnowChest;
import com.snowstep115.ssutils.tileentity.TileEntityTrashCan;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
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
        Items.init();
        TileEntity.register(Items.BANK_NULL.getRegistryName().toString(), TileEntityBankNull.class);
        TileEntity.register(Items.DISTILLER.getRegistryName().toString(), TileEntityDistiller.class);
        TileEntity.register(Items.EXTRACTOR.getRegistryName().toString(), TileEntityExtractor.class);
        TileEntity.register(Items.HEAT_EXCHANGER.getRegistryName().toString(), TileEntityHeatExchanger.class);
        TileEntity.register(Items.INSERTER.getRegistryName().toString(), TileEntityInserter.class);
        TileEntity.register(Items.SNOWCHEST.getRegistryName().toString(), TileEntitySnowChest.class);
        TileEntity.register(Items.TRASHCAN.getRegistryName().toString(), TileEntityTrashCan.class);
    }

    public void init(FMLInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(SnowStepUtils.INSTANCE, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
    }

    public World getClientWorld() {
        return null;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        Items.CHUNKLOADER.processBlock(event);
        Items.STONE_COMPRESSED.processBlock(event);
        Items.STONE_DOUBLE_COMPRESSED.processBlock(event);
        Items.GRANITE_COMPRESSED.processBlock(event);
        Items.GRANITE_DOUBLE_COMPRESSED.processBlock(event);
        Items.DIORITE_COMPRESSED.processBlock(event);
        Items.DIORITE_DOUBLE_COMPRESSED.processBlock(event);
        Items.ANDESITE_COMPRESSED.processBlock(event);
        Items.ANDESITE_DOUBLE_COMPRESSED.processBlock(event);
        Items.COBBLESTONE_COMPRESSED.processBlock(event);
        Items.COBBLESTONE_DOUBLE_COMPRESSED.processBlock(event);
        Items.DIRT_COMPRESSED.processBlock(event);
        Items.DIRT_DOUBLE_COMPRESSED.processBlock(event);
        Items.NETHERRACK_COMPRESSED.processBlock(event);
        Items.NETHERRACK_DOUBLE_COMPRESSED.processBlock(event);
        Items.BANK_NULL.processBlock(event);
        Items.DISTILLER.processBlock(event);
        Items.EXTRACTOR.processBlock(event);
        Items.HEAT_EXCHANGER.processBlock(event);
        Items.INSERTER.processBlock(event);
        Items.SNOW_TELEPORTER.processBlock(event);
        Items.SNOWCHEST.processBlock(event);
        Items.TRASHCAN.processBlock(event);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        Items.CHUNKLOADER.process(event);
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
