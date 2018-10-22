package com.snowstep115.ssutils.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class SnowTeleporter extends Teleporter {
    public SnowTeleporter(WorldServer world) {
        super(world);
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

    @Override
    public boolean placeInExistingPortal(Entity entity, float rotationYaw) {
        return true;
    }
}
