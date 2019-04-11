package com.snowstep115.ssutils.client.gui;

import java.util.ArrayList;
import com.snowstep115.ssutils.container.ContainerBankNull;

import net.minecraft.client.gui.FontRenderer;
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

    private static String getAltText(ItemStack stack) {
        int cnt = stack.getCount();
        if (cnt <= 1) {
            return "";
        } else if (cnt < 1000) {
            return String.format("%d", cnt);
        } else if (cnt < 1000000) {
            return String.format("%dK", cnt / 1000);
        } else if (cnt < 1000000000) {
            return String.format("%dM", cnt / 1000000);
        } else {
            return String.format("%dG", cnt / 1000000000);
        }
    }

    private void drawItemStack(ItemStack stack, int x, int y) {
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        this.itemRender.zLevel = 200.0F;
        net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
        if (font == null)
            font = fontRenderer;
        this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, getAltText(stack));
        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
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
                this.drawItemStack(stack, x, y);
                x += 18;
                if (this.xSize < x + 18) {
                    x = 2;
                    y += 18;
                }
            }
        }
        if (this.guiLeft < mouseX && mouseX < this.guiLeft + this.xSize && this.guiTop < mouseY
                && mouseY < this.guiTop + this.ySize && this.inventorySlots instanceof ContainerBankNull) {
            ArrayList<ItemStack> stacks = ((ContainerBankNull) this.inventorySlots).collect();
            int index = ((mouseX - this.guiLeft - 2) / 18) + ((mouseY - this.guiTop - 2) / 18) * 14;
            if (index < stacks.size()) {
                ItemStack stack = stacks.get(index);
                FontRenderer font = stack.getItem().getFontRenderer(stack);
                net.minecraftforge.fml.client.config.GuiUtils.preItemToolTip(stack);
                this.drawHoveringText(this.getItemToolTip(stack), mouseX - this.guiLeft, mouseY - this.guiTop,
                        (font == null ? fontRenderer : font));
                net.minecraftforge.fml.client.config.GuiUtils.postItemToolTip();
            }
        }
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
