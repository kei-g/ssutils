package com.snowstep115.ssutils.block;

import java.util.List;

import com.snowstep115.ssutils.SnowStepUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.OrderedLoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public final class BlockChunkLoader extends BlockBase {
	private Ticket ticket;

	public BlockChunkLoader(String name) {
		super(Material.PORTAL, name, 1.0f, SoundType.ANVIL);
		ForgeChunkManager.setForcedChunkLoadingCallback(SnowStepUtils.INSTANCE, new Callback());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack itemstack) {
		super.onBlockPlacedBy(world, pos, state, placer, itemstack);
		if (world.isRemote)
			return;
		this.ticket = ForgeChunkManager.requestTicket(SnowStepUtils.INSTANCE, world, Type.NORMAL);
		if (this.ticket == null) {
			SnowStepUtils.info("requestTicket failed");
			return;
		}
		ForgeChunkManager.forceChunk(this.ticket, new ChunkPos(pos));
		for (ChunkPos cp : this.ticket.getChunkList())
			SnowStepUtils.info("chunk: %s", cp);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(world, pos, state, player);
		if (this.ticket != null) {
			for (ChunkPos cp : this.ticket.getChunkList()) {
				ForgeChunkManager.unforceChunk(this.ticket, cp);
				SnowStepUtils.info("unforced chunk: %s", cp);
			}
			ForgeChunkManager.releaseTicket(this.ticket);
			this.ticket = null;
		}
	}

	public static final class Callback implements OrderedLoadingCallback {
		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world) {
			SnowStepUtils.info("ticketsLoaded(%d)", tickets.size());
			for (Ticket ticket : tickets) {
				for (ChunkPos cp : ticket.getChunkList()) {
					ForgeChunkManager.unforceChunk(ticket, cp);
					SnowStepUtils.info("unforced chunk: %s", cp);
				}
				ForgeChunkManager.releaseTicket(ticket);
			}
		}

		@Override
		public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
			SnowStepUtils.info("ticketsLoaded(%d, %d)", tickets.size(), maxTicketCount);
			return tickets;
		}
	}
}
