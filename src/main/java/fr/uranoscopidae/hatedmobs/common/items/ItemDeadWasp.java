package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemDeadWasp extends Item
{
    public ItemDeadWasp()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_wasp"));
        setUnlocalizedName("dead_wasp");
        setCreativeTab(HatedMobs.TAB);
    }
}
