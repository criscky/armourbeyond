package com.criscky.armourbeyond.setup.client.tileentityrender;

import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class InjectorRenderer  extends TileEntityRenderer<InjectorTileEntity> {
    private Minecraft mc = Minecraft.getInstance();

    public InjectorRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(InjectorTileEntity pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        ClientPlayerEntity player = mc.player;
        int lightLevel = getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above());
        if(isValidToRender(pBlockEntity.getItem(0))){
            renderItem(pBlockEntity.getItem(0), new double[] { 0.5d, 0.78d, 0.5d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(1))){
            renderItem(pBlockEntity.getItem(1), new double[] { 0.25d, 0.78d, 0.5d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(2))){
            renderItem(pBlockEntity.getItem(2), new double[] { 0.44d, 0.78d, 0.25d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(3))){
            renderItem(pBlockEntity.getItem(3), new double[] { 0.68d, 0.78d, 0.31d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(4))){
            renderItem(pBlockEntity.getItem(4), new double[] { 0.68d, 0.78d, 0.69d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(5))){
            renderItem(pBlockEntity.getItem(5), new double[] { 0.44d, 0.78d, 0.75d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(6))){
            renderItem(pBlockEntity.getItem(6), new double[] { 0.5d, 1.03d, 0.5d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }

    }


    private void renderItem(ItemStack stack, double[] translation, Quaternion rotation, MatrixStack matrixStack,
                            IRenderTypeBuffer buffer, float partialTicks, int combinedOverlay, int lightLevel, float scale) {
        matrixStack.pushPose();
        matrixStack.translate(translation[0], translation[1], translation[2]);
        matrixStack.mulPose(rotation);
        matrixStack.scale(scale, scale, scale);

        IBakedModel model = mc.getItemRenderer().getModel(stack, null, null);
        mc.getItemRenderer().render(stack, ItemCameraTransforms.TransformType.GROUND, true, matrixStack, buffer,
                lightLevel, combinedOverlay, model);
        matrixStack.popPose();
    }

    private int getLightLevel(World world, BlockPos pos) {
        int bLight = world.getBrightness(LightType.BLOCK, pos);
        int sLight = world.getBrightness(LightType.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    public boolean isValidToRender(ItemStack item){
        return !(item.equals(ItemStack.EMPTY) || item.getItem().equals(Items.AIR));
    }
}
