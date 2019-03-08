package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemBlackAntQueen extends Item
{
    public ItemBlackAntQueen()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "black_ant_queen"));
        setUnlocalizedName("black_ant_queen");
        setCreativeTab(HatedMobs.TAB);
    }
}
