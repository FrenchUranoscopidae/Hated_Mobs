package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockDeadGrass extends Block
{
    public BlockDeadGrass()
    {
        super(Material.GRASS);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_grass"));
        setUnlocalizedName("dead_grass");
        setHardness(0.6F);
    }
}
