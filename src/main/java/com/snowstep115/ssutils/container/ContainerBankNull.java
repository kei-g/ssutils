package com.snowstep115.ssutils.container;

import java.util.Collection;
import com.snowstep115.ssutils.tileentity.TileEntityBankNull;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ContainerBankNull extends Container {
    private final TileEntityBankNull bankNull;

    public ContainerBankNull(TileEntityBankNull bankNull, InventoryPlayer inventoryPlayer, EnumHand hand) {
        this.bankNull = bankNull;
        for (int i = 0; i < 14 * 14; i++) {
            addSlotToContainer(new Slot(bankNull, i, 2 + (i % 14) * 18, 2 + (i / 14) * 18));
        }
    }

    public Collection<ItemStack> collect() {
        return this.bankNull.collect();
    }

    public String getName() {
        return I18n.format("tile.ssutils.banknull.name");
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        return ItemStack.EMPTY;
    }
}
