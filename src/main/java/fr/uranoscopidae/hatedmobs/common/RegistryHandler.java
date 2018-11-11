package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.*;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityWaspNest;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDoor;
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
        event.getRegistry().registerAll(HatedMobs.NET, HatedMobs.WEB_BLOCK, HatedMobs.EGG_SACK, HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK, HatedMobs.ANTI_MOSQUITO_GLASS, HatedMobs.NET_DOOR, HatedMobs.WASP_NEST);
        GameRegistry.registerTileEntity(TileEntityEggSack.class, HatedMobs.EGG_SACK.getRegistryName());
        GameRegistry.registerTileEntity(TileEntityWaspNest.class, HatedMobs.WASP_NEST.getRegistryName());
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
        event.getRegistry().registerAll(item, HatedMobs.SWATTER, webItem, eggSackItem, HatedMobs.SPIDER_EGG,
                spiderInfestedLeavesItem, HatedMobs.SILK_BOOTS, antiMosquitoGlassItem, HatedMobs.NET_DOOR_ITEM,
                waspNestItem, HatedMobs.FROG_LEG, HatedMobs.COOKED_FROG_LEG, HatedMobs.GIANT_SPIDER_FANG,
                HatedMobs.GIANT_SPIDER_FANG_SWORD, HatedMobs.DEAD_MOSQUITO, HatedMobs.DEAD_WASP);
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

        for(Biome biome : Biome.REGISTRY)
        {
            if(ConfigurationHandler.MOB_TOGGLE.mosquito)
            {
                EntityRegistry.addSpawn(EntityMosquito.class, 75, 4, 6, EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.giantSpider)
            {
                EntityRegistry.addSpawn(EntityGiantSpider.class, 1, 1, 1, EnumCreatureType.MONSTER, biome);
            }

            if(ConfigurationHandler.MOB_TOGGLE.toad)
            {
                EntityRegistry.addSpawn(EntityToad.class, 100, 2, 5, EnumCreatureType.CREATURE, biome);
            }
        }

        EntitySpawnPlacementRegistry.setPlacementType(EntityToad.class, EntityLiving.SpawnPlacementType.IN_WATER);

        if(ConfigurationHandler.MOB_TOGGLE.mosquito)
        {
            EntityRegistry.removeSpawn(EntityMosquito.class, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MUTATED_DESERT, Biomes.JUNGLE, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE, Biomes.MUTATED_JUNGLE_EDGE);
            EntityRegistry.addSpawn(EntityMosquito.class, 10, 4, 6, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MUTATED_DESERT);
            EntityRegistry.addSpawn(EntityMosquito.class, 100, 4, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE, Biomes.MUTATED_JUNGLE_EDGE);
        }
    }

    @SubscribeEvent
    public void registerPotion(RegistryEvent.Register<Potion> event)
    {
        event.getRegistry().register(HatedMobs.INSOMNIA);
    }
}
