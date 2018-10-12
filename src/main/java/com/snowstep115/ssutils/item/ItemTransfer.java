package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockTransfer;
import java.lang.reflect.InvocationTargetException;

public class ItemTransfer extends ItemBlockGeneric<BlockTransfer> {
    public ItemTransfer()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("transfer", BlockTransfer.class);
    }
}
