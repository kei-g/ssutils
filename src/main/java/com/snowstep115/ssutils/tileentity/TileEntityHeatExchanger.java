package com.snowstep115.ssutils.tileentity;

import java.util.HashMap;

import javax.annotation.Nullable;

import com.snowstep115.ssutils.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityHeatExchanger extends TileEntityTankBase implements ITickable {
    private static final HashMap<String, String> COOL = new HashMap<String, String>();
    private static final HashMap<String, String> HEAT = new HashMap<String, String>();

    static {
        COOL.put("fuel_light_heat_1", "fuel_light");
        COOL.put("fuel_dense_heat_2", "fuel_dense_heat_1");
        COOL.put("fuel_dense_heat_1", "fuel_dense");
        COOL.put("oil_residue_heat_2", "oil_residue_heat_1");
        COOL.put("oil_residue_heat_1", "oil_residue");
        HEAT.put("oil_heavy", "oil_heavy_heat_1");
        HEAT.put("oil_dense_heat_1", "oil_dense_heat_2");
    }

    private final FluidTank aboveTank = new FluidTank(2147483647);
    private final FluidTank belowTank = new FluidTank(2147483647);

    @Override
    public void dropAll() {
        dropFluidShard(this.aboveTank);
        dropFluidShard(this.belowTank);
        spawnEntity(new ItemStack(ModItems.HEAT_EXCHANGER));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("AboveTank", this.aboveTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("BelowTank", this.belowTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound compound) {
        this.aboveTank.readFromNBT(compound.getCompoundTag("AboveTank"));
        this.belowTank.readFromNBT(compound.getCompoundTag("BelowTank"));
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.aboveTank.readFromNBT(compound.getCompoundTag("AboveTank"));
        this.belowTank.readFromNBT(compound.getCompoundTag("BelowTank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setTag("AboveTank", this.aboveTank.writeToNBT(new NBTTagCompound()));
        compound.setTag("BelowTank", this.belowTank.writeToNBT(new NBTTagCompound()));
        return compound;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        try {
            if (facing == EnumFacing.DOWN) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.belowTank);
            } else if (facing == EnumFacing.UP) {
                return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this.aboveTank);
            } else {
                return null;
            }
        } catch (Throwable exception) {
            return null;
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && facing != null;
    }

    private static FluidStack loadFluidStack(String name, int amount) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("FluidName", name);
        compound.setInteger("Amount", amount);
        return FluidStack.loadFluidStackFromNBT(compound);
    }

    private IFluidHandler getFluidHandler(int dx, int dz, EnumFacing facing) {
        BlockPos pos = new BlockPos(this.pos.getX() + dx, this.pos.getY(), this.pos.getZ() + dz);
        TileEntity tile = this.world.getTileEntity(pos);
        if (tile == null || !tile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
            return null;
        }
        return tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
    }

    private IFluidHandler getSideTank() {
        IFluidHandler handler = getFluidHandler(0, 1, EnumFacing.NORTH);
        if (handler != null) {
            return handler;
        }
        handler = getFluidHandler(-1, 0, EnumFacing.EAST);
        if (handler != null) {
            return handler;
        }
        handler = getFluidHandler(0, -1, EnumFacing.SOUTH);
        if (handler != null) {
            return handler;
        }
        handler = getFluidHandler(1, 0, EnumFacing.WEST);
        if (handler != null) {
            return handler;
        }
        return null;
    }

    @Override
    public void update() {
        IFluidHandler sideTank = getSideTank();
        if (sideTank == null) {
            return;
        }
        FluidStack side = sideTank.drain(1, false);
        if (side == null) {
            return;
        }
        String name = side.getFluid().getName();
        if (name.equals("water")) {
            FluidStack input = this.aboveTank.drain(1, false);
            if (input == null) {
                return;
            }
            String inputName = input.getFluid().getName();
            String outputName = COOL.get(inputName);
            if (outputName == null) {
                return;
            }
            input = this.aboveTank.drain(8000, true);
            side = sideTank.drain(input.amount, true);
            FluidStack output = loadFluidStack(outputName, side.amount);
            this.belowTank.fill(output, true);
        } else if (name.equals("lava")) {
            FluidStack input = this.belowTank.drain(1, false);
            if (input == null) {
                return;
            }
            String inputName = input.getFluid().getName();
            String outputName = HEAT.get(inputName);
            if (outputName == null) {
                return;
            }
            input = this.belowTank.drain(8000, true);
            side = sideTank.drain(input.amount, true);
            FluidStack output = loadFluidStack(outputName, side.amount);
            this.aboveTank.fill(output, true);
        }
    }
}
