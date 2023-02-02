package caittastic.technica.menuScreen;

import caittastic.technica.Technica;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu> {
    //constructure
    public GeneratorScreen(GeneratorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }
    ResourceLocation GUITexture = new ResourceLocation(Technica.MOD_ID, "textures/gui/generator_gui.png"); //texture of generator gui
    int[] energyBarBaseXYOverlayXY = {80,27,176,0}; //top left pixel of the energy bar base and energy bar base overlay

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUITexture); //rset gui to be rendered
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(poseStack, x, y, 0, 0, imageWidth, imageHeight); //draw gui
        //if machine is burning, render the flames in the gui scaled based on burn progress
        if(menu.isCrafting())
            blit(poseStack, x + 46, y + 53 + 13 - menu.getScaledBurnHeight(13), 176, 24 + 13 - menu.getScaledBurnHeight(13), 14, menu.getScaledBurnHeight(13));

        //draw energy bar scaled with energy stored
        //if(menu.hasEnergy())
            blit(poseStack, x + 84, y + 31 + 24 - menu.getScaledEnergyHeight(24), 176, 0 + 24 - menu.getScaledEnergyHeight(24) , 8, menu.getScaledEnergyHeight(24));
    }

    //"boilerplate code" that i dont know what does
    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
