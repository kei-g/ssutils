package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.GroundBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class DirtCompressed extends ItemBlockGeneric<GroundBlockCompressed> {
    public DirtCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("dirt_compressed", GroundBlockCompressed.class);
    }
}
