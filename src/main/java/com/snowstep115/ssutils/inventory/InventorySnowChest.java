package com.snowstep115.ssutils.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants.NBT;

public class InventorySnowChest implements IInventory {
    private static final String KEY_ITEMS = "items";

    private final ItemStack snowchest;
    private final ItemStack[] stacks = new ItemStack[130];

    public InventorySnowChest(ItemStack snowchest) {
        this.snowchest = snowchest;
        for (int i = 0; i < this.stacks.length; i++) {
            this.stacks[i] = ItemStack.EMPTY;
        }
        if (!snowchest.hasTagCompound()) {
            return;
        }
        NBTTagCompound compound = snowchest.getTagCompound();
        if (!compound.hasKey(KEY_ITEMS, NBT.TAG_LIST)) {
            return;
        }
        NBTTagList itemsTag = compound.getTagList(KEY_ITEMS, NBT.TAG_COMPOUND);
        for (int i = 0; i < this.stacks.length && i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            this.stacks[i] = new ItemStack(nbt);
        }
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TextComponentString("");
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        if (!snowchest.hasTagCompound()) {
            return;
        }
        NBTTagCompound compound = snowchest.getTagCompound();
        if (!compound.hasKey(KEY_ITEMS, NBT.TAG_LIST)) {
            return;
        }
        NBTTagList itemsTag = compound.getTagList(KEY_ITEMS, NBT.TAG_COMPOUND);
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < this.stacks.length && i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            items.add(i, stack);
        }
        Collections.sort(items, new Comparator<ItemStack>() {
            @Override
            public int compare(ItemStack s1, ItemStack s2) {
                Item i1 = s1.getItem();
                Item i2 = s2.getItem();
                if (i1 == Items.AIR) {
                    return i2 == Items.AIR ? 0 : 1;
                }
                if (i2 == Items.AIR) {
                    return -1;
                }
                int id1 = Item.getIdFromItem(i1);
                int id2 = Item.getIdFromItem(i2);
                int c = id1 - id2;
                return c == 0 ? s1.getMetadata() - s2.getMetadata() : c;
            }
        });
        compound.removeTag(KEY_ITEMS);
        itemsTag = new NBTTagList();
        compound.setTag(KEY_ITEMS, itemsTag);
        for (ItemStack stack : items) {
            NBTTagCompound nbt = stack.serializeNBT();
            itemsTag.appendTag(nbt);
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.length;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.stacks[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack stack = this.stacks[index];
        ItemStack result = stack.copy();
        stack.shrink(count);
        result.setCount(count);
        return result;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack result = this.stacks[index].copy();
        this.stacks[index] = ItemStack.EMPTY;
        return result;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks[index] = stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if (!this.snowchest.hasTagCompound()) {
            this.snowchest.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound compound = this.snowchest.getTagCompound();
        if (!compound.hasKey(KEY_ITEMS, NBT.TAG_LIST)) {
            compound.setTag(KEY_ITEMS, new NBTTagList());
        }
        NBTTagList itemsTag = compound.getTagList(KEY_ITEMS, NBT.TAG_COMPOUND);
        for (int i = 0; i < this.stacks.length; i++) {
            ItemStack stack = this.stacks[i];
            NBTTagCompound nbt = stack.serializeNBT();
            if (i < itemsTag.tagCount()) {
                itemsTag.set(i, nbt);
            } else {
                itemsTag.appendTag(nbt);
            }
        }
    }
}
