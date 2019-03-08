package fr.uranoscopidae.hatedmobs.client.gui;

import fr.uranoscopidae.hatedmobs.HatedMobs;
import fr.uranoscopidae.hatedmobs.common.containers.ContainerDomesticatedAnthill;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityDomesticatedAnthill;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiDomesticatedAnthill extends GuiContainer
{
    ResourceLocation backgroundTexture = new ResourceLocation(HatedMobs.MODID, "textures/gui/domesticated_anthill.png");
    TileEntityDomesticatedAnthill tileEntity;

    public GuiDomesticatedAnthill(InventoryPlayer inventory, TileEntityDomesticatedAnthill tileEntity)
    {
        super(new ContainerDomesticatedAnthill(inventory, tileEntity));
        this.tileEntity = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(backgroundTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GlStateManager.disableCull();
        drawTexturedModalRect(guiLeft + 79 + 18, guiTop + 35, 176+18, 0, (int) -(18 * (double)tileEntity.progressTick / TileEntityDomesticatedAnthill.DURATION_IN_TICKS), 3);
        GlStateManager.enableCull();
    }
}
