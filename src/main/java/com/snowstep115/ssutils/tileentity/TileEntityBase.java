package com.snowstep115.ssutils.tileentity;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityBase extends TileEntity {
    @SuppressWarnings("unchecked")
    protected <T> void read(HashMap<BlockPos, T> stream, NBTTagCompound compound, String key,
            Predicate<TileEntity> predicate) {
        if (this.world == null) {
            return;
        }
        stream.clear();
        if (compound.hasKey(key, NBT.TAG_LIST)) {
            NBTTagList list = compound.getTagList(key, NBT.TAG_COMPOUND);
            for (NBTBase nbt : list) {
                NBTTagCompound tag = (NBTTagCompound) nbt;
                BlockPos pos = NBTUtil.getPosFromTag(tag);
                if (pos == null) {
                    continue;
                }
                TileEntity tile = this.world.getTileEntity(pos);
                if (tile != null && predicate.test(tile)) {
                    stream.put(pos, (T) tile);
                }
            }
        }
    }

    private void process(int dx, int dy, int dz, BiConsumer<BlockPos, TileEntity> consumer) {
        BlockPos pos = new BlockPos(this.pos.getX() + dx, this.pos.getY() + dy, this.pos.getZ() + dz);
        TileEntity tile = this.world.getTileEntity(pos);
        consumer.accept(pos, tile);
    }

    protected void searchNeighbor(BiConsumer<BlockPos, TileEntity> consumer) {
        if (this.pos == null || this.world == null) {
            return;
        }
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    process(x, y, z, consumer);
                }
            }
        }
    }

    protected static void write(NBTTagCompound compound, Iterable<BlockPos> stream, String key) {
        NBTTagList list = new NBTTagList();
        for (BlockPos pos : stream) {
            NBTTagCompound tag = NBTUtil.createPosTag(pos);
            list.appendTag(tag);
        }
        if (!list.hasNoTags()) {
            compound.setTag(key, list);
        }
    }
}
