package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class GraniteCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public GraniteCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("granite_compressed", RockBlockCompressed.class);
    }
}
