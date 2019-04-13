package com.snowstep115.ssutils.tileentity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityTankBase extends TileEntity {
    private final Item shard;

    protected TileEntityTankBase() {
        this.shard = Item.getByNameOrId("buildcraftcore:fragile_fluid_shard");
    }

    public abstract void dropAll();

    protected void dropFluidShard(FluidTank tank) {
        if (this.shard == null) {
            return;
        }
        do {
            FluidStack fluid = tank.drain(500, true);
            if (fluid == null || fluid.amount == 0) {
                break;
            }
            ItemStack stack = new ItemStack(this.shard);
            NBTTagCompound compound = new NBTTagCompound();
            stack.setTagCompound(compound);
            NBTTagCompound fluidTag = new NBTTagCompound();
            compound.setTag("fluid", fluidTag);
            fluidTag.setString("FluidName", fluid.getFluid().getName());
            fluidTag.setInteger("Amount", fluid.amount);
            spawnEntity(stack);
        } while (true);
    }

    protected void spawnEntity(ItemStack stack) {
        this.world.spawnEntity(new EntityItem(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), stack));
    }
}
