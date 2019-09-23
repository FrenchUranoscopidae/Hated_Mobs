package fr.uranoscopidae.hatedmobs.common.items;

import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.ConfigurationHandler;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;


public class ItemSpiderEgg extends Item
{
    public static final TextComponentTranslator CANT_SPAWN = new TextComponentTranslator(HatedMobs.MODID + ".cant_spawn_silk_spider");
    public static final TextComponentTranslator DESCRIPTION = new TextComponentTranslator(HatedMobs.MODID + ".spider_egg_description");

    public ItemSpiderEgg()
    {
        super();
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "spider_egg"));
        setUnlocalizedName("spider_egg");
        setCreativeTab(HatedMobs.TAB);
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.isRemote)
        {
            return ActionResultType.SUCCESS;
        }
        else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            return ActionResultType.FAIL;
        }

        if(worldIn.getBlockState(pos).getBlock() != HatedMobs.SPIDER_INFESTED_LEAVES_BLOCK)
        {
            return ActionResultType.FAIL;
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

                return ActionResultType.SUCCESS;
            }
        }
        player.sendStatusMessage(CANT_SPAWN, true);

        return ActionResultType.FAIL;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(DESCRIPTION.getUnformattedText());
    }
}
