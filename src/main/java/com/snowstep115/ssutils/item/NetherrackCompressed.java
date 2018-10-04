package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.RockBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class NetherrackCompressed extends ItemBlockGeneric<RockBlockCompressed> {
    public NetherrackCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("netherrack_compressed", RockBlockCompressed.class);
        block.setHardness(0.5f);
    }
}
