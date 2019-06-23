package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemRedAntQueen extends Item
{
    public ItemRedAntQueen()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "red_ant_queen"));
        setUnlocalizedName("red_ant_queen");
        setCreativeTab(HatedMobs.TAB);
    }
}
