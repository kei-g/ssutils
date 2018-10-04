package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class CobblestoneCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public CobblestoneCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("cobblestone_compressed", RockBlockCompressed.class);
    }
}
