package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.common.PathfinderAStar;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TileEntityAnthiveSpecialRenderer extends TileEntitySpecialRenderer<TileEntityAntHive>
{
    @Override
    public void render(TileEntityAntHive te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int startX = te.getPos().getX();
        int startY = te.getPos().getY();
        int startZ = te.getPos().getZ();
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        for (BlockPos target : te.posSet)
        {
            List<BlockPos> path = PathfinderAStar.findPath(getWorld(), te.getPos(), target, TileEntityAntHive.RADIUS);
            if(path != null)
            {
                for(BlockPos element : path)
                {
                    double relativeX = element.getX()-startX + x;
                    double relativeY = element.getY()-startY + y;
                    double relativeZ = element.getZ()-startZ + z;
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(relativeX, relativeY, relativeZ);
                    Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(Blocks.TNT.getDefaultState(), 1f);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}
