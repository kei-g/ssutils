package com.snowstep115.ssutils.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;

public class GuiTrashCan extends GuiContainer {
    private static final GuiTextureResource TEXTURE = new GuiTextureResource("trashcan", 256, 256);

    public GuiTrashCan(Container container) {
        super(container);
        this.xSize = 184;
        this.ySize = 184;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(I18n.format("container.trashcan"), 12, 12, 0x404040);
        fontRenderer.drawString(I18n.format("container.inventory"), 12, 84, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        TEXTURE.bind();
        TEXTURE.drawQuad((width - xSize) / 2, (height - ySize) / 2, 0, 0, 256, 256);
    }
}
