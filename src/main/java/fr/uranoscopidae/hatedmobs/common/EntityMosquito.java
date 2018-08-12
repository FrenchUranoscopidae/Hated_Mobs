package fr.uranoscopidae.hatedmobs.common;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMosquito extends EntityMob
{
    private PathFinder pathFinder;
    public EntityMosquito(World world)
    {
        super(new MosquitoWorldWrapper(world));
        pathFinder = new PathFinder(new WalkNodeProcessor());
        setSize(2f/16f, 2f/16f);
        this.experienceValue = 1;
    }

    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1);
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source)
    {
        Entity attacker = source.getTrueSource();

        if(attacker instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)attacker;
            ItemStack item = player.getHeldItemMainhand();

            return !(item.getItem() instanceof ItemSwatter);
        }
        return super.isEntityInvulnerable(source);
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1, false));
        this.targetTasks.addTask(6, new EntityAIFindEntityNearestPlayer(this));
    }

    @Override
    public boolean isPreventingPlayerRest(EntityPlayer playerIn)
    {
        return false;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn)
    {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {

    }
}
