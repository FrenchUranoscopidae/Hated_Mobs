package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.block.*;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockAntHive extends Block
{
    public  BlockAntHive()
    {
        super(HatedMobs.WEB_MATERIAL);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "ant_hive"));
        setUnlocalizedName("ant_hive");
        setHardness(0.6F);
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
        return new TileEntityAntHive();
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
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, BlockDataAccessor world, BlockPos pos, BlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);
        drops.add(new ItemStack(HatedMobs.RED_ANT_QUEEN));
        drops.add(new ItemStack(HatedMobs.BLACK_ANT_QUEEN));
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int fortune)
    {
        return super.getItemDropped(state, rand, fortune);
    }

    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, BlockState state)
    {
        super.onPlayerDestroy(worldIn, pos, state);

        if(worldIn.rand.nextInt(4) == 3)
        {
            int newX = (pos.getX()/16) * 16 + worldIn.rand.nextInt(16);
            int newZ = (pos.getZ()/16) * 16 + worldIn.rand.nextInt(16);
            int newY = worldIn.getHeight(newX, newZ);
            BlockPos blockPos = new BlockPos(newX, newY, newZ);
            Block blockUnder = worldIn.getBlockState(blockPos.down()).getBlock();
            if (blockUnder instanceof TallGrassBlock)
            {
                worldIn.setBlockState(blockPos.down(), this.getDefaultState());
            }

            else if(blockUnder instanceof BlockDirt || blockUnder instanceof SandBlock || blockUnder instanceof BlockSandStone || blockUnder instanceof GrassBlock)
            {
                worldIn.setBlockState(blockPos, this.getDefaultState());
            }
        }
    }
}