package com.snowstep115.ssutils.block;

import com.snowstep115.ssutils.SnowStepUtils;
import com.snowstep115.ssutils.world.SnowTeleporter;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;

public class BlockSnowTeleporter extends BlockBase {
    public BlockSnowTeleporter(String name) {
        super(Material.ROCK, name, 3.0f, SoundType.STONE);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) {
            return false;
        }
        Entity entity = player;
        PlayerList playerList = server.getPlayerList();
        int overworld = DimensionType.OVERWORLD.getId();
        int dest = entity.dimension == overworld ? SnowStepUtils.SNOW_DIMENSION.getId() : overworld;
        Teleporter teleporter = new SnowTeleporter(server.getWorld(dest));
        if (entity instanceof EntityPlayerMP) {
            playerList.transferPlayerToDimension((EntityPlayerMP) entity, dest, teleporter);
        } else {
            int origin = entity.dimension;
            WorldServer src = server.getWorld(origin);
            WorldServer dst = server.getWorld(dest);
            entity.dimension = dest;
            world.removeEntityDangerously(entity);
            entity.isDead = false;
            playerList.transferEntityToWorld(entity, origin, src, dst, teleporter);
        }
        return true;
    }
}
