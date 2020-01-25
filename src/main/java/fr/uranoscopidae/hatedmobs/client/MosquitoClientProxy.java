package fr.uranoscopidae.hatedmobs.client;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.MosquitoCommonProxy;
import fr.uranoscopidae.hatedmobs.client.renders.*;
import fr.uranoscopidae.hatedmobs.common.entities.*;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class MosquitoClientProxy extends MosquitoCommonProxy
{
    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        MinecraftForge.EVENT_BUS.register(this);
        RenderingRegistry.registerEntityRenderingHandler(EntityMosquito.class, RenderMosquito::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySilkSpider.class, RenderSilkSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGiantSpider.class, RenderGiantSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPoisonBall.class, RenderPoisonBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWasp.class, RenderWasp::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityToad.class, RenderToad::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityScorpion.class, RenderScorpion::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTamedGiantSpider.class, RenderTamedGiantSpider::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRedAnt.class, RenderRedAnt::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySlug.class, RenderSlug::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDeadBoat.class, RenderDeadBoat::new);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAntHive.class, new TileEntityAnthiveSpecialRenderer());
    }

    @Override
    public void init()
    {
        super.init();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        registerBlockModel(HatedMobs.NET);
        registerItemModel(HatedMobs.SWATTER);
        registerBlockModel(HatedMobs.WEB_BLOCK);
        registerBlockModel(HatedMobs.EGG_SACK);
        registerItemModel(HatedMobs.SPIDER_EGG);
        registerItemModel(HatedMobs.SILK_BOOTS);
        registerBlockModel(HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK);
        registerBlockModel(HatedMobs.ANTI_MOSQUITO_GLASS);
        registerBlockModel(HatedMobs.NET_DOOR);
        registerItemModel(HatedMobs.NET_DOOR_ITEM);
        registerBlockModel(HatedMobs.WASP_NEST);
        registerItemModel(HatedMobs.FROG_LEG);
        registerItemModel(HatedMobs.COOKED_FROG_LEG);
        registerItemModel(HatedMobs.GIANT_SPIDER_FANG);
        registerItemModel(HatedMobs.GIANT_SPIDER_FANG_SWORD);
        registerItemModel(HatedMobs.DEAD_MOSQUITO);
        registerItemModel(HatedMobs.DEAD_WASP);
        registerItemModel(HatedMobs.SPIDER_CANDY);
        registerBlockModel(HatedMobs.ANT_HIVE);
        registerItemModel(HatedMobs.RED_ANT_QUEEN);
        registerBlockModel(HatedMobs.DOMESTICATED_ANTHILL);
        registerItemModel(HatedMobs.BLACK_ANT_QUEEN);
        registerBlockModel(HatedMobs.DEAD_LOG);
        registerBlockModel(HatedMobs.DEAD_PLANKS);
        registerBlockModel(HatedMobs.DEAD_HALF_SLAB);
        registerBlockModel(HatedMobs.DEAD_DOUBLE_SLAB);
        registerBlockModel(HatedMobs.DEAD_STAIRS);
        registerBlockModel(HatedMobs.DEAD_FENCE);
        registerBlockModel(HatedMobs.DEAD_FENCE_GATE);
        registerBlockModel(HatedMobs.DEAD_DOOR);
        registerItemModel(HatedMobs.DEAD_DOOR_ITEM);
        registerItemModel(HatedMobs.ITEM_DEAD_BOAT);
    }

    private void registerBlockModel(Block block)
    {
        ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));
    }

    public void registerItemModel(Item item)
    {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
    }
}