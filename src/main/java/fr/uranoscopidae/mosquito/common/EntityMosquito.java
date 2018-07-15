package fr.uranoscopidae.mosquito.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMosquito extends EntityMob
{
    public EntityMosquito(World world)
    {
        super(world);
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
}
