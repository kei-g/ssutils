package com.snowstep115.ssutils.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

public class GroundBlockCompressed extends BlockBase {
    public GroundBlockCompressed(String name) {
        super(Material.GROUND, name, 0.5f, SoundType.GROUND);
    }
}
