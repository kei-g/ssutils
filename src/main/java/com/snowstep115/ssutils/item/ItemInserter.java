package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockInserter;
import java.lang.reflect.InvocationTargetException;

public class ItemInserter extends ItemBlockGeneric<BlockInserter> {
    public ItemInserter()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("inserter", BlockInserter.class);
    }
}
