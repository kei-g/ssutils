package com.snowstep115.ssutils.tileentity;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.snowstep115.ssutils.util.DistillerInfo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityDistiller extends TileEntity implements ITickable {
    private static final HashMap<String, DistillerInfo> TABLE = new HashMap<String, DistillerInfo>();

    static {
        TABLE.put("oil", new DistillerInfo("fuel_gaseous", "oil_heavy", 8, 16, 3));
        TABLE.put("oil_heavy_heat_1", new DistillerInfo("fuel_light_heat_1", "oil_dense_heat_1", 3, 4, 2));
        TABLE.put("oil_dense_heat_2", new DistillerInfo("fuel_dense_heat_2", "oil_residue_heat_2", 2, 2, 1));
    }

    private final FluidTank aboveTank = new FluidTank(2147483647);
    private final FluidTank belowTank = new FluidTank(2147483647);
    private final FluidTank inputTank = new FluidTank(2147483647);

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.aboveTank.readFromNBT(compound.getCompoundTag("AboveTank"));
        this.belowTank.readFromNBT(compound.getCompoundTag("BelowTank"));
        this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("AboveTank", this.aboveTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("BelowTank", this.belowTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("InputTank", this.inputTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (facing == EnumFacing.DOWN) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.belowTank);
        } else if (facing == EnumFacing.UP) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.aboveTank);
        } else if (facing != null) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.inputTank);
        } else {
            return null;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null;
    }

    @Override
    public void update() {
        FluidStack input = this.inputTank.drain(2, false);
        if (input == null) {
            return;
        }
        Fluid fluid = input.getFluid();
        if (fluid == null) {
            return;
        }
        String name = fluid.getName();
        if (name == null || !TABLE.containsKey(name)) {
            return;
        }
        TABLE.get(name).process(this.inputTank, (aboveFluid, belowFluid) -> {
            this.aboveTank.fill(aboveFluid, true);
            this.belowTank.fill(belowFluid, true);
        });
    }
}
