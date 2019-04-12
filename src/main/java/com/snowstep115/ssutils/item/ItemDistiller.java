package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockDistiller;

public class ItemDistiller extends ItemBlockBase {
    private static final String NAME = "distiller";

    public ItemDistiller() {
        super(new BlockDistiller(NAME), NAME);
    }
}
