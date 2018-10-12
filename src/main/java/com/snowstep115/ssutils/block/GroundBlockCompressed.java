package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

public class GroundBlockCompressed extends BlockBase {
    public GroundBlockCompressed() {
        super(Material.GROUND, 0.5f);
        setSoundType(SoundType.GROUND);
    }
}
