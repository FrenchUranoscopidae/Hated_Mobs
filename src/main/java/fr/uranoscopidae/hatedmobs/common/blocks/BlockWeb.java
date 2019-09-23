package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockWeb extends Block
{
    protected static final AxisAlignedBB WEB_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockWeb()
    {
        super(HatedMobs.WEB_MATERIAL);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "web_block"));
        setUnlocalizedName("web_block");
        blockHardness(0.6F);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return WEB_BLOCK_AABB;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if(!(entityIn instanceof SpiderEntity || entityIn instanceof EntitySilkSpider))
        {
            if(entityIn instanceof PlayerEntity)
            {
                PlayerEntity player = (PlayerEntity)entityIn;
                if(player.inventory.armorInventory.get(0).getItem() != HatedMobs.SILK_BOOTS)
                {
                    entityIn.motionX *= 0.4D;
                    entityIn.motionZ *= 0.4D;
                }
            }
            else
            {
                entityIn.motionX *= 0.4D;
                entityIn.motionZ *= 0.4D;
            }
        }
    }
}
