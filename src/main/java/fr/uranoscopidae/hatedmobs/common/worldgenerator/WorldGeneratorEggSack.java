package fr.uranoscopidae.hatedmobs.common.worldgenerator;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
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

public class WorldGeneratorEggSack implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        WorldServer worldserver = (WorldServer)world;

        if(random.nextInt(32) != 0)
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

        world.setBlockState(blockPos, HatedMobs.EGG_SACK.getDefaultState());

        LootTableManager lootTableManager = world.getLootTableManager();
        LootTable eggSackLootTable = lootTableManager.getLootTableFromLocation(new ResourceLocation(HatedMobs.MODID, "egg_sack"));
        LootContext.Builder builder = new LootContext.Builder(worldserver);
        TileEntityEggSack eggSack = (TileEntityEggSack)world.getTileEntity(blockPos);
        eggSackLootTable.fillInventory(eggSack, new Random(), builder.build());

        blockPos.release();
    }
}
