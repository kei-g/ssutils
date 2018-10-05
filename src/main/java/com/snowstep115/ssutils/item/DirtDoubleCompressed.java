package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.GroundBlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class DirtDoubleCompressed extends ItemBlockGeneric<GroundBlockCompressed> {
    public DirtDoubleCompressed() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("dirt_double_compressed", GroundBlockCompressed.class);
    }
}
