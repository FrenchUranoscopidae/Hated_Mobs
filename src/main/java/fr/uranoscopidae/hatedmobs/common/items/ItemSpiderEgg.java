package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

import static net.minecraft.item.ItemMonsterPlacer.applyItemEntityDataToEntity;

public class ItemSpiderEgg extends Item
{
    public ItemSpiderEgg()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "spider_egg"));
        setUnlocalizedName("spider_egg");
        setCreativeTab(HatedMobs.TAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return EnumActionResult.FAIL;
        }

        if(worldIn.getBlockState(pos).getBlock() != HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
        {
            return EnumActionResult.FAIL;
        }

        BlockPos blockpos = pos.offset(facing);
        double d0 = this.getYOffset(worldIn, blockpos);
        EntitySilkSpider entity = new EntitySilkSpider(worldIn, pos);

        entity.setPosition((double)blockpos.getX() + 0.5D, (double)blockpos.getY() + d0, (double)blockpos.getZ() + 0.5D);

        worldIn.spawnEntity(entity);

        if (itemstack.hasDisplayName())
        {
            entity.setCustomNameTag(itemstack.getDisplayName());
        }

        applyItemEntityDataToEntity(worldIn, player, itemstack, entity);

        if (!player.capabilities.isCreativeMode)
        {
            itemstack.shrink(1);
        }

        return EnumActionResult.SUCCESS;
    }

    private double getYOffset(World worldIn, BlockPos blockpos)
    {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = worldIn.getCollisionBoxes((Entity)null, axisalignedbb);

        if (list.isEmpty())
        {
            return 0.0D;
        }
        else
        {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list)
            {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double)blockpos.getY();
        }
    }


}
