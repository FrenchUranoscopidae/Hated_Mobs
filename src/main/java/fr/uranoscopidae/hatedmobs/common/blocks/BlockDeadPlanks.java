package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockDeadPlanks extends Block
{
    public BlockDeadPlanks()
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_planks"));
        setUnlocalizedName("dead_planks");
        this.setCreativeTab(HatedMobs.TAB);
    }
}
