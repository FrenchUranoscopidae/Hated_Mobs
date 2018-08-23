package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityMosquito;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
        event.getRegistry().registerAll(HatedMobs.NET, HatedMobs.WEB_BLOCK, HatedMobs.EGG_SACK);
        GameRegistry.registerTileEntity(TileEntityEggSack.class, HatedMobs.EGG_SACK.getRegistryName());
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
        event.getRegistry().registerAll(item, HatedMobs.SWATTER, webItem, eggSackItem, HatedMobs.SPIDER_EGG);
    }

    @SubscribeEvent
    public void registerEntities(RegistryEvent.Register<EntityEntry> event)
    {
        EntityEntry mosquitoEntry = EntityEntryBuilder.<EntityMosquito>create()
                .entity(EntityMosquito.class)
                .id(new ResourceLocation(HatedMobs.MODID, "hatedmobs"), 0)
                .name("hatedmobs")
                .tracker(64, 3, true)
                .factory(EntityMosquito::new)
                .egg(0, 0)
                .build();
        event.getRegistry().register(mosquitoEntry);

        EntityEntry silkSpiderEntry = EntityEntryBuilder.<EntitySilkSpider>create()
                .entity(EntitySilkSpider.class)
                .id(new ResourceLocation(HatedMobs.MODID, "silk_spider"), 1)
                .name("silk_spider")
                .tracker(64, 3, true)
                .factory(EntitySilkSpider::new)
                .egg(0x699eaf, 0x022f3a)
                .build();
        event.getRegistry().register(silkSpiderEntry);

        for(Biome biome : Biome.REGISTRY) {
            EntityRegistry.addSpawn(EntityMosquito.class, 75, 4, 6, EnumCreatureType.MONSTER, biome);
        }
        EntityRegistry.removeSpawn(EntityMosquito.class, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MUTATED_DESERT, Biomes.JUNGLE, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE, Biomes.MUTATED_JUNGLE_EDGE);
        EntityRegistry.addSpawn(EntityMosquito.class, 10, 4, 6, EnumCreatureType.MONSTER, Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.MUTATED_DESERT);
        EntityRegistry.addSpawn(EntityMosquito.class, 100, 4, 6, EnumCreatureType.MONSTER, Biomes.JUNGLE, Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.MUTATED_JUNGLE, Biomes.MUTATED_JUNGLE_EDGE);
    }
}
