package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockBase;
import java.lang.reflect.InvocationTargetException;

public class ItemBlockGeneric<T extends BlockBase> extends ItemBlockBase {
    protected ItemBlockGeneric(String name, Class<T> c)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(c.getConstructor(String.class).newInstance(name), name);
    }
}
