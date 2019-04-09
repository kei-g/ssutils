package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockBankNull;

public class ItemBankNull extends ItemBlockBase {
    private static final String NAME = "banknull";

    public ItemBankNull() {
        super(new BlockBankNull(NAME), NAME);
    }
}
