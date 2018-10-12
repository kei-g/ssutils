package com.snowstep115.ssutils.tileentity;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.network.MessageTransferSync;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityTransfer extends TileEntityLockableLoot implements ITickable {
    private TileEntity downstream;
    private boolean empty;
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
    private TileEntity upstream;

    public BlockPos getDownStream() {
        return this.downstream != null ? this.downstream.getPos() : null;
    }

    public BlockPos getUpStream() {
        return this.upstream != null ? this.upstream.getPos() : null;
    }

    public void receiveSyncMessage(NonNullList<ItemStack> stacks, BlockPos downstream, BlockPos upstream) {
        int count = stacks.size();
        this.empty = count == 0;
        this.stacks.clear();
        if (0 < count) {
            this.stacks = NonNullList.<ItemStack>withSize(count, ItemStack.EMPTY);
            for (ItemStack stack : stacks) {
                this.stacks.add(stack);
            }
        }
        this.downstream = world.getTileEntity(downstream);
        this.upstream = world.getTileEntity(upstream);
    }

    private TileEntity readTileEntityFromNBT(NBTTagCompound compound, String key) {
        if (!compound.hasKey(key, NBT.TAG_COMPOUND)) {
            return null;
        }
        NBTTagCompound nbt = compound.getCompoundTag(key);
        BlockPos pos = NBTUtil.getPosFromTag(nbt);
        return pos != null ? world.getTileEntity(pos) : null;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.downstream = readTileEntityFromNBT(compound, "downstream");
        NBTTagCompound item = compound.getCompoundTag("item");
        ItemStack stack = new ItemStack(item);
        this.empty = stack.isEmpty();
        this.stacks.set(0, stack);
        this.upstream = readTileEntityFromNBT(compound, "upstream");
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound tag = super.writeToNBT(compound);
        NBTTagCompound item = this.stacks.get(0).serializeNBT();
        tag.setTag("item", item);
        if (this.downstream != null) {
            tag.setTag("downstream", NBTUtil.createPosTag(this.downstream.getPos()));
        }
        if (this.upstream != null) {
            tag.setTag("upstream", NBTUtil.createPosTag(this.downstream.getPos()));
        }
        return tag;
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return null;
    }

    public String getGuiID() {
        return SnowStepUtils.MODID + ":transfer";
    }

    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    public String getName() {
        return "";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public int getSizeInventory() {
        return 1;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void setInventorySlotContents(int slot, ItemStack item) {
        if (this.empty && !item.isEmpty()) {
            this.empty = false;
        }
        this.stacks.set(slot, item);
        markDirty();
    }

    public void markDirty() {
        super.markDirty();
        MessageTransferSync msg = new MessageTransferSync(this, this.stacks);
        TargetPoint tp = new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 128);
        SnowStepUtils.PACKET_HANDLER.sendToAllAround(msg, tp);
    }

    private void getInventory(ArrayList<TileEntity> found, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IInventory) {
            found.add(tile);
        }
    }

    private ArrayList<TileEntity> findInventories() {
        ArrayList<TileEntity> found = new ArrayList<TileEntity>();
        getInventory(found, new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
        getInventory(found, new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
        getInventory(found, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        getInventory(found, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
        getInventory(found, new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
        getInventory(found, new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
        return found;
    }

    public void update() {
        if (this.downstream == null) {
            ArrayList<TileEntity> found = findInventories();
            if (found.size() != 0) {
                this.downstream = found.get(0);
            }
            return;
        }
        if (this.upstream == null) {
            ArrayList<TileEntity> found = findInventories();
            if (found.contains(this.downstream)) {
                found.remove(this.downstream);
            }
            if (found.size() != 0) {
                this.upstream = found.get(0);
            }
            return;
        }
        IInventory downstream = (IInventory) this.downstream;
        IInventory upstream = (IInventory) this.upstream;
        for (int i = 0; i < upstream.getSizeInventory();) {
            ItemStack stack = upstream.getStackInSlot(i);
            if (stack.isEmpty()) {
                i++;
                continue;
            }
            for (int j = 0; j < downstream.getSizeInventory(); j++) {
                ItemStack dest = downstream.getStackInSlot(j);
                if (dest.isEmpty()) {
                    downstream.setInventorySlotContents(j, stack);
                    upstream.removeStackFromSlot(i++);
                    downstream.markDirty();
                    upstream.markDirty();
                    break;
                }
                if (!ItemStack.areItemStacksEqual(stack, dest)) {
                    continue;
                }
                //
            }
        }
    }
}
