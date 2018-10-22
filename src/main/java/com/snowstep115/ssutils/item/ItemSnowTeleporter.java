package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockSnowTeleporter;
import java.lang.reflect.InvocationTargetException;

public class ItemSnowTeleporter extends ItemBlockGeneric<BlockSnowTeleporter> {
    public ItemSnowTeleporter() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("snow_teleporter", BlockSnowTeleporter.class);
    }
}
