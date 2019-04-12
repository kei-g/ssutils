package com.snowstep115.ssutils.util;

import java.util.function.BiConsumer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class DistillerInfo {
    private final String aboveName;
    private final String belowName;
    private final int drain;
    private final int fillAbove;
    private final int fillBelow;

    public DistillerInfo(String aboveName, String belowName, int drain, int fillAbove, int fillBelow) {
        this.aboveName = aboveName;
        this.belowName = belowName;
        this.drain = drain;
        this.fillAbove = fillAbove;
        this.fillBelow = fillBelow;
    }

    private static FluidStack loadFluidStack(String name, int amount) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("FluidName", name);
        compound.setInteger("Amount", amount);
        return FluidStack.loadFluidStackFromNBT(compound);
    }

    public void process(FluidTank tank, BiConsumer<FluidStack, FluidStack> consumer) {
        FluidStack stack = tank.drain(this.drain * 1000, true);
        if (stack.amount < this.drain) {
            tank.fill(stack, true);
            return;
        }
        int coeff = stack.amount / this.drain;
        stack.amount %= this.drain;
        tank.fill(stack, true);
        FluidStack t = loadFluidStack(this.aboveName, this.fillAbove * coeff);
        FluidStack u = loadFluidStack(this.belowName, this.fillBelow * coeff);
        consumer.accept(t, u);
    }
}
