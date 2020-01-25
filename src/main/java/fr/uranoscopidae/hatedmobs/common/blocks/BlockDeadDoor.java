package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockDeadDoor extends BlockDoor
{
    public BlockDeadDoor()
    {
        super(Material.WOOD);
        setHardness(3f);
        setRegistryName(HatedMobs.MODID, "dead_door");
        setUnlocalizedName("dead_door");
        setSoundType(SoundType.WOOD);
        setCreativeTab(HatedMobs.TAB);
    }
}
