package fr.uranoscopidae.hatedmobs.common.entities;

import fr.uranoscopidae.hatedmobs.common.items.ItemSwatter;
import fr.uranoscopidae.hatedmobs.common.MosquitoWorldWrapper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityMosquito extends EntityMob
{
    public EntityMosquito(World world)
    {
        super(new MosquitoWorldWrapper(world));
        setSize(2f/16f, 2f/16f);
        this.experienceValue = 1;
        this.moveHelper = new EntityFlyHelper(this);
    }

    @Override
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying fly = new PathNavigateFlying(this, worldIn);
        fly.setCanEnterDoors(true);
        fly.setCanFloat(true);
        return fly;
    }

    public void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4D);
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
        this.tasks.addTask(0, new EntityAIWanderAvoidWaterFlying(this, 0.30));
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
