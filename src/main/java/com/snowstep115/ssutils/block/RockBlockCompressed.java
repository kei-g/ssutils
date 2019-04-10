package com.snowstep115.ssutils.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

public class RockBlockCompressed extends BlockBase {
    public RockBlockCompressed(String name) {
        super(Material.ROCK, name, 1.5f, SoundType.STONE);
    }
}
