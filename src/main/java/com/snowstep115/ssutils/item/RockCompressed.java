package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class RockCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public RockCompressed(String name) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(name + "_compressed", RockBlockCompressed.class);
    }
}
