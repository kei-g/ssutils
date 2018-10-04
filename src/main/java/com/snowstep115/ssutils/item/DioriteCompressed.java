package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class DioriteCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public DioriteCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("diorite_compressed", RockBlockCompressed.class);
    }
}
