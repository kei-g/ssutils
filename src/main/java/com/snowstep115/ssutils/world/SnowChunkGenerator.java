package com.snowstep115.ssutils.world;

import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class SnowChunkGenerator implements IChunkGenerator {
    private final World world;

    public SnowChunkGenerator(World world) {
        this.world = world;
    }

    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        Chunk chunk = new Chunk(world, chunkPrimer, x, z);
        for (int u = 0; u < 16; u++) {
            for (int v = 0; v < 16; v++) {
                chunk.setBlockState(new BlockPos(u, 0, v), Blocks.BEDROCK.getDefaultState());
            }
        }
        Biome[] abiome = world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();
        for (int i = 0; i < abiome.length; i++) {
            abyte[i] = (byte) Biome.getIdForBiome(abiome[i]);
        }
        chunk.resetRelightChecks();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
    }

    @Override
    public boolean generateStructures(Chunk chunk, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return this.world.getBiome(pos).getSpawnableList(creatureType);
    }

    @Override
    public BlockPos getNearestStructurePos(World world, String name, BlockPos pos, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunk, int x, int z) {
    }

    @Override
    public boolean isInsideStructure(World world, String name, BlockPos pos) {
        return false;
    }
}
