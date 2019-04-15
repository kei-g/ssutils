package com.snowstep115.ssutils.container;

import com.snowstep115.ssutils.tileentity.TileEntityBankNull;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ContainerBankNull extends Container {
    public ContainerBankNull(TileEntityBankNull bankNull, InventoryPlayer inventoryPlayer, EnumHand hand) {
        for (int i = 0; i < 14 * 14; i++) {
            addSlotToContainer(new SlotBankNull(bankNull, i, 2 + (i % 14) * 18, 2 + (i / 14) * 18));
        }
    }

    public String getName() {
        return I18n.format("tile.ssutils.banknull.name");
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
