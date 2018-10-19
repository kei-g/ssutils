package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockSnowChest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSnowChest extends ItemBlockGeneric<BlockSnowChest> {
    public ItemSnowChest()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("snowchest", BlockSnowChest.class);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack snowchest, World world, List<String> tooltip, ITooltipFlag advanced) {
        if (snowchest.hasTagCompound()) {
            return;
        }
        NBTTagCompound compound = snowchest.getTagCompound();
        if (!compound.hasKey("items", NBT.TAG_LIST)) {
            return;
        }
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
        int tagCount = itemsTag.tagCount();
        for (int i = 0; i < 5 && i < tagCount; i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            tooltip.add(String.format("%s x%d", stack.getDisplayName(), stack.getCount()));
        }
        if (5 < tagCount) {
            tooltip.add(I18n.format("tooltip.container.itemstack.remaining", tagCount - 5));
        }
    }
}
