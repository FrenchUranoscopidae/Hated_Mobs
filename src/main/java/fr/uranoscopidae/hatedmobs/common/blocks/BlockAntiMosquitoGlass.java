package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class BlockAntiMosquitoGlass extends BreakableBlock
{

    public BlockAntiMosquitoGlass()
    {
        super(Material.GLASS, false);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "anti_mosquito_glass"));
        setUnlocalizedName("anti_mosquito_glass");
        setSoundType(SoundType.GLASS);
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    protected boolean canSilkHarvest()
    {
        return true;
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }
}
