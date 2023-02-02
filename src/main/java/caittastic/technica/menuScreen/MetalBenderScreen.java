package caittastic.technica.menuScreen;

import caittastic.technica.Technica;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MetalBenderScreen extends AbstractContainerScreen<MetalBenderMenu> {
    /* constructor */
    public MetalBenderScreen(MetalBenderMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }
    /* variable */
    private static final ResourceLocation TEXTURE = new ResourceLocation(Technica.MOD_ID, "textures/gui/metal_press_gui.png"); //the gui texture of the gem cutter station
    int barLocationX = 73; //top left pixel x coordinate of the gui progress bar
    int barLocationY = 39; //top left pixel y coordinate of the gui progress bar
    int barOverlayX = 176; //top left pixel x coordinate of the progress bar overlay
    int barOverlayY = 0; //top left pixel y coordinate of the progress bar overlay

    /* implemented methods */
    //"boilerplate code" that i dont know what does, something to do with drawing the texture on the screen
    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        if(menu.isCrafting()) {
            blit(pPoseStack, x + barLocationX, y + barLocationY, barOverlayX, barOverlayY, getMenu().getScaledProgress(34), 8);
        }
    }

    //"boilerplate code" that i dont know what does
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
