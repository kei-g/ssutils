package com.snowstep115.ssutils.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemKey {
    private final int id;
    private final int damage;
    private final NBTTagCompound tag;

    public ItemKey(ItemStack stack) {
        Item item = stack.getItem();
        this.id = Item.getIdFromItem(item);
        this.damage = stack.getItemDamage();
        this.tag = stack.hasTagCompound() ? stack.getTagCompound() : null;
    }

    @Override
    public int hashCode() {
        return this.id + this.damage + (this.tag != null ? this.tag.hashCode() : 0);
    }

    @Override
    public boolean equals(Object otherKey) {
        return otherKey instanceof ItemKey && ((ItemKey) otherKey).equals(this);
    }

    public boolean equals(ItemKey otherKey) {
        return this.id == otherKey.id && this.damage == otherKey.damage && ((this.tag == null && otherKey.tag == null)
                || (this.tag != null && otherKey.tag != null && this.tag.equals(otherKey.tag)));
    }
}
