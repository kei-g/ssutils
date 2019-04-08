package com.snowstep115.ssutils.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
//import net.minecraft.item.ItemStack;

public class GuiSnowChest extends GuiContainer {
    private static final GuiTextureResource TEXTURE = new GuiTextureResource("snowchest", 256, 256);

    public GuiSnowChest(Container container) {
        super(container);
        xSize = 256;
        ySize = 256;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        //this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, new ItemStack(Items.APPLE), (this.width - 16) / 2, (this.height - 16) / 2);
        //this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, new ItemStack(Items.APPLE), (this.width - 16) / 2, (this.height - 16) / 2, String.format("%d,%d", this.width, this.height));
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //fontRenderer.drawString(I18n.format("container.inventory"), 12, 80, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        TEXTURE.bind();
        TEXTURE.drawQuad((width - xSize) / 2, (height - ySize) / 2, 0, 0, 256, 256);
    }
}
