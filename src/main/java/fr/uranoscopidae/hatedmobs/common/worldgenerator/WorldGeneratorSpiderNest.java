package fr.uranoscopidae.hatedmobs.common.worldgenerator;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.ConfigurationHandler;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Map;
import java.util.Random;

public class WorldGeneratorSpiderNest implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if(random.nextInt(8) != 0)
        {
            return;
        }

        WorldServer worldserver = (WorldServer)world;
        MinecraftServer minecraftServer = worldserver.getMinecraftServer();
        TemplateManager templateManager = worldserver.getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(HatedMobs.MODID, "spider_nest"));

        int y = random.nextInt(64);
        int x = random.nextInt(16-9);
        int z = random.nextInt(16-9);

        int airBlocks = 0;
        BlockPos.PooledMutableBlockPos blockPos = BlockPos.PooledMutableBlockPos.retain();

        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                for (int k = 0; k < 9;k++)
                {
                    blockPos.setPos(chunkX * 16 + x + i, y + j, chunkZ * 16 + z);
                    if(world.isAirBlock(blockPos))
                    {
                        airBlocks++;
                    }
                }
            }
        }

        if(airBlocks <= 4 || airBlocks > 50)
        {
            blockPos.release();
            return;
        }

        PlacementSettings placementSettings = new PlacementSettings().setIgnoreEntities(true).setReplacedBlock(Blocks.STONE);
        blockPos.setPos(x+chunkX*16, y, z+chunkZ*16);
        template.addBlocksToWorld(world, blockPos, placementSettings);
        Map<BlockPos, String> map = template.getDataBlocks(blockPos, placementSettings);

        LootTableManager lootTableManager = world.getLootTableManager();
        LootTable eggSackLootTable = lootTableManager.getLootTableFromLocation(new ResourceLocation(HatedMobs.MODID, "egg_sack"));
        ResourceLocation chestLootTableLocation = new ResourceLocation(HatedMobs.MODID, "spider_nest_chest");
        LootTable chestLootTable = lootTableManager.getLootTableFromLocation(chestLootTableLocation);
        LootContext.Builder builder = new LootContext.Builder(worldserver);

        for (Map.Entry<BlockPos, String> entry : map.entrySet())
        {
            String function = entry.getValue();
            BlockPos blockPos1 = entry.getKey();
            switch (function)
            {
                case "egg_sack":
                    world.setBlockState(blockPos1, HatedMobs.EGG_SACK.getDefaultState(), 3);
                    TileEntityEggSack eggSack = (TileEntityEggSack)world.getTileEntity(blockPos1);
                    eggSackLootTable.fillInventory(eggSack, new Random(), builder.build());
                    break;

                case "chest":
                    world.setBlockState(blockPos1, Blocks.CHEST.getDefaultState(), 3);
                    ChestTileEntity chest = (ChestTileEntity) world.getTileEntity(blockPos1);
                    chest.setLootTable(chestLootTableLocation, random.nextLong());
                    break;

                case "giant_spider":
                    if(ConfigurationHandler.MOB_TOGGLE.giantSpider)
                    {
                        EntityGiantSpider spider = new EntityGiantSpider(world);
                        spider.setPosition(blockPos1.getX()+0.5, blockPos1.getY(), blockPos1.getZ()+0.5);
                        spider.enablePersistence();
                        world.addEntity(spider);
                        world.setBlockToAir(blockPos1);
                    }
                    break;
            }
        }

        blockPos.release();
    }
}