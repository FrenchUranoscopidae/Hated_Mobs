package fr.uranoscopidae.mosquito.common;

import fr.uranoscopidae.mosquito.ModMosquitos;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryHandler
{
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(ModMosquitos.NET);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event)
    {
        ItemBlock item = new ItemBlock(ModMosquitos.NET);
        item.setRegistryName(ModMosquitos.NET.getRegistryName());
        event.getRegistry().registerAll(item);
    }
}
