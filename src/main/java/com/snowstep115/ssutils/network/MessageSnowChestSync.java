package com.snowstep115.ssutils.network;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.tileentity.TileEntitySnowChest;
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

public class MessageSnowChestSync implements IMessage {
    private int dimension;
    private BlockPos pos;
    private NonNullList<ItemStack> stacks;

    public MessageSnowChestSync(TileEntitySnowChest tile, NonNullList<ItemStack> stacks) {
        this.dimension = tile.getWorld().provider.getDimension();
        this.pos = tile.getPos();
        this.stacks = stacks;
    }

    public MessageSnowChestSync() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.dimension = buf.readInt();
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
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
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.dimension);
        buf.writeInt(this.pos.getX());
        buf.writeInt(this.pos.getY());
        buf.writeInt(this.pos.getZ());
        if (this.stacks == null) {
            buf.writeInt(0);
        } else {
            buf.writeInt(this.stacks.size());
            for (ItemStack stack : this.stacks) {
                ByteBufUtils.writeItemStack(buf, stack);
            }
        }
    }

    public static class Handler implements IMessageHandler<MessageSnowChestSync, IMessage> {
        @Override
        public IMessage onMessage(MessageSnowChestSync message, MessageContext ctx) {
            World world = SnowStepUtils.PROXY.getClientWorld();
            if (world != null) {
                TileEntity tile = world.getTileEntity(message.pos);
                if (tile instanceof TileEntitySnowChest) {
                    ((TileEntitySnowChest) tile).receiveSyncMessage(message.stacks);
                }
            }
            return null;
        }
    }
}
