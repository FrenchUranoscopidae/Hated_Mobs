package fr.uranoscopidae.hatedmobs.client.gui;

import fr.uranoscopidae.hatedmobs.common.containers.ContainerDomesticatedAnthill;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

public class GuiDomesticatedAnthill extends GuiContainer
{
    public GuiDomesticatedAnthill()
    {
        super(new ContainerDomesticatedAnthill());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

    }
}
