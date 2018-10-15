package com.snowstep115.ssutils.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class PseudoContainer extends Container {
    private final int size;

    public PseudoContainer(IInventory inventory) {
        this.size = inventory.getSizeInventory();
        for (int i = 0; i < this.size; i++) {
            addSlotToContainer(new Slot(inventory, i, 0, 0));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public boolean process(ItemStack stack) {
        return mergeItemStack(stack, 0, this.size, false);
    }
}
