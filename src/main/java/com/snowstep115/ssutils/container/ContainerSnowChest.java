package com.snowstep115.ssutils.container;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerSnowChest extends Container {
    private int blocked = -1;
    private boolean closed = true;

    public ContainerSnowChest(IInventory snowChestInventory, InventoryPlayer inventoryPlayer, EnumHand hand) {
        // SnowChest
        int yOffset = 8;
        for (int y = 0; y < 10; y++, yOffset += 18) {
            for (int x = 0; x < 13; x++) {
                addSlotToContainer(new Slot(snowChestInventory, x + y * 13, 12 + x * 18, yOffset));
            }
        }

        // Hotbar
        yOffset = 196;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 3; x++) {
                Slot slot = new Slot(inventoryPlayer, x + y * 3, 12 + x * 18, yOffset) {
                    @Override
                    public boolean canTakeStack(EntityPlayer player) {
                        return slotNumber != blocked;
                    }
                };
                addSlotToContainer(slot);
                if (hand == EnumHand.MAIN_HAND && x + y * 3 == inventoryPlayer.currentItem) {
                    this.blocked = slot.slotNumber;
                }
            }
        }

        // Inventory
        yOffset = 196;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, 9 + x + y * 9, 84 + x * 18, yOffset));
            }
        }
    }

    public String getName() {
        return I18n.format("item.ssutils.snowchest.name");
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        if (this.closed) {
            this.closed = false;
        } else {
            IInventory snowChestInventory = getSlot(0).inventory;
            snowChestInventory.closeInventory(player);
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (index < 130) {
            IInventory snowChestInventory = getSlot(0).inventory;
            ItemStack stack = snowChestInventory.getStackInSlot(index);
            if (mergeItemStack(stack, 130, 166, false)) {
                snowChestInventory.removeStackFromSlot(index);
                snowChestInventory.markDirty();
            }
        } else {
            IInventory inventory = getSlot(130).inventory;
            ItemStack stack = inventory.getStackInSlot(index - 130);
            if (mergeItemStack(stack, 0, 130, false)) {
                inventory.removeStackFromSlot(index - 130);
                inventory.markDirty();
            }
        }
        return ItemStack.EMPTY;
    }
}
