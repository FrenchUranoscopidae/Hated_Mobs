package fr.uranoscopidae.hatedmobs.common.worldgenerator;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGeneratorAnthill implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        WorldServer worldserver = (WorldServer)world;

        if(random.nextInt(40) != 0)
        {
            return;
        }
        int x = random.nextInt(13) + 1;
        int z = random.nextInt(13) + 1;
        int y = world.getHeight(x + chunkX * 16, z + chunkZ * 16);

        BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain(x + chunkX * 16, y, z + chunkZ * 16);

        if(!world.isSideSolid(blockPos.down(), Direction.UP))
        {
            blockPos.release();
            return;
        }

        world.setBlockState(blockPos, HatedMobs.ANT_HIVE.getDefaultState());

        TileEntityAntHive antHive = (TileEntityAntHive) world.getTileEntity(blockPos);

        blockPos.release();
    }
}
