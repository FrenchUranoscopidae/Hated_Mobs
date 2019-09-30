package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.GuiHandler;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockEggSack extends Block
{

    public BlockEggSack()
    {
        super(HatedMobs.WEB_MATERIAL);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "egg_sack"));
        setUnlocalizedName("egg_sack");
        setHardness(1.0F);
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
    public TileEntity createTileEntity(World world, BlockState state)
    {
        return new TileEntityEggSack();
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }

        playerIn.openGui(HatedMobs.instance, GuiHandler.EGG_SACK_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public void breakBlock(World worldIn, BlockPos pos, BlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }
}
