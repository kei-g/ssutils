package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockSnowChest;
import java.lang.reflect.InvocationTargetException;

public class ItemSnowChest extends ItemBlockGeneric<BlockSnowChest> {
    public ItemSnowChest()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("snowchest", BlockSnowChest.class);
    }
}
