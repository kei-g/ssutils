package com.snowstep115.ssutils;

import com.snowstep115.ssutils.init.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SnowStepUtilsTab extends CreativeTabs {
    public SnowStepUtilsTab() {
        super(SnowStepUtils.MODID);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(Items.YUKIHO);
    }
}
