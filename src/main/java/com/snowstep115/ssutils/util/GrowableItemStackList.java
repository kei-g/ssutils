package com.snowstep115.ssutils.util;

import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GrowableItemStackList extends NonNullList<ItemStack> {
    private final ArrayList<ItemStack> list;

    private GrowableItemStackList(ArrayList<ItemStack> list) {
        super(list, ItemStack.EMPTY);
        this.list = list;
    }

    public GrowableItemStackList(int capacity) {
        this(new ArrayList<ItemStack>(capacity));
    }

    public ItemStack get(int index) {
        return index < size() ? super.get(index) : ItemStack.EMPTY;
    }

    public ItemStack set(int index, ItemStack stack) {
        if (index < size()) {
            return super.set(index, stack);
        }
        for (int i = size(); i < index; i++) {
            this.list.add(ItemStack.EMPTY);
        }
        this.list.add(stack);
        return ItemStack.EMPTY;
    }
}
