package com.snowstep115.ssutils.world;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class SnowWorldProvider extends WorldProvider {
    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0f;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new SnowChunkGenerator(this.world);
    }

    @Override
    public DimensionType getDimensionType() {
        return SnowStepUtils.SNOW_DIMENSION;
    }

    @Override
    protected void init() {
        this.biomeProvider = new SnowBiomeProvider();
        this.doesWaterVaporize = false;
        this.hasSkyLight = true;
        for (int i = 0; i < this.lightBrightnessTable.length; i++) {
            this.lightBrightnessTable[i] = 1f;
        }
    }
}
