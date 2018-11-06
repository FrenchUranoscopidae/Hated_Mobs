package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDeadMosquito extends Item
{
    public ItemDeadMosquito()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_mosquito"));
        setUnlocalizedName("dead_mosquito");
        setCreativeTab(HatedMobs.TAB);
    }
}
