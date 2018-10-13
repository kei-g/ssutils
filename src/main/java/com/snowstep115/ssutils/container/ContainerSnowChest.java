package com.snowstep115.ssutils.container;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerSnowChest extends Container {
    public ContainerSnowChest(IInventory snowChestInventory, InventoryPlayer inventoryPlayer) {
        int yOffset = 8;
        for (int y = 0; y < 9; y++, yOffset += 18) {
            for (int x = 0; x < 13; x++) {
                addSlotToContainer(new Slot(snowChestInventory, x + y * 13, 12 + x * 18, yOffset));
            }
        }

        // Hotbar
        yOffset = 232;
        for (int x = 0; x < 9; x++) {
            addSlotToContainer(new Slot(inventoryPlayer, x, 48 + x * 18, yOffset));
        }

        // Inventory
        yOffset = 174;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, 9 + x + y * 9, 48 + x * 18, yOffset));
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
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        if (index < 13 * 9) {
            IInventory snowChestInventory = getSlot(0).inventory;
            ItemStack stack = snowChestInventory.getStackInSlot(index);
            if (mergeItemStack(stack, 13 * 9, 13 * 9 + 36, false)) {
                snowChestInventory.removeStackFromSlot(index);
                snowChestInventory.markDirty();
            }
        } else {
            IInventory inventory = getSlot(13 * 9).inventory;
            ItemStack stack = inventory.getStackInSlot(index - 13 * 9);
            if (mergeItemStack(stack, 0, 13 * 9, false)) {
                inventory.removeStackFromSlot(index - 13 * 9);
                inventory.markDirty();
            }
        }
        return ItemStack.EMPTY;
    }
}
