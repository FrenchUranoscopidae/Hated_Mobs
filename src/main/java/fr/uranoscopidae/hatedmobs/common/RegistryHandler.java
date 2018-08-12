package fr.uranoscopidae.hatedmobs.common;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public class RegistryHandler
{
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(HatedMobs.NET, HatedMobs.WEB_BLOCK);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
        ItemBlock item = new ItemBlock(HatedMobs.NET);
        item.setRegistryName(HatedMobs.NET.getRegistryName());
        ItemBlock webItem = new ItemBlock(HatedMobs.WEB_BLOCK);
        webItem.setRegistryName(HatedMobs.WEB_BLOCK.getRegistryName());
        event.getRegistry().registerAll(item, HatedMobs.SWATTER, webItem);
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
    }
}
