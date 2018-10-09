package com.snowstep115.ssutils.tileentity;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.BlockSnowChest;
import com.snowstep115.ssutils.network.MessageSnowChestSync;
import com.snowstep115.ssutils.util.GrowableItemStackList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntitySnowChest extends TileEntityLockableLoot {
    private boolean empty;
    private GrowableItemStackList items = new GrowableItemStackList(1);

    public void receiveSyncMessage(NonNullList<ItemStack> items) {
        int count = items.size();
        this.empty = count == 0;
        this.items.clear();
        if (0 < count) {
            this.items = new GrowableItemStackList(count);
            for (ItemStack item : items) {
                this.items.add(item);
            }
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.empty = true;
        if (tag.hasKey("items", NBT.TAG_LIST)) {
            NBTTagList items = tag.getTagList("items", NBT.TAG_COMPOUND);
            int count = items.tagCount();
            this.items = new GrowableItemStackList(count);
            for (int i = 0; i < count; i++) {
                NBTTagCompound item = items.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(item);
                if (stack.isEmpty()) {
                    continue;
                }
                this.empty = false;
                this.items.add(stack);
            }
        } else {
            this.items = new GrowableItemStackList(1);
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        NBTTagList items = new NBTTagList();
        for (ItemStack item : this.items) {
            if (item.isEmpty()) {
                continue;
            }
            items.appendTag(item.serializeNBT());
        }
        if (0 < items.tagCount()) {
            tag.setTag("items", items);
        }
        return tag;
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    public String getGuiID() {
        return SnowStepUtils.MODID + ":snowchest";
    }

    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    public String getName() {
        return "";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getSizeInventory() {
        return this.items.size() + 1;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        if (this.empty && !item.isEmpty()) {
            this.empty = false;
        }
        this.items.set(slot, item);
        markDirty();
    }

    public void markDirty() {
        super.markDirty();
        MessageSnowChestSync msg = new MessageSnowChestSync(this, this.items);
        TargetPoint tp = new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128);
        SnowStepUtils.PACKET_HANDLER.sendToAllAround(msg, tp);
    }
}
