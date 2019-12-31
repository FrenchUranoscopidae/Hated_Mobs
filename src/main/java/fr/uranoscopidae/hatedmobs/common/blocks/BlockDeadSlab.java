package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockDeadSlab extends BlockSlab
{
    public BlockDeadSlab()
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_slab"));
        setUnlocalizedName("dead_slab");
        this.setCreativeTab(HatedMobs.TAB);
        this.fullBlock = this.isDouble();
        this.setLightOpacity(255);
    }

    @Override
    public String getUnlocalizedName(int meta)
    {
        return null;
    }

    @Override
    public boolean isDouble()
    {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty()
    {
        return null;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return null;
    }
}
