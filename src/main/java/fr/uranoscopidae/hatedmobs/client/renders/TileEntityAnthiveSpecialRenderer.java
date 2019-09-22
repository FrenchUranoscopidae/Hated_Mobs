package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.PathfinderAStar;
import fr.uranoscopidae.hatedmobs.common.blocks.BlockNet;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityAntHive;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntityAnthiveSpecialRenderer extends TileEntitySpecialRenderer<TileEntityAntHive>
{

    public static final ResourceLocation ANT_HILE_LOCATION = new ResourceLocation(HatedMobs.MODID, "textures/ant_tile.png");

    @Override
    public void render(TileEntityAntHive te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        int startX = te.getPos().getX();
        int startY = te.getPos().getY();
        int startZ = te.getPos().getZ();
        this.bindTexture(ANT_HILE_LOCATION);
        for (BlockPos target : te.posSet)
        {
            List<PathfinderAStar.Node> path = te.getPath(target);
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
                    GlStateManager.translated(relativeX+1, relativeY, relativeZ+1);
                    GlStateManager.scaled(-1,1,-1);

                    double centerX = 0.5;
                    double centerY = 0.5;
                    double centerZ = 0.5;
                    GlStateManager.translated(centerX, centerY, centerZ);
                    switch (facing) {
                        case DOWN:
                            GlStateManager.rotated(180f, 1f, 0f, 0f);
                            break;

                        case UP:
                            break;

                        case NORTH:
                            GlStateManager.rotated(90f, 1f, 0f, 0f);
                            break;

                        case SOUTH:
                            GlStateManager.rotated(-90f, 1f, 0f, 0f);
                            break;

                        case WEST:
                            GlStateManager.rotated(90f, 0f, 1f, 0f);
                            GlStateManager.rotated(90f, 1f, 0f, 0f);
                            break;

                        case EAST:
                            GlStateManager.rotated(-90f, 0f, 1f, 0f);
                            GlStateManager.rotated(90f, 1f, 0f, 0f);
                            break;
                    }

                    GlStateManager.translated(-centerX, -centerY, -centerZ);

                    Tessellator tess = Tessellator.getInstance();
                    BufferBuilder builder = tess.getBuffer();
                    builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
                    builder.pos(0, 1f-.8/16f, 0).tex(0, 0).endVertex();
                    builder.pos(16f/16f, 1f-.8/16f, 0).tex(1, 0).endVertex();
                    builder.pos(16f/16f, 1f-.8/16f, 16f/16f).tex(1, 1).endVertex();
                    builder.pos(0, 1f-.8/16f, 16f/16f).tex(0, 1).endVertex();
                    GlStateManager.disableCull();
                    tess.draw();
                    GlStateManager.enableCull();

                    GlStateManager.popMatrix();
                }
            }
        }
    }


}
