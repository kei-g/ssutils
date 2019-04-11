package com.snowstep115.ssutils.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import invtweaks.api.container.ChestContainer;
import invtweaks.api.container.ContainerSection;
import invtweaks.api.container.ContainerSectionCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

@ChestContainer
public class ContainerSnowChest extends Container {
    private int blocked = -1;
    private boolean closed = true;

    private final ArrayList<Slot> hotbarSlots = new ArrayList<Slot>();
    private final ArrayList<Slot> inventorySlots = new ArrayList<Slot>();
    private final ArrayList<Slot> snowchestSlots = new ArrayList<Slot>();

    public ContainerSnowChest(IInventory snowChestInventory, InventoryPlayer inventoryPlayer, EnumHand hand) {
        // SnowChest
        int yOffset = 17;
        for (int y = 0; y < 10; y++, yOffset += 18) {
            for (int x = 0; x < 13; x++) {
                Slot slot = new Slot(snowChestInventory, x + y * 13, 12 + x * 18, yOffset);
                addSlotToContainer(slot);
                this.snowchestSlots.add(slot);
            }
        }

        // Hotbar
        yOffset = 199;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 3; x++) {
                Slot slot = new Slot(inventoryPlayer, x + y * 3, 12 + x * 18, yOffset) {
                    @Override
                    public boolean canTakeStack(EntityPlayer player) {
                        return slotNumber != blocked;
                    }
                };
                addSlotToContainer(slot);
                this.hotbarSlots.add(slot);
                if (hand == EnumHand.MAIN_HAND && x + y * 3 == inventoryPlayer.currentItem) {
                    this.blocked = slot.slotNumber;
                }
            }
        }

        // Inventory
        yOffset = 199;
        for (int y = 0; y < 3; y++, yOffset += 18) {
            for (int x = 0; x < 9; x++) {
                Slot slot = new Slot(inventoryPlayer, 9 + x + y * 9, 84 + x * 18, yOffset);
                addSlotToContainer(slot);
                this.inventorySlots.add(slot);
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

    @ContainerSectionCallback
    public Map<ContainerSection, List<Slot>> getSlotMap() {
        HashMap<ContainerSection, List<Slot>> map = new HashMap<ContainerSection, List<Slot>>();
        map.put(ContainerSection.CHEST, this.snowchestSlots);
        ArrayList<Slot> inventorySlots = new ArrayList<Slot>();
        inventorySlots.addAll(this.inventorySlots);
        inventorySlots.addAll(this.hotbarSlots);
        map.put(ContainerSection.INVENTORY, inventorySlots);
        return map;
    }
}
