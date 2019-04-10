package com.snowstep115.ssutils.tileentity;

import com.snowstep115.ssutils.container.PseudoContainer;
import java.util.HashMap;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityInserter extends TileEntityBase implements ITickable {
    private final HashMap<BlockPos, IInventory> downstream = new HashMap<BlockPos, IInventory>();

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        read(this.downstream, compound, "downstream", (tile) -> {
            return tile instanceof IInventory;
        });
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        write(compound, this.downstream.keySet(), "downstream");
        return compound;
    }

    private boolean process(IInventory downstream, ItemStack stack) {
        PseudoContainer container = new PseudoContainer(downstream);
        if (!container.process(stack.copy())) {
            return false;
        }
        stack.setCount(0);
        downstream.markDirty();
        return true;
    }

    public boolean process(ItemStack stack) {
        for (IInventory downstream : this.downstream.values()) {
            if (process(downstream, stack)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        searchNeighbor((pos, tile) -> {
            if (tile instanceof IInventory) {
                this.downstream.put(pos, (IInventory) tile);
            } else {
                this.downstream.remove(pos);
            }
        });
    }
}
