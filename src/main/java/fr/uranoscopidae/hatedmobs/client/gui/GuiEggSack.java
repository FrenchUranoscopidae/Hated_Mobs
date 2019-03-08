package fr.uranoscopidae.hatedmobs.client.gui;

import fr.uranoscopidae.hatedmobs.common.containers.ContainerEggSack;
import fr.uranoscopidae.hatedmobs.common.tileentities.TileEntityEggSack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiEggSack extends GuiContainer
{
    ResourceLocation backgroundTexture = new ResourceLocation("textures/gui/container/generic_54.png");

    public GuiEggSack(InventoryPlayer inventoryPlayer, TileEntityEggSack tileEntityEggSack)
    {
        super(new ContainerEggSack(inventoryPlayer, tileEntityEggSack));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        mc.getTextureManager().bindTexture(backgroundTexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, 17 + 3 * 18);
        drawTexturedModalRect(guiLeft, guiTop + 3 * 18 + 17, 0, 126, xSize, 96);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}
