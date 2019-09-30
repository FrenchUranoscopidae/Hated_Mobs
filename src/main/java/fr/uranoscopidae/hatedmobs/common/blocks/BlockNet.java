package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import javafx.beans.property.BooleanProperty;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.command.impl.data.BlockDataAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.BlockStateContainer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class BlockNet extends Block
{
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty[] ALL_FACES = new BooleanProperty[] {UP, NORTH, SOUTH, WEST, EAST, DOWN};
    protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
    protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public BlockNet()
    {
        super(Material.CLOTH);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(UP, false)
                .withProperty(NORTH, false)
                .withProperty(EAST, false)
                .withProperty(SOUTH, false)
                .withProperty(WEST, false)
                .withProperty(DOWN, false)
        );
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "net"));
        setUnlocalizedName("net");
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(BlockState blockState, BlockDataAccessor worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    public AxisAlignedBB getBoundingBox(BlockState state, BlockDataAccessor source, BlockPos pos)
    {
        state = state.getActualState(source, pos);
        int i = 0;
        AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB;

        if (state.getValue(UP))
        {
            axisalignedbb = UP_AABB;
            ++i;
        }

        if (state.getValue(NORTH))
        {
            axisalignedbb = NORTH_AABB;
            ++i;
        }

        if (state.getValue(EAST))
        {
            axisalignedbb = EAST_AABB;
            ++i;
        }

        if (state.getValue(SOUTH))
        {
            axisalignedbb = SOUTH_AABB;
            ++i;
        }

        if (state.getValue(WEST))
        {
            axisalignedbb = WEST_AABB;
            ++i;
        }

        if (state.getValue(DOWN))
        {
            axisalignedbb = DOWN_AABB;
            ++i;
        }

        return i == 1 ? axisalignedbb : FULL_BLOCK_AABB;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, BlockState state, PlayerEntity playerIn, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
        // pass the event to the block behind
        BlockPos behind = pos.offset(facing.getOpposite());
        BlockState stateBehind = worldIn.getBlockState(behind);
        return stateBehind.getBlock().onBlockActivated(worldIn, behind, stateBehind, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public BlockState getActualState(BlockState state, BlockDataAccessor worldIn, BlockPos pos)
    {
        BlockPos north = pos.north();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos up = pos.up();
        BlockPos down = pos.down();
        return state
                .withProperty(NORTH, canPlaceOn(worldIn.getBlockState(north).getBlock()))
                .withProperty(EAST, canPlaceOn(worldIn.getBlockState(east).getBlock()))
                .withProperty(WEST, canPlaceOn(worldIn.getBlockState(west).getBlock()))
                .withProperty(SOUTH, canPlaceOn(worldIn.getBlockState(south).getBlock()))
                .withProperty(UP, canPlaceOn(worldIn.getBlockState(up).getBlock()))
                .withProperty(DOWN, canPlaceOn(worldIn.getBlockState(down).getBlock()));
    }

    public boolean isOpaqueCube(BlockState state)
    {
        return false;
    }

    public boolean isFullCube(BlockState state)
    {
        return false;
    }

    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, Direction side)
    {
        BlockPos neighbor = pos.offset(side.getOpposite());
        Block block = worldIn.getBlockState(neighbor).getBlock();
        return canPlaceOn(block);
    }

    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (!canPlace(pos, worldIn))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canPlace(BlockPos pos, World world)
    {
        for(Direction facing : Direction.values())
        {
            BlockPos neighbor = pos.offset(facing);
            world.getBlockState(neighbor).getBlock();

            if(canPlaceOn(world.getBlockState(neighbor).getBlock()))
            {
                return true;
            }
        }
        return false;
    }

    private boolean canPlaceOn(Block block)
    {
        return block instanceof DoorBlock
                || block instanceof TrapDoorBlock
                || block instanceof GlassBlock
                || block instanceof StainedGlassBlock
                || block instanceof PaneBlock;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ALL_FACES);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public BlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState();
    }

    @OnlyIn(Dist.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(BlockState state)
    {
        return 0;
    }

    public BlockState getStateForPlacement(World worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer)
    {
        return getDefaultState();
    }

    public static BooleanProperty getPropertyFor(Direction facing) {
        if(facing == null)
            throw new NullPointerException("facing");
        switch (facing) {
            case UP:
                return UP;

            case DOWN:
                return DOWN;

            case EAST:
                return EAST;

            case WEST:
                return WEST;

            case NORTH:
                return NORTH;

            case SOUTH:
                return SOUTH;

            default: // should never happen
                return UP;
        }
    }

}
