package com.snowstep115.ssutils;

import com.snowstep115.ssutils.item.AndesiteCompressed;
import com.snowstep115.ssutils.item.CobblestoneCompressed;
import com.snowstep115.ssutils.item.DioriteCompressed;
import com.snowstep115.ssutils.item.DirtCompressed;
import com.snowstep115.ssutils.item.GraniteCompressed;
import com.snowstep115.ssutils.item.ItemBlockBase;
import com.snowstep115.ssutils.item.NetherrackCompressed;
import com.snowstep115.ssutils.item.RedFlowerCompressed;
import com.snowstep115.ssutils.item.StoneCompressed;
import com.snowstep115.ssutils.item.WheatSeedsCompressed;
import com.snowstep115.ssutils.item.Yukiho;

import net.minecraft.item.Item;

public class ModItems {
    public static ItemBlockBase ANDESITE_COMPRESSED;
    public static ItemBlockBase COBBLESTONE_COMPRESSED;
    public static ItemBlockBase DIORITE_COMPRESSED;
    public static ItemBlockBase DIRT_COMPRESSED;
    public static ItemBlockBase GRANITE_COMPRESSED;
    public static ItemBlockBase NETHERRACK_COMPRESSED;
    public static final RedFlowerCompressed RED_FLOWER_COMPRESSED = new RedFlowerCompressed();
    public static ItemBlockBase STONE_COMPRESSED;
    public static final WheatSeedsCompressed WHEAT_SEEDS_COMPRESSED = new WheatSeedsCompressed();
    public static final Yukiho YUKIHO = new Yukiho();

    public static void init() {
        try {
            ANDESITE_COMPRESSED = new AndesiteCompressed();
            COBBLESTONE_COMPRESSED = new CobblestoneCompressed();
            DIORITE_COMPRESSED = new DioriteCompressed();
            DIRT_COMPRESSED = new DirtCompressed();
            GRANITE_COMPRESSED = new GraniteCompressed();
            NETHERRACK_COMPRESSED = new NetherrackCompressed();
            STONE_COMPRESSED = new StoneCompressed();
        } catch (Exception e) {
            SnowStepUtils.LOGGER.info(e.getMessage());
        }
    }
}
