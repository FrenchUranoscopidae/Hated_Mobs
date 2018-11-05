package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemGiantSpiderFang extends Item
{
    public  ItemGiantSpiderFang()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "giant_spider_fang"));
        setUnlocalizedName("giant_spider_fang");
        setCreativeTab(HatedMobs.TAB);
    }
}
