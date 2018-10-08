package com.snowstep115.ssutils;

import com.snowstep115.ssutils.item.ChunkDestroyer;
import com.snowstep115.ssutils.item.DirtCompressed;
import com.snowstep115.ssutils.item.DirtDoubleCompressed;
import com.snowstep115.ssutils.item.HarukaAxe;
import com.snowstep115.ssutils.item.ItemBase;
import com.snowstep115.ssutils.item.ItemBlockBase;
import com.snowstep115.ssutils.item.RedFlowerCompressed;
import com.snowstep115.ssutils.item.RockCompressed;
import com.snowstep115.ssutils.item.RockDoubleCompressed;
import com.snowstep115.ssutils.item.WheatSeedsCompressed;
import com.snowstep115.ssutils.item.Yukiho;
import net.minecraft.item.Item;

public class ModItems {
    public static ItemBlockBase ANDESITE_COMPRESSED;
    public static ItemBlockBase ANDESITE_DOUBLE_COMPRESSED;
    public static final ItemBase CHUNK_DESTROYER = new ChunkDestroyer();
    public static ItemBlockBase COBBLESTONE_COMPRESSED;
    public static ItemBlockBase COBBLESTONE_DOUBLE_COMPRESSED;
    public static ItemBlockBase DIORITE_COMPRESSED;
    public static ItemBlockBase DIORITE_DOUBLE_COMPRESSED;
    public static ItemBlockBase DIRT_COMPRESSED;
    public static ItemBlockBase DIRT_DOUBLE_COMPRESSED;
    public static ItemBlockBase GRANITE_COMPRESSED;
    public static ItemBlockBase GRANITE_DOUBLE_COMPRESSED;
    public static final HarukaAxe HARUKA_AXE = new HarukaAxe();
    public static ItemBlockBase NETHERRACK_COMPRESSED;
    public static ItemBlockBase NETHERRACK_DOUBLE_COMPRESSED;
    public static final ItemBase RED_FLOWER_COMPRESSED = new RedFlowerCompressed();
    public static ItemBlockBase STONE_COMPRESSED;
    public static ItemBlockBase STONE_DOUBLE_COMPRESSED;
    public static final ItemBase WHEAT_SEEDS_COMPRESSED = new WheatSeedsCompressed();
    public static final Yukiho YUKIHO = new Yukiho();

    public static void init() {
        try {
            ANDESITE_COMPRESSED = new RockCompressed("andesite");
            ANDESITE_DOUBLE_COMPRESSED = new RockDoubleCompressed("andesite");
            COBBLESTONE_COMPRESSED = new RockCompressed("cobblestone");
            COBBLESTONE_DOUBLE_COMPRESSED = new RockDoubleCompressed("cobblestone");
            DIORITE_COMPRESSED = new RockCompressed("diorite");
            DIORITE_DOUBLE_COMPRESSED = new RockDoubleCompressed("diorite");
            DIRT_COMPRESSED = new DirtCompressed();
            DIRT_DOUBLE_COMPRESSED = new DirtDoubleCompressed();
            GRANITE_COMPRESSED = new RockCompressed("granite");
            GRANITE_DOUBLE_COMPRESSED = new RockDoubleCompressed("granite");
            NETHERRACK_COMPRESSED = new RockCompressed("netherrack");
            NETHERRACK_DOUBLE_COMPRESSED = new RockDoubleCompressed("netherrack");
            STONE_COMPRESSED = new RockCompressed("stone");
            STONE_DOUBLE_COMPRESSED = new RockDoubleCompressed("stone");
        } catch (Throwable exception) {
            SnowStepUtils.LOGGER.info(exception.getMessage());
        }
    }
}
