package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorSpiderLeaves implements IWorldGenerator
{
    WorldGenSpiderLeaves leafGenerator = new WorldGenSpiderLeaves();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        int attempts = 3;
        BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain();
        for (int i = 0; i < attempts; i++)
        {
            int x = random.nextInt(16);
            int z = random.nextInt(16);
            int y = world.getHeight(x + chunkX * 16, z + chunkZ * 16);

            blockPos.setPos(x + chunkX * 16, y, z + chunkZ * 16);

            Biome biome = world.getBiome(blockPos);

            if(!world.isSideSolid(blockPos.down(), EnumFacing.UP))
            {
                blockPos.release();
                return;
            }

            if(biome == Biomes.BIRCH_FOREST || biome == Biomes.BIRCH_FOREST_HILLS || biome == Biomes.FOREST
                    || biome == Biomes.FOREST_HILLS || biome == Biomes.MUTATED_BIRCH_FOREST
                    || biome == Biomes.MUTATED_BIRCH_FOREST_HILLS || biome == Biomes.MUTATED_FOREST
                    || biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.COLD_TAIGA
                    || biome == Biomes.COLD_TAIGA_HILLS || biome == Biomes.REDWOOD_TAIGA
                    || biome == Biomes.MUTATED_REDWOOD_TAIGA_HILLS || biome == Biomes.MUTATED_REDWOOD_TAIGA
                    || biome == Biomes.MUTATED_TAIGA || biome == Biomes.MUTATED_TAIGA_COLD
                    || biome == Biomes.MUTATED_ROOFED_FOREST || biome == Biomes.ROOFED_FOREST || biome == Biomes.SWAMPLAND
                    || biome == Biomes.MUTATED_SWAMPLAND)
            {
                genStandardOre2(world, blockPos, random, leafGenerator, 0, 2);
            }
        }
        blockPos.release();
    }

    protected void genStandardOre2(World worldIn, BlockPos chunkPos,Random random, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < 5; i++)
        {
            BlockPos blockpos = chunkPos.add(random.nextInt(spread), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(spread));
            generator.generate(worldIn, random, blockpos);
        }
    }
}
