package fr.uranoscopidae.hatedmobs.common.blocks;

import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import javafx.beans.property.IntegerProperty;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BlockSpiderInfestedLeaves extends Block implements IShearable
{
    public static final IntegerProperty SPIDER_COUNT = net.minecraft.state.IntegerProperty.create("spider_count", 0, 2);
    public static final TextComponentTranslator DESCRIPTION = new TextComponentTranslator(HatedMobs.MODID + ".infested_leaves_description");

    public BlockSpiderInfestedLeaves()
    {
        super(Material.LEAVES);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "spider_infested_leaves"));
        setUnlocalizedName("spider_infested_leaves");
        setHardness(0.2F);
        this.setLightOpacity(1);
        this.setTickRandomly(true);
        this.setSoundType(SoundType.PLANT);
        setDefaultState(blockState.getBaseState().withProperty(SPIDER_COUNT, 0));
    }

    @OnlyIn(Dist.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (worldIn.isRainingAt(pos.up()) && !worldIn.getBlockState(pos.down()).isTopSolid() && rand.nextInt(15) == 1)
        {
            double d0 = (double)((float)pos.getX() + rand.nextFloat());
            double d1 = (double)pos.getY() - 0.05D;
            double d2 = (double)((float)pos.getZ() + rand.nextFloat());
            worldIn.addParticle(ParticleType.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    public boolean causesSuffocation(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

    @Override public boolean isLeaves(IBlockState state, IBlockAccess world, BlockPos pos){ return true; }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
    {
        return Collections.singletonList(new ItemStack(Item.getItemFromBlock(this)));
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics && blockAccess.getBlockState(pos.offset(side)).getBlock() == this ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, SPIDER_COUNT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(SPIDER_COUNT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(SPIDER_COUNT);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(DESCRIPTION.getUnformattedText());
    }

    public int quantityDropped(Random random)
    {
        return 0;
    }
}
