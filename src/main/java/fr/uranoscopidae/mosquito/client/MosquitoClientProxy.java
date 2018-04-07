package fr.uranoscopidae.mosquito.client;

import fr.uranoscopidae.mosquito.ModMosquitos;
import fr.uranoscopidae.mosquito.MosquitoCommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class MosquitoClientProxy extends MosquitoCommonProxy
{
    @Override
    public void preInit(File configFile)
    {
        super.preInit(configFile);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void init()
    {
        super.init();
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        System.out.println("hello");
        registerBlockModel(ModMosquitos.NET);
    }

    private void registerBlockModel(Block block) {
        ModelLoader.setCustomModelResourceLocation(ItemBlock.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));
    }
}
