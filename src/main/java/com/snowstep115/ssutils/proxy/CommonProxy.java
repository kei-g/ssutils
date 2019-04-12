package com.snowstep115.ssutils.proxy;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.ModItems;
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
        ModItems.init();
        TileEntity.register(ModItems.BANK_NULL.getRegistryName().toString(), TileEntityBankNull.class);
        TileEntity.register(ModItems.DISTILLER.getRegistryName().toString(), TileEntityDistiller.class);
        TileEntity.register(ModItems.EXTRACTOR.getRegistryName().toString(), TileEntityExtractor.class);
        TileEntity.register(ModItems.HEAT_EXCHANGER.getRegistryName().toString(), TileEntityHeatExchanger.class);
        TileEntity.register(ModItems.INSERTER.getRegistryName().toString(), TileEntityInserter.class);
        TileEntity.register(ModItems.SNOWCHEST.getRegistryName().toString(), TileEntitySnowChest.class);
        TileEntity.register(ModItems.TRASHCAN.getRegistryName().toString(), TileEntityTrashCan.class);
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
        ModItems.BANK_NULL.processBlock(event);
        ModItems.DISTILLER.processBlock(event);
        ModItems.EXTRACTOR.processBlock(event);
        ModItems.HEAT_EXCHANGER.processBlock(event);
        ModItems.INSERTER.processBlock(event);
        ModItems.SNOW_TELEPORTER.processBlock(event);
        ModItems.SNOWCHEST.processBlock(event);
        ModItems.TRASHCAN.processBlock(event);
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
        ModItems.BANK_NULL.process(event);
        ModItems.CHUNK_DESTROYER.process(event);
        ModItems.DISTILLER.process(event);
        ModItems.EXTRACTOR.process(event);
        ModItems.HARUKA_AXE.process(event);
        ModItems.HEAT_EXCHANGER.process(event);
        ModItems.INSERTER.process(event);
        ModItems.RED_FLOWER_COMPRESSED.process(event);
        ModItems.SNOW_TELEPORTER.process(event);
        ModItems.SNOWCHEST.process(event);
        ModItems.TRASHCAN.process(event);
        ModItems.WHEAT_SEEDS_COMPRESSED.process(event);
        ModItems.YUKIHO.process(event);
    }
}
