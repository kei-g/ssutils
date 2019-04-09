package com.snowstep115.ssutils.tileentity;

import java.util.Collection;
import java.util.HashMap;
import com.snowstep115.ssutils.container.ContainerBankNull;
import com.snowstep115.ssutils.util.GrowableItemStackList;
import com.snowstep115.ssutils.util.ItemKey;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityBankNull extends TileEntityLockableLoot {
    private final GrowableItemStackList stacks = new GrowableItemStackList(54);

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerBankNull(this, inventoryPlayer, EnumHand.MAIN_HAND);
    }

    @Override
    public String getGuiID() {
        return "";
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public void closeInventory(EntityPlayer player) {
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        return 2147483647;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Collection<ItemStack> collect() {
        HashMap<ItemKey, ItemStack> stacks = new HashMap<ItemKey, ItemStack>();
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            ItemKey item = new ItemKey(stack);
            if (stacks.containsKey(item)) {
                stacks.get(item).grow(stack.getCount());
            } else {
                stacks.put(item, stack.copy());
            }
        }
        return stacks.values();
    }

    private void writeItemsTo(NBTTagCompound compound) {
        NBTTagList items = new NBTTagList();
        compound.setTag("items", items);
        for (ItemStack stack : collect()) {
            if (stack.isEmpty()) {
                continue;
            }
            NBTTagCompound tag = stack.serializeNBT();
            items.appendTag(tag);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        writeItemsTo(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        if (!compound.hasKey("items", NBT.TAG_LIST)) {
            return;
        }
        this.stacks.clear();
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            this.stacks.set(i, stack);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        handleUpdateTag(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagCompound compound = super.writeToNBT(tag);
        writeItemsTo(compound);
        return compound;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        this.stacks.set(slot, item);
    }

    @Override
    public void markDirty() {
    }
}
