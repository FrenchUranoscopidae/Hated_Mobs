package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.PathfinderAStar;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockNet;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
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
            List<PathfinderAStar.Node> path = PathfinderAStar.findPath(getWorld(), te.getPos(), target, TileEntityAntHive.RADIUS);
            if(path != null)
            {
                for(PathfinderAStar.Node node : path)
                {
                    BlockPos position = node.getPos();
                    EnumFacing facing = node.getSide();
                    double relativeX = position.getX()-startX + x;
                    double relativeY = position.getY()-startY + y;
                    double relativeZ = position.getZ()-startZ + z;
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(relativeX, relativeY, relativeZ);
                    GlStateManager.scale(-1,1,-1);
                    GlStateManager.rotate(90f, 0f, 1f, 0f);
                    Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(HatedMobs.NET.getDefaultState().withProperty(BlockNet.getPropertyFor(facing), true), 1f);
                    GlStateManager.popMatrix();
                }
            }
        }
    }
}
