package com.snowstep115.ssutils.item;

import com.snowstep115.ssutils.block.BlockChunkLoader;

public final class ItemChunkLoader extends ItemBlockBase {
    private static final String NAME = "chunkloader";

    public ItemChunkLoader() {
        super(new BlockChunkLoader(NAME), NAME);
	}
}
