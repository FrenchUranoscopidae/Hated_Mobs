package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.GuiHandler;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityDomesticatedAnthill;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockDomesticatedAnthill extends Block
{

    public BlockDomesticatedAnthill()
    {
        super(Material.GLASS);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "domesticated_anthill"));
        setUnlocalizedName("domesticated_anthill");
        setHardness(0.6F);
        setSoundType(SoundType.GLASS);
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, BlockState state) {
        System.out.println("hello");
        return new TileEntityDomesticatedAnthill();
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(BlockState blockState, BlockDataAccessor blockAccess, BlockPos pos, Direction side)
    {
        return true;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(BlockDataAccessor worldIn, BlockState state, BlockPos pos, Direction face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side)
    {
        return canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return !worldIn.isAirBlock(pos.down());
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if(!canPlaceBlockAt(worldIn, pos))
        {
            worldIn.destroyBlock(pos, false);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }

        playerIn.openGui(HatedMobs.instance, GuiHandler.DOMESTICATED_ANTHILL_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
