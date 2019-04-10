package com.snowstep115.ssutils.client.gui;

import java.util.ArrayList;
import com.snowstep115.ssutils.container.ContainerBankNull;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class GuiBankNull extends GuiContainer {
    private static final GuiTextureResource TEXTURE = new GuiTextureResource("banknull", 256, 256);

    public GuiBankNull(Container container) {
        super(container);
        this.xSize = 256;
        this.ySize = 256;
    }

    private void drawButtonsAndLabels(int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < this.buttonList.size(); i++) {
            ((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        for (int j = 0; j < this.labelList.size(); j++) {
            ((GuiLabel) this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        this.drawButtonsAndLabels(mouseX, mouseY, partialTicks);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) this.guiLeft, (float) this.guiTop, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.inventorySlots instanceof ContainerBankNull) {
            ArrayList<ItemStack> stacks = ((ContainerBankNull) this.inventorySlots).collect();
            int y = 2, x = 2;
            for (ItemStack stack : stacks) {
                this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, stack, x, y);
                if (stack.getCount() > 1) {
                    String msg = String.format("%d", stack.getCount());
                    this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, stack, x, y, msg);
                }
                x += 18;
                if (this.xSize < x + 18) {
                    x = 2;
                    y += 18;
                }
            }
        }
        this.renderHoveredToolTip(mouseX, mouseY);
        RenderHelper.disableStandardItemLighting();
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);
        RenderHelper.enableGUIStandardItemLighting();
        net.minecraftforge.common.MinecraftForge.EVENT_BUS
                .post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, mouseX, mouseY));
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        TEXTURE.bind();
        TEXTURE.drawQuad((width - xSize) / 2, (height - ySize) / 2, 0, 0, 256, 256);
    }
}
