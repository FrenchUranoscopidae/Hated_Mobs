package fr.uranoscopidae.hatedmobs.client.renders;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.entities.EntityPoisonBall;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class RenderPoisonBall extends Render<EntityPoisonBall>
{
    public static final ResourceLocation TEXTURE = new ResourceLocation(HatedMobs.MODID,"textures/entity/poison_ball.png");

    public RenderPoisonBall(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPoisonBall entity)
    {
        return TEXTURE;
    }

    @Override
    public void doRender(EntityPoisonBall entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();
        GlStateManager.translated((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotated(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotated((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotated(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TEXTURE);
        float scale = 8f/16f;
        GlStateManager.scaled(scale, scale, scale);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        buffer.pos(-0.5, 0, 0).tex(0, 1).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(+0.5, 0, 0).tex(1, 1).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(+0.5, 1, 0).tex(1, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(-0.5, 1, 0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();


        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
