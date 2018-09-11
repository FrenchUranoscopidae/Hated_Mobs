package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class BlockWaspNest extends Block
{

    public BlockWaspNest()
    {
        super(Material.ROCK);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "wasp_nest"));
        setUnlocalizedName("wasp_nest");
        setHardness(1.0F);
    }
}
