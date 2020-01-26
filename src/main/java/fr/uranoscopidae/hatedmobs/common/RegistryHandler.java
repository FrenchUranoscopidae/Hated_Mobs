package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.*;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityDomesticatedAnthill;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityWaspNest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RegistryHandler
{
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(HatedMobs.NET, HatedMobs.WEB_BLOCK, HatedMobs.EGG_SACK,
                HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK, HatedMobs.ANTI_MOSQUITO_GLASS, HatedMobs.NET_DOOR,
                HatedMobs.WASP_NEST, HatedMobs.ANT_HIVE, HatedMobs.DOMESTICATED_ANTHILL, HatedMobs.DEAD_LOG,
                HatedMobs.DEAD_PLANKS, HatedMobs.DEAD_HALF_SLAB, HatedMobs.DEAD_DOUBLE_SLAB, HatedMobs.DEAD_STAIRS,
                HatedMobs.DEAD_FENCE, HatedMobs.DEAD_FENCE_GATE, HatedMobs.DEAD_DOOR, HatedMobs.DEAD_TRAP_DOOR);
        GameRegistry.registerTileEntity(TileEntityEggSack.class, HatedMobs.EGG_SACK.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityWaspNest.class, HatedMobs.WASP_NEST.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityAntHive.class, HatedMobs.ANT_HIVE.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityDomesticatedAnthill.class, HatedMobs.DOMESTICATED_ANTHILL.getRegistryName());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
        ItemBlock item = new ItemBlock(HatedMobs.NET);
        item.setRegistryName(HatedMobs.NET.getRegistryName());
        ItemBlock webItem = new ItemBlock(HatedMobs.WEB_BLOCK);
        webItem.setRegistryName(HatedMobs.WEB_BLOCK.getRegistryName());
        ItemBlock eggSackItem = new ItemBlock(HatedMobs.EGG_SACK);
        eggSackItem.setRegistryName(HatedMobs.EGG_SACK.getRegistryName());
        ItemBlock spiderInfestedLeavesItem = new ItemBlock(HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK);
        spiderInfestedLeavesItem.setRegistryName(HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK.getRegistryName());
        ItemBlock antiMosquitoGlassItem = new ItemBlock(HatedMobs.ANTI_MOSQUITO_GLASS);
        antiMosquitoGlassItem.setRegistryName(HatedMobs.ANTI_MOSQUITO_GLASS.getRegistryName());
        ItemBlock waspNestItem = new ItemBlock(HatedMobs.WASP_NEST);
        waspNestItem.setRegistryName(HatedMobs.WASP_NEST.getRegistryName());
        ItemBlock antHiveItem = new ItemBlock(HatedMobs.ANT_HIVE);
        antHiveItem.setRegistryName(HatedMobs.ANT_HIVE.getRegistryName());
        ItemBlock domesticatedAnthillItem = new ItemBlock(HatedMobs.DOMESTICATED_ANTHILL);
        domesticatedAnthillItem.setRegistryName(HatedMobs.DOMESTICATED_ANTHILL.getRegistryName());
        ItemBlock deadLogItem = new ItemBlock(HatedMobs.DEAD_LOG);
        deadLogItem.setRegistryName(HatedMobs.DEAD_LOG.getRegistryName());
        ItemBlock deadPlanksItem = new ItemBlock(HatedMobs.DEAD_PLANKS);
        deadPlanksItem.setRegistryName(HatedMobs.DEAD_PLANKS.getRegistryName());
        ItemSlab deadSlabItem = new ItemSlab(HatedMobs.DEAD_HALF_SLAB, HatedMobs.DEAD_HALF_SLAB, HatedMobs.DEAD_DOUBLE_SLAB);
        deadSlabItem.setRegistryName(HatedMobs.DEAD_HALF_SLAB.getRegistryName());
        ItemBlock deadStairsItem = new ItemBlock(HatedMobs.DEAD_STAIRS);
        deadStairsItem.setRegistryName(HatedMobs.DEAD_STAIRS.getRegistryName());
        ItemBlock deadFenceItem = new ItemBlock(HatedMobs.DEAD_FENCE);
        deadFenceItem.setRegistryName(HatedMobs.DEAD_FENCE.getRegistryName());
        ItemBlock deadFenceGateItem = new ItemBlock(HatedMobs.DEAD_FENCE_GATE);
        deadFenceGateItem.setRegistryName(HatedMobs.DEAD_FENCE_GATE.getRegistryName());
        ItemBlock deadTrapDoorItem = new ItemBlock(HatedMobs.DEAD_TRAP_DOOR);
        deadTrapDoorItem.setRegistryName(HatedMobs.DEAD_TRAP_DOOR.getRegistryName());
        event.getRegistry().registerAll(item, HatedMobs.SWATTER, webItem, eggSackItem, HatedMobs.SPIDER_EGG,
                spiderInfestedLeavesItem, HatedMobs.SILK_BOOTS, antiMosquitoGlassItem, HatedMobs.NET_DOOR_ITEM,
                waspNestItem, HatedMobs.FROG_LEG, HatedMobs.COOKED_FROG_LEG, HatedMobs.GIANT_SPIDER_FANG,
                HatedMobs.GIANT_SPIDER_FANG_SWORD, HatedMobs.DEAD_MOSQUITO, HatedMobs.DEAD_WASP, HatedMobs.SPIDER_CANDY,
                antHiveItem, HatedMobs.RED_ANT_QUEEN, domesticatedAnthillItem, HatedMobs.BLACK_ANT_QUEEN, deadLogItem,
                deadPlanksItem, deadSlabItem, deadStairsItem, deadFenceItem, deadFenceGateItem, HatedMobs.DEAD_DOOR_ITEM,
                HatedMobs.ITEM_DEAD_BOAT, deadTrapDoorItem);
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        if(ConfigurationHandler.MOB_TOGGLE.mosquito)
        {
            EntityEntry mosquitoEntry = EntityEntryBuilder.<EntityMosquito>create()
                    .entity(EntityMosquito.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "mosquito"), 0)
                    .name("hatedmobs.mosquito")
                    .tracker(64, 3, true)
                    .factory(EntityMosquito::new)
                    .egg(0, 0)
                    .build();
            event.getRegistry().register(mosquitoEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.silkSpider)
        {
            EntityEntry silkSpiderEntry = EntityEntryBuilder.<EntitySilkSpider>create()
                    .entity(EntitySilkSpider.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "silk_spider"), 1)
                    .name("hatedmobs.silk_spider")
                    .tracker(64, 3, true)
                    .factory(EntitySilkSpider::new)
                    .build();
            event.getRegistry().register(silkSpiderEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.giantSpider)
        {
            EntityEntry giantSpiderEntry = EntityEntryBuilder.create()
                    .entity(EntityGiantSpider.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "giant_spider"), 2)
                    .name("hatedmobs.giant_spider")
                    .tracker(64, 3, true)
                    .egg(0x303030, 0x7f0000)
                    .factory(EntityGiantSpider::new)
                    .build();
            event.getRegistry().register(giantSpiderEntry);
        }

        EntityEntry poisonBallEntry = EntityEntryBuilder.create()
                .entity(EntityPoisonBall.class)
                .id(new ResourceLocation(HatedMobs.MODID, "poison_ball"), 3)
                .name("hatedmobs.poison_ball")
                .tracker(64, 3, true)
                .factory(EntityPoisonBall::new)
                .build();
        event.getRegistry().register(poisonBallEntry);

        if(ConfigurationHandler.MOB_TOGGLE.wasp)
        {
            EntityEntry waspEntry = EntityEntryBuilder.create()
                    .entity(EntityWasp.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "wasp"), 4)
                    .name("hatedmobs.wasp")
                    .tracker(64, 3, true)
                    .egg(0xffe666, 0x4d4d4d)
                    .factory(EntityWasp::new)
                    .build();
            event.getRegistry().register(waspEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.toad)
        {
            EntityEntry toadEntry = EntityEntryBuilder.create()
                    .entity(EntityToad.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "toad"), 5)
                    .name("hatedmobs.toad")
                    .tracker(64, 3, true)
                    .egg(0xFFDA6E, 0xE8650C)
                    .factory(EntityToad::new)
                    .build();
            event.getRegistry().register(toadEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.scorpion)
        {
            EntityEntry scorpionEntry = EntityEntryBuilder.create()
                    .entity(EntityScorpion.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "scorpion"), 6)
                    .name("hatedmobs.scorpion")
                    .tracker(64, 3, true)
                    .egg(0xE2E085, 0x928355)
                    .factory(EntityScorpion::new)
                    .build();
            event.getRegistry().register(scorpionEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.giantSpider)
        {
            EntityEntry tamedGiantSpiderEntry = EntityEntryBuilder.create()
                    .entity(EntityTamedGiantSpider.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "tamed_giant_spider"), 7)
                    .name("hatedmobs.tamed_giant_spider")
                    .tracker(64, 3, true)
                    .factory(EntityTamedGiantSpider::new)
                    .build();
            event.getRegistry().register(tamedGiantSpiderEntry);
        }

        if(ConfigurationHandler.MOB_TOGGLE.ant)
        {
            EntityEntry redAntEntry = EntityEntryBuilder.create()
                    .entity(EntityRedAnt.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "red_ant"), 8)
                    .name("hatedmobs.red_ant")
                    .tracker(64, 3, true)
                    .egg(0x822323, 0xff7878)
                    .factory(EntityRedAnt::new)
                    .build();
            event.getRegistry().register(redAntEntry);

            EntityEntry anthillSpawnerEntry = EntityEntryBuilder.create()
                    .entity(EntityAnthillSpawner.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "anthill_spawner"), 10)
                    .name("hatedmobs.anthill_spawner")
                    .tracker(64, 3, true)
                    .factory(EntityAnthillSpawner::new)
                    .build();
            event.getRegistry().register(anthillSpawnerEntry);


        }

        if(ConfigurationHandler.MOB_TOGGLE.slug)
        {
            EntityEntry slugEntry = EntityEntryBuilder.create()
                    .entity(EntitySlug.class)
                    .id(new ResourceLocation(HatedMobs.MODID, "slug"), 9)
                    .name("hatedmobs.slug")
                    .tracker(64, 3, true)
                    .egg(0xb04c39, 0xfa9682)
                    .factory(EntitySlug::new)
                    .build();
            event.getRegistry().register(slugEntry);
        }

        EntityEntry deadBoatEntry = EntityEntryBuilder.create()
                .entity(EntityDeadBoat.class)
                .id(new ResourceLocation(HatedMobs.MODID, "dead_boat"), 11)
                .name("hatedmobs.dead_boat")
                .tracker(64, 3, true)
                .factory(EntityDeadBoat::new)
                .build();
        event.getRegistry().register(deadBoatEntry);

        for(Biome biome : Biome.REGISTRY)
        {
            if(biome == Biomes.HELL || biome == Biomes.SKY)
            {
                continue;
            }

            if(ConfigurationHandler.MOB_TOGGLE.mosquito)
            {
                int mosquitoSpawnrate = ConfigurationHandler.MOB_FREQUENCY.mosquito.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.mosquitoDefault);
                EntityRegistry.addSpawn(EntityMosquito.class, mosquitoSpawnrate, 4, 6, EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.giantSpider)
            {
                int giantSpiderSpawnrate = ConfigurationHandler.MOB_FREQUENCY.giantSpider.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.giantSpiderDefault);
                EntityRegistry.addSpawn(EntityGiantSpider.class, giantSpiderSpawnrate, 1, 1, EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.toad)
            {
                int toadSpawnrate = ConfigurationHandler.MOB_FREQUENCY.toad.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.toadDefault);
                EntityRegistry.addSpawn(EntityToad.class, toadSpawnrate, 2, 5, EnumCreatureType.CREATURE, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.scorpion)
            {
                int scorpionSpawnrate = ConfigurationHandler.MOB_FREQUENCY.scorpion.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.scorpionDefault);
                EntityRegistry.addSpawn(EntityScorpion.class, scorpionSpawnrate, 3, 4,EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.slug)
            {
                int slugSpawnrate = ConfigurationHandler.MOB_FREQUENCY.slug.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.slugDefault);
                EntityRegistry.addSpawn(EntitySlug.class, slugSpawnrate, 2, 4, EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.ant)
            {
                int anthillSpawnerSpawnrate = ConfigurationHandler.MOB_FREQUENCY.anthillSpawner.getOrDefault(biome.getRegistryName().toString(), ConfigurationHandler.MOB_FREQUENCY.anthillSpawnerDefault);
                EntityRegistry.addSpawn(EntityAnthillSpawner.class, anthillSpawnerSpawnrate, 1, 1, EnumCreatureType.MONSTER, biome);
            }
        }

        EntitySpawnPlacementRegistry.setPlacementType(EntityToad.class, EntityLiving.SpawnPlacementType.IN_WATER);
    }

    @SubscribeEvent
    public void registerPotion(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().register(HatedMobs.INSOMNIA);
    }
}
