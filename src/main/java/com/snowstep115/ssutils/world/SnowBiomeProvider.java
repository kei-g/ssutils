package com.snowstep115.ssutils.world;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;

public class SnowBiomeProvider extends BiomeProviderSingle {
    public SnowBiomeProvider() {
        super(Biome.getBiomeForId(13));
    }
}
