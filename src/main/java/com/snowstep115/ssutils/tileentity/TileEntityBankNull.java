package com.snowstep115.ssutils.tileentity;

import java.util.HashMap;

import com.snowstep115.ssutils.container.ContainerBankNull;
import com.snowstep115.ssutils.init.Items;
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
import net.minecraftforge.items.IItemHandler;

public class TileEntityBankNull extends TileEntityLockableLoot {
    private final HashMap<ItemKey, Integer> indices = new HashMap<ItemKey, Integer>();
    private final NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(14 * 14, ItemStack.EMPTY);

    @Override
    public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer player) {
        return new ContainerBankNull(this, inventoryPlayer, EnumHand.MAIN_HAND);
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
    public String getName() {
        return "";
    }

    @Override
    public int getInventoryStackLimit() {
        return 2147483647;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
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
        this.world.spawnEntity(new EntityItem(this.world, x, y, z, new ItemStack(Items.BANK_NULL)));
    }

    public void spawnAsEntity() {
        ItemStack bankNull = new ItemStack(Items.BANK_NULL);
        NBTTagCompound compound = new NBTTagCompound();
        bankNull.setTagCompound(compound);
        writeToNBT(compound);
        this.world.spawnEntity(new EntityItem(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), bankNull));
    }

    private void writeContentsTo(NBTTagCompound compound) {
        NBTTagList itemsTag = new NBTTagList();
        compound.setTag("items", itemsTag);
        for (int i = 0; i < this.stacks.size(); i++) {
            ItemStack stack = this.stacks.get(i);
            if (stack.isEmpty()) {
                continue;
            }
            NBTTagCompound tag = stack.serializeNBT();
            tag.setInteger("RealCount", stack.getCount());
            itemsTag.appendTag(tag);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        writeContentsTo(compound);
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        if (!compound.hasKey("items", NBT.TAG_LIST)) {
            return;
        }
        this.indices.clear();
        this.stacks.clear();
        NBTTagList itemsTag = compound.getTagList("items", NBT.TAG_COMPOUND);
        for (int i = 0; i < itemsTag.tagCount(); i++) {
            NBTTagCompound nbt = itemsTag.getCompoundTagAt(i);
            ItemStack stack = new ItemStack(nbt);
            stack.setCount(nbt.getInteger("RealCount"));
            ItemKey key = new ItemKey(stack);
            this.indices.put(key, i);
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
        writeContentsTo(compound);
        return compound;
    }

    class StacksWrapper implements IItemHandler {
        private final HashMap<ItemKey, Integer> indices;
        private final Object lock = new Object();
        private final NonNullList<ItemStack> stacks;

        public StacksWrapper(HashMap<ItemKey, Integer> indices, NonNullList<ItemStack> stacks) {
            this.indices = indices;
            this.stacks = stacks;
        }

        @Override
        public int getSlots() {
            return this.stacks.size();
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return this.stacks.get(slot);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (stack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            ItemKey key = new ItemKey(stack);
            synchronized (this.lock) {
                if (this.indices.containsKey(key)) {
                    int index = this.indices.get(key);
                    ItemStack s = this.stacks.get(index);
                    long cnt = (long) s.getCount() + stack.getCount();
                    if (cnt <= 2147483647) {
                        if (!simulate) {
                            s.setCount((int) cnt);
                        }
                        return ItemStack.EMPTY;
                    } else {
                        if (!simulate) {
                            s.setCount(2147483647);
                        }
                        ItemStack result = stack.copy();
                        result.setCount((int) (cnt - 2147483647));
                        return result;
                    }
                } else {
                    if (!simulate) {
                        int index = this.indices.size();
                        this.indices.put(key, index);
                        this.stacks.set(index, stack.copy());
                    }
                    return ItemStack.EMPTY;
                }
            }
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            synchronized (this.lock) {
                if (slot < 0 || this.stacks.size() <= slot) {
                    return ItemStack.EMPTY;
                }
                ItemStack stack = this.stacks.get(slot);
                if (stack.isEmpty()) {
                    return ItemStack.EMPTY;
                }
                if (amount < stack.getCount()) {
                    ItemStack result = stack.copy();
                    result.setCount(amount);
                    if (!simulate) {
                        stack.shrink(amount);
                    }
                    return result;
                } else {
                    ItemStack result = stack.copy();
                    if (!simulate) {
                        this.indices.remove(new ItemKey(stack));
                        for (int i = slot + 1; i < this.stacks.size(); i++) {
                            ItemStack s = this.stacks.get(i);
                            this.stacks.set(i - 1, s);
                            if (s.isEmpty()) {
                                continue;
                            }
                            ItemKey key = new ItemKey(s);
                            this.indices.put(key, i - 1);
                        }
                        ItemStack last = this.stacks.get(this.stacks.size() - 1);
                        if (!last.isEmpty()) {
                            this.indices.remove(new ItemKey(last));
                            this.stacks.set(this.stacks.size() - 1, ItemStack.EMPTY);
                        }
                    }
                    return result;
                }
            }
        }

        @Override
        public int getSlotLimit(int slot) {
            return 2147483647;
        }
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new StacksWrapper(this.indices, this.stacks);
    }
}
