package fr.uranoscopidae.hatedmobs.common.entities.entityai;

import fr.uranoscopidae.hatedmobs.common.entities.EntitySlug;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAINomNomCultures extends EntityAIMoveToBlock
{
    private final int searchDistance;
    EntitySlug slug;
    private boolean wantsToNomNomStuff;

    public EntityAINomNomCultures(EntitySlug slug, double speed)
    {
        super(slug, speed, 16);
        this.slug = slug;
        this.searchDistance = 16;
    }

    @Override
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();

        if (block == Blocks.FARMLAND)
        {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            if (block instanceof BlockCrops && this.wantsToNomNomStuff)
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void updateTask()
    {
        super.updateTask();

        if(this.getIsAboveDestination())
        {
            World world = this.slug.world;
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block instanceof BlockCrops)
            {
                world.destroyBlock(blockpos, true);
            }
        }
    }

    public boolean shouldExecute()
    {
        if (this.runDelay <= 0)
        {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.slug.world, this.slug))
            {
                return false;
            }
            this.wantsToNomNomStuff = this.slug.wantsMoreFood();
        }

        if (this.runDelay > 0)
        {
            --this.runDelay;
            return false;
        }
        else
        {
            this.runDelay = 200 + this.slug.getRNG().nextInt(200);
            return this.searchForDestination();
        }
    }

    private boolean searchForDestination()
    {
        int i = this.searchDistance;
        int j = 1;
        BlockPos blockpos = new BlockPos(this.slug);

        for (int k = -searchDistance; k <= searchDistance; k++)
        {
            for (int l = 0; l < i; ++l)
            {
                for (int i1 = 0; i1 <= l; i1 = i1 > 0 ? -i1 : 1 - i1)
                {
                    for (int j1 = i1 < l && i1 > -l ? l : 0; j1 <= l; j1 = j1 > 0 ? -j1 : 1 - j1)
                    {
                        BlockPos blockpos1 = blockpos.add(i1, k, j1);

                        if (this.shouldMoveTo(this.slug.world, blockpos1))
                        {
                            this.destinationBlock = blockpos1;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}
