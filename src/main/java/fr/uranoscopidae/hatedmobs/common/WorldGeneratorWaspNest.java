package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityWaspNest;
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
        WorldServer worldserver = (WorldServer)world;

        if(random.nextInt(100) != 0)
        {
            return;
        }
        int x = random.nextInt(16);
        int z = random.nextInt(16);
        int y = world.getHeight(x + chunkX * 16, z + chunkZ * 16);

        BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain(x + chunkX * 16, y, z + chunkZ * 16);

        if(!world.isSideSolid(blockPos.down(), EnumFacing.UP))
        {
            blockPos.release();
            return;
        }

        world.setBlockState(blockPos, HatedMobs.WASP_NEST.getDefaultState());

        TileEntityWaspNest waspNest = (TileEntityWaspNest) world.getTileEntity(blockPos);

        blockPos.release();
    }
}