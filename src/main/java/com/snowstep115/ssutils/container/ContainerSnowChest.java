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
        for (int y = 0; y < 10; y++, yOffset += 18) {
            for (int x = 0; x < 13; x++) {
                addSlotToContainer(new Slot(snowChestInventory, x + y * 13, 12 + x * 18, yOffset));
            }
        }

        // Hotbar
        yOffset = 196;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 3; x++) {
                addSlotToContainer(new Slot(inventoryPlayer, x + y * 3, 12 + x * 18, yOffset));
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
