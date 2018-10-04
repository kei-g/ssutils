package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class StoneCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public StoneCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("stone_compressed", RockBlockCompressed.class);
    }
}
