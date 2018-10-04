package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockCompressed;
import java.lang.reflect.InvocationTargetException;

public class ItemBlockGeneric<T extends BlockCompressed> extends ItemBlockBase {
    protected ItemBlockGeneric(String name, Class<T> c) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super(c.newInstance(), name);
    }
}
