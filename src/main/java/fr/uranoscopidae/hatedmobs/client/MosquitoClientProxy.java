package fr.uranoscopidae.hatedmobs.client;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.MosquitoCommonProxy;
import fr.uranoscopidae.hatedmobs.client.renders.RenderGiantSpider;
import fr.uranoscopidae.hatedmobs.client.renders.RenderMosquito;
import fr.uranoscopidae.hatedmobs.client.renders.RenderPoisonBall;
import fr.uranoscopidae.hatedmobs.client.renders.RenderSilkSpider;
import fr.uranoscopidae.hatedmobs.common.entities.EntityGiantSpider;
import fr.uranoscopidae.hatedmobs.common.entities.EntityMosquito;
import fr.uranoscopidae.hatedmobs.common.entities.EntityPoisonBall;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
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
