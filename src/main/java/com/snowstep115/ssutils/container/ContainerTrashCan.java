package com.snowstep115.ssutils.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTrashCan extends Container {
    public ContainerTrashCan(IInventory inventory, InventoryPlayer inventoryPlayer) {
        // trashcan
        addSlotToContainer(new Slot(inventory, 0, 84, 44));

        // hotbar
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventoryPlayer, x, 12 + x * 18, 160));
        }

        // inventory
        for (int y = 0, yOffset = 102; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, 9 + x + y * 9, 12 + x * 18, yOffset));
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return ItemStack.EMPTY;
    }
}
