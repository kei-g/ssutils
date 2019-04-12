package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockHeatExchanger;

public class ItemHeatExchanger extends ItemBlockBase {
    private static final String NAME = "heat_exchanger";

    public ItemHeatExchanger() {
        super(new BlockHeatExchanger(NAME), NAME);
    }
}
