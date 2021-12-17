package com.criscky.armourbeyond.setup.client.screens;

import com.criscky.armourbeyond.armourbeyond;
import com.criscky.armourbeyond.setup.containers.InjectorContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class InjectorScreen extends ContainerScreen<InjectorContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(armourbeyond.MOD_ID, "textures/gui/injector.png");

    public InjectorScreen(InjectorContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) {
            return;
        }

        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE);

        int posX = (this.width - this.imageWidth) / 2;
        int posY = (this.height - this.imageHeight) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.imageWidth, this.imageHeight);

        // Progress arrow
        blit(matrixStack, posX + 98, posY + 29, 176, 14, menu.getProgressArrowScale() + 1, 16);
    }
}
