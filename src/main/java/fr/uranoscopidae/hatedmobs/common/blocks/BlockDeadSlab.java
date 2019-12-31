package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public abstract class BlockDeadSlab extends BlockSlab
{
    public static final PropertyEnum<BlockDeadSlab.Variant> VARIANT = PropertyEnum.create("variant", BlockDeadSlab.Variant.class);

    public BlockDeadSlab()
    {
        super(Material.WOOD);
        this.setSoundType(SoundType.WOOD);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "dead_slab" + (isDouble() ? "_double" : "")));
        setUnlocalizedName("dead_slab" + (isDouble() ? "_double" : ""));
        this.setCreativeTab(HatedMobs.TAB);
        this.fullBlock = this.isDouble();
        this.setLightOpacity(255);
        IBlockState iblockstate = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(iblockstate.withProperty(VARIANT, Variant.DEFAULT));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockDeadSlab.Variant.DEFAULT);

        if (!this.isDouble())
        {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }

        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, new IProperty[] {VARIANT}) : new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }

    /**
     * Returns the slab block name with the type associated with it
     */
    public String getUnlocalizedName(int meta)
    {
        return super.getUnlocalizedName();
    }

    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack)
    {
        return BlockDeadSlab.Variant.DEFAULT;
    }

    public static class Double extends BlockDeadSlab
    {
        public boolean isDouble()
        {
            return true;
        }
    }

    public static class Half extends BlockDeadSlab
    {
        public boolean isDouble()
        {
            return false;
        }
    }

    public static enum Variant implements IStringSerializable
    {
        DEFAULT;

        public String getName()
        {
            return "default";
        }
    }
}
