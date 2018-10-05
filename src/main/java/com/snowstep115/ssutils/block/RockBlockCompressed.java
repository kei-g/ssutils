package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.SoundType;

public class RockBlockCompressed extends BlockCompressed {
    public RockBlockCompressed() {
        super(Material.ROCK, 1.5f);
        setSoundType(SoundType.STONE);
    }
}
