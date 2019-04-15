package com.snowstep115.ssutils.tileentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import com.snowstep115.ssutils.ModItems;
import com.snowstep115.ssutils.container.ContainerBankNull;
import com.snowstep115.ssutils.util.GrowableItemStackList;
import com.snowstep115.ssutils.util.ItemKey;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityBankNull extends TileEntityLockableLoot {
    private final ArrayList<ContainerBankNull> containers = new ArrayList<ContainerBankNull>();
    private final GrowableItemStackList stacks = new GrowableItemStackList(54);

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        ContainerBankNull container = new ContainerBankNull(this, inventoryPlayer, EnumHand.MAIN_HAND);
        this.containers.add(container);
        return container;
    }

    public void onContainerClosed(ContainerBankNull container) {
        this.containers.remove(container);
    }

    @Override
    public String getGuiID() {
        return "";
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    public void closeInventory(EntityPlayer player) {
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
        return this.stacks.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<ItemStack> collect() {
        HashMap<ItemKey, Integer> order = new HashMap<ItemKey, Integer>();
        HashMap<ItemKey, ItemStack> stacks = new HashMap<ItemKey, ItemStack>();
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            ItemKey item = new ItemKey(stack);
            if (stacks.containsKey(item)) {
                stacks.get(item).grow(stack.getCount());
            } else {
                stacks.put(item, stack.copy());
                order.put(item, order.size());
            }
        }
        ArrayList<ItemStack> collection = new ArrayList<ItemStack>(stacks.values());
        Collections.sort(collection, new Comparator<ItemStack>() {
            public int compare(ItemStack stack1, ItemStack stack2) {
                ItemKey key1 = new ItemKey(stack1);
                ItemKey key2 = new ItemKey(stack2);
                return order.get(key1) - order.get(key2);
            }
        });
        return collection;
    }

    public void dropAll() {
        double x = this.pos.getX();
        double y = this.pos.getY();
        double z = this.pos.getZ();
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            this.world.spawnEntity(new EntityItem(this.world, x, y, z, stack));
        }
        this.world.spawnEntity(new EntityItem(this.world, x, y, z, new ItemStack(ModItems.BANK_NULL)));
    }

    public void spawnAsEntity() {
        ItemStack bankNull = new ItemStack(ModItems.BANK_NULL);
        NBTTagCompound compound = new NBTTagCompound();
        bankNull.setTagCompound(compound);
        writeToNBT(compound);
        this.world.spawnEntity(new EntityItem(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), bankNull));
    }

    private void writeItemsTo(NBTTagCompound compound) {
        NBTTagList items = new NBTTagList();
        compound.setTag("items", items);
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            NBTTagCompound tag = stack.serializeNBT();
            items.appendTag(tag);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        writeItemsTo(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        if (!compound.hasKey("items", NBT.TAG_LIST)) {
            return;
        }
        this.stacks.clear();
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            this.stacks.set(i, stack);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        handleUpdateTag(tag);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        NBTTagCompound compound = super.writeToNBT(tag);
        writeItemsTo(compound);
        return compound;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        this.stacks.set(slot, item);
    }

    @Override
    public void markDirty() {
        ItemStack stack = this.stacks.get(this.stacks.size() - 1);
        if (stack.isEmpty()) {
            return;
        }
        for (ContainerBankNull container : this.containers) {
            container.grow();
        }
    }
}
