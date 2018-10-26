package com.snowstep115.ssutils.tileentity;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.container.ContainerTrashCan;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;

public class TileEntityTrashCan extends TileEntityLockableLoot {
    private final NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerTrashCan(this, inventoryPlayer);
    }

    @Override
    public String getGuiID() {
        return SnowStepUtils.MODID + ":trashcan";
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        // Do nothing.
    }
}
