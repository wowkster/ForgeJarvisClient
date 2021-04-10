package com.adrianwowk.jarvis_client.client.gui;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;


public class GuiMods extends GuiScreen {

    protected int textureWidth = 256;
    protected int textureHeight = 154;
    private int framesRendered = 0;
    private int openAnimationLength;
    private int ruleAnimationLength;
    private float masterScale;

    private static final ResourceLocation BACKGROUND = new ResourceLocation("jarvis_client","textures/gui/menu/mods_menu_background.png");

    public GuiMods(){
        int openAnimationLength = 50;
        int ruleAnimationLength = openAnimationLength + 50;
        float masterScale = 1.5f;
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done")));
    }

    @Override
    // render(int mouseX, int mouseY, float delta)
    public void drawScreen(int mouseX, int mouseY, float delta) {
        framesRendered++;

        if (framesRendered == 1){
            System.out.println("Width: " + width);
        }

        // Semi transparent Backdrop

        // Background

        double scale = bezier((framesRendered < openAnimationLength ? framesRendered : 50) / (float) openAnimationLength);

        int x =  -textureWidth / 2;
        int y =  -textureHeight / 2;

        GlStateManager.color(0, 100, 100, 100);
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.translate(width / 2D, height / 2D, 0);
        GlStateManager.scale(scale * masterScale, scale * masterScale, scale * masterScale);
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(x, y, 0, 0, textureWidth, textureHeight);
        GlStateManager.popMatrix();

        // Horizontal Rule
        if (framesRendered >= openAnimationLength) {
            if (framesRendered < ruleAnimationLength) {
                scale = bezier((framesRendered - openAnimationLength) / (float)openAnimationLength);
            } else
                scale = 1.0f;
            drawHorizontalLineFromCenter(width / 2, (int) ((textureWidth - 30) * masterScale * scale), height / 2 - 50, -0xff7777);

        }

        // Title drawCenteredString()

        this.drawString(this.fontRendererObj, EnumChatFormatting.GREEN + "Pog: " + EnumChatFormatting.WHITE + Minecraft.getDebugFPS(), 50, 50, 0);
        int j;
        for(j = 0; j < this.buttonList.size(); ++j) {
            ((GuiButton)this.buttonList.get(j)).drawButton(this.mc, mouseX, mouseY);
        }

        for(j = 0; j < this.labelList.size(); ++j) {
            ((GuiLabel)this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
        }
    }

    protected void drawHorizontalLine(int x1, int x2, int y, int color){
        super.drawHorizontalLine(x1, x2, y, color);
    }

    private void drawHorizontalLineFromCenter(int centerX, int length, int y, int color){
        drawHorizontalLine(centerX, centerX + (length / 2), y, color);
        drawHorizontalLine(centerX, centerX - (length / 2), y, color);
    }

    protected void drawVerticalLine(int x, int y1, int y2, int color) {
        super.drawVerticalLine(x, y1, y2, color);
    }

    /**
     * Calculates the scale from the current animation frame using a bezier curve algorithm
     * @param time
     * @return the scale from 0.0-1.0
     */
    public static double bezier(float time){
        return time * time * (3.0f - 2.0f * time);
    }

    public int getX(){
        return this.width / 2 - textureWidth / 2;
    }
    public int getY(){
        return this.height / 2 - ((textureHeight - 25) * 9 / 16);
    }
}
