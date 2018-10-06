package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class RockDoubleCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public RockDoubleCompressed(String name)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(name + "_double_compressed", RockBlockCompressed.class);
    }
}
