package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockExtractor;
import java.lang.reflect.InvocationTargetException;

public class ItemExtractor extends ItemBlockGeneric<BlockExtractor> {
    public ItemExtractor()
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        super("extractor", BlockExtractor.class);
    }
}
