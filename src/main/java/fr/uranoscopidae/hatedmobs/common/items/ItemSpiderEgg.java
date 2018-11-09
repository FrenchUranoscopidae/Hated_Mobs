package fr.uranoscopidae.hatedmobs.common.items;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.ConfigurationHandler;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static net.minecraft.item.ItemMonsterPlacer.applyItemEntityDataToEntity;

public class ItemSpiderEgg extends Item
{
    public static final TextComponentTranslation CANT_SPAWN = new TextComponentTranslation(HatedMobs.MODID + ".cant_spawn_silk_spider");
    public static final TextComponentTranslation DESCRIPTION = new TextComponentTranslation(HatedMobs.MODID + ".spider_egg_description");

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

        if(ConfigurationHandler.MOB_TOGGLE.silkSpider)
        {
            Optional<EntitySilkSpider> silkSpider = EntitySilkSpider.trySpawn(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D);

            if (silkSpider.isPresent()) {
                if (itemstack.hasDisplayName()) {
                    silkSpider.get().setCustomNameTag(itemstack.getDisplayName());
                }

                applyItemEntityDataToEntity(worldIn, player, itemstack, silkSpider.get());

                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }

                return EnumActionResult.SUCCESS;
            }
        }
        player.sendStatusMessage(CANT_SPAWN, true);

        return EnumActionResult.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(DESCRIPTION.getUnformattedText());
    }
}
