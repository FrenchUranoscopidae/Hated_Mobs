package fr.uranoscopidae.hatedmobs.common.worldgenerator;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityWaspNest;
import net.minecraft.block.BlockLog;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorWaspNest implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        for (int i = 0; i < 10; i++)
        {
            WorldServer worldserver = (WorldServer)world;

            if(random.nextInt(10) != 0)
            {
                continue;
            }
            int x = random.nextInt(14) + 1;
            int z = random.nextInt(14) + 1;
            int y = random.nextInt(80 - 64) + 64;
            int count = 0;

            BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain(x + chunkX * 16, y, z + chunkZ * 16);

            for (EnumFacing facing : EnumFacing.values())
            {
                if(world.isSideSolid(blockPos.offset(facing), facing.getOpposite()) && !world.isAirBlock(blockPos.offset(facing)))
                {
                    if(world.getBlockState(blockPos.offset(facing)).getBlock() instanceof BlockLog)
                    {
                        count++;
                    }
                    count++;
                }
            }

            if(count < 2 || count > 4)
            {
                blockPos.release();
                continue;
            }

            world.setBlockState(blockPos, HatedMobs.WASP_NEST.getDefaultState());

            TileEntityWaspNest waspNest = (TileEntityWaspNest) world.getTileEntity(blockPos);

            blockPos.release();
        }
    }
}