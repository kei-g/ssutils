package com.snowstep115.ssutils.tileentity;

import java.util.HashMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityExtractor extends TileEntityBase implements ITickable {
    private final HashMap<BlockPos, TileEntityInserter> downstream = new HashMap<BlockPos, TileEntityInserter>();
    private final HashMap<BlockPos, IInventory> upstream = new HashMap<BlockPos, IInventory>();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        read(this.downstream, compound, "downstream", (tile) -> {
            return tile instanceof TileEntityInserter;
        });
        read(this.upstream, compound, "upstream", (tile) -> {
            return tile instanceof IInventory;
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        write(compound, this.downstream.keySet(), "downstream");
        write(compound, this.upstream.keySet(), "upstream");
        return compound;
    }

    private boolean process(ItemStack stack) {
        for (TileEntityInserter downstream : this.downstream.values()) {
            if (downstream.process(stack)) {
                return true;
            }
        }
        return false;
    }

    private void process(IInventory upstream) {
        for (int i = 0; i < upstream.getSizeInventory(); i++) {
            ItemStack stack = upstream.getStackInSlot(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (process(stack)) {
                upstream.markDirty();
                break;
            }
        }
    }

    @Override
    public void update() {
        searchNeighbor((pos, tile) -> {
            if (tile instanceof TileEntityInserter) {
                this.downstream.put(pos, (TileEntityInserter) tile);
                this.upstream.remove(pos);
            } else if (tile instanceof IInventory) {
                this.downstream.remove(pos);
                this.upstream.put(pos, (IInventory) tile);
            } else {
                this.downstream.remove(pos);
                this.upstream.remove(pos);
            }
        });
        for (IInventory upstream : this.upstream.values()) {
            process(upstream);
        }
    }
}
