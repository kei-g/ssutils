package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class AndesiteCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public AndesiteCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("andesite_compressed", RockBlockCompressed.class);
    }
}
