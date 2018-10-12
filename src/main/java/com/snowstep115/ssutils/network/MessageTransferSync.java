package com.snowstep115.ssutils.network;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntityTransfer;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTransferSync implements IMessage {
    private int dimension;
    private BlockPos downstream;
    private BlockPos pos;
    private NonNullList<ItemStack> stacks;
    private BlockPos upstream;

    public MessageTransferSync(TileEntityTransfer tile, NonNullList<ItemStack> stacks) {
        this.dimension = tile.getWorld().provider.getDimension();
        this.downstream = tile.getDownStream();
        this.pos = tile.getPos();
        this.stacks = stacks;
        this.upstream = tile.getUpStream();
    }

    public MessageTransferSync() {
    }

    private static BlockPos readBlockPos(ByteBuf buf) {
        if (buf.readByte() == 0) {
            return null;
        }
        return new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
    }

    private static void writeBlockPos(ByteBuf buf, BlockPos pos) {
        buf.writeByte(pos == null ? 0 : 1);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dimension = buf.readInt();
        this.pos = readBlockPos(buf);
        int count = buf.readInt();
        if (count == 0) {
            this.stacks = null;
        } else {
            this.stacks = NonNullList.<ItemStack>withSize(count, ItemStack.EMPTY);
            for (int i = 0; i < count; i++) {
                ItemStack stack = ByteBufUtils.readItemStack(buf);
                this.stacks.set(i, stack);
            }
        }
        this.downstream = readBlockPos(buf);
        this.upstream = readBlockPos(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.dimension);
        writeBlockPos(buf, this.pos);
        if (this.stacks == null) {
            buf.writeInt(0);
        } else {
            buf.writeInt(this.stacks.size());
            for (ItemStack stack : this.stacks) {
                ByteBufUtils.writeItemStack(buf, stack);
            }
        }
        writeBlockPos(buf, this.downstream);
        writeBlockPos(buf, this.upstream);
    }

    public static class Handler implements IMessageHandler<MessageTransferSync, IMessage> {
        @Override
        public IMessage onMessage(MessageTransferSync message, MessageContext ctx) {
            World world = SnowStepUtils.PROXY.getClientWorld();
            if (world != null) {
                TileEntity tile = world.getTileEntity(message.pos);
                if (tile instanceof TileEntityTransfer) {
                    ((TileEntityTransfer) tile).receiveSyncMessage(message.stacks, message.downstream,
                            message.upstream);
                }
            }
            return null;
        }
    }
}
