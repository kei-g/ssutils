package com.snowstep115.ssutils.client.gui;

import com.snowstep115.ssutils.SnowStepUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiTextureResource extends ResourceLocation {
    public final int defaultWidth;
    public final int defaultHeight;

    public GuiTextureResource(String location, int defaultWidth, int defaultHeight) {
        super(SnowStepUtils.MODID, "textures/gui/" + location + ".png");
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    public void bind() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this);
    }

    public void drawQuad(int x, int y, int u, int v, int w, int h, float zLevel) {
        float scaleX = 1.0F / defaultWidth;
        float scaleY = 1.0F / defaultHeight;
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(x + 0, y + h, zLevel).tex((u + 0) * scaleX, (v + h) * scaleY).endVertex();
        bufferBuilder.pos(x + w, y + h, zLevel).tex((u + w) * scaleX, (v + h) * scaleY).endVertex();
        bufferBuilder.pos(x + w, y + 0, zLevel).tex((u + w) * scaleX, (v + 0) * scaleY).endVertex();
        bufferBuilder.pos(x + 0, y + 0, zLevel).tex((u + 0) * scaleX, (v + 0) * scaleY).endVertex();
        tess.draw();
    }

    public void drawQuad(int x, int y, int u, int v, int w, int h) {
        drawQuad(x, y, u, v, w, h, 0);
    }
}
