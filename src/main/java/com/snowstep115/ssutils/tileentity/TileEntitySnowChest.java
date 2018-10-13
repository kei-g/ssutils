package com.snowstep115.ssutils.tileentity;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.block.BlockSnowChest;
import com.snowstep115.ssutils.container.ContainerSnowChest;
import com.snowstep115.ssutils.network.MessageSnowChestSync;
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
    private final NonNullList<ItemStack> items = NonNullList.<ItemStack>withSize(13 * 9, ItemStack.EMPTY);

    private void fillEmpty() {
        this.empty = true;
        for (int i = 0; i < this.items.size(); i++) {
            this.items.set(i, ItemStack.EMPTY);
        }
    }

    public TileEntitySnowChest() {
        fillEmpty();
    }

    public void receiveSyncMessage(NonNullList<ItemStack> items) {
        boolean empty = true;
        for (int i = 0; i < this.items.size() && i < items.size(); i++) {
            ItemStack item = items.get(i);
            this.items.set(i, item != null ? item : ItemStack.EMPTY);
            if (!this.items.get(i).isEmpty()) {
                empty = false;
            }
        }
        this.empty = empty;
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        fillEmpty();
        if (tag.hasKey("items", NBT.TAG_LIST)) {
            NBTTagList items = tag.getTagList("items", NBT.TAG_COMPOUND);
            int count = items.tagCount();
            for (int i = 0; i < count; i++) {
                NBTTagCompound item = items.getCompoundTagAt(i);
                ItemStack stack = new ItemStack(item);
                this.items.set(i, stack != null ? stack : ItemStack.EMPTY);
                if (!this.items.get(i).isEmpty()) {
                    this.empty = false;
                }
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag = super.writeToNBT(tag);
        NBTTagList items = new NBTTagList();
        for (ItemStack item : this.items) {
            items.appendTag(item.serializeNBT());
        }
        if (0 < items.tagCount()) {
            tag.setTag("items", items);
        }
        return tag;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerSnowChest(this, inventoryPlayer);
    }

    public String getGuiID() {
        return SnowStepUtils.MODID + ":snowchest";
    }

    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public String getName() {
        return "";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getSizeInventory() {
        return this.items.size();
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        if (this.empty && !item.isEmpty()) {
            this.empty = false;
        }
        this.items.set(slot, item);
    }

    public void markDirty() {
        super.markDirty();
        boolean empty = true;
        for (ItemStack item : this.items) {
            if (!item.isEmpty()) {
                empty = false;
            }
        }
        this.empty = empty;
        MessageSnowChestSync msg = new MessageSnowChestSync(this, this.items);
        TargetPoint tp = new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128);
        SnowStepUtils.PACKET_HANDLER.sendToAllAround(msg, tp);
    }
}
