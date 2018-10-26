package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockTrashCan;

public class ItemTrashCan extends ItemBlockBase {
    private static final String NAME = "trashcan";

    public ItemTrashCan() {
        super(new BlockTrashCan(NAME), NAME);
    }
}
