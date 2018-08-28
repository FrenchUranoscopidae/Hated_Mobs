package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockNetDoor extends BlockDoor
{
    public BlockNetDoor()
    {
        super(Material.CLOTH);
        setHardness(3f);
        setRegistryName(HatedMobs.MODID, "net_door");
        setUnlocalizedName("net_door");
        setSoundType(SoundType.CLOTH);
        setCreativeTab(HatedMobs.TAB);
    }
}
