package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemSpiderCandy extends Item
{
    public  ItemSpiderCandy()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "spider_candy"));
        setUnlocalizedName("spider_candy");
        setCreativeTab(HatedMobs.TAB);
    }
}
