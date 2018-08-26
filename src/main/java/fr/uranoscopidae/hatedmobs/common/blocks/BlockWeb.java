package fr.uranoscopidae.hatedmobs.common.blocks;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntitySilkSpider;
import fr.uranoscopidae.hatedmobs.common.items.ItemSilkBoots;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockWeb extends Block
{
    protected static final AxisAlignedBB WEB_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D);

    public BlockWeb()
    {
        super(Material.WEB);
        this.setCreativeTab(HatedMobs.TAB);
        setRegistryName(new ResourceLocation(HatedMobs.MODID, "web_block"));
        setUnlocalizedName("web_block");
        setHardness(0.6F);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return WEB_BLOCK_AABB;
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if(!(entityIn instanceof EntitySpider || entityIn instanceof EntitySilkSpider))
        {
            if(entityIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entityIn;
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
