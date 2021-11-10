package com.criscky.armourbeyond.setup.client.tileentityrender;

import com.criscky.armourbeyond.setup.configs.ClientConfig;
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
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

public class InjectorRenderer  extends TileEntityRenderer<InjectorTileEntity> {
    private Minecraft mc = Minecraft.getInstance();
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    final double  y_base = 0.78d;


    public InjectorRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(InjectorTileEntity pBlockEntity, float pPartialTicks, MatrixStack pMatrixStack, IRenderTypeBuffer pBuffer, int pCombinedLight, int pCombinedOverlay) {
        if(pBlockEntity.isEmpty() || !ClientConfig.render_item_injector.get()){
            return;
        }
        ClientPlayerEntity player = mc.player;
        int lightLevel = getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos().above());
        Direction FacingValue = pBlockEntity.getBlockState().getValue(FACING);


        if(isValidToRender(pBlockEntity.getItem(0))){
            renderItem(pBlockEntity.getItem(0), new double[] { 0.5d, this.y_base, 0.5d },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(1))){
            double x = 0.25d;
            double z = 0.5d;
            renderItem(pBlockEntity.getItem(1), new double[] { PositionRender(FacingValue, x, z, true), this.y_base, PositionRender(FacingValue, x, z, false) },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);

        }
        if(isValidToRender(pBlockEntity.getItem(2))){
            double x = 0.44d;
            double z = 0.25d;
            renderItem(pBlockEntity.getItem(2), new double[] { PositionRender(FacingValue, x, z, true), this.y_base, PositionRender(FacingValue, x, z, false) },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(3))){
            double x = 0.68d;
            double z = 0.31d;
            renderItem(pBlockEntity.getItem(3), new double[] { PositionRender(FacingValue, x, z, true), this.y_base, PositionRender(FacingValue, x, z, false) },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(4))){
            double x = 0.68d;
            double z = 0.68d;
            renderItem(pBlockEntity.getItem(4), new double[] { PositionRender(FacingValue, x, z, true), this.y_base, PositionRender(FacingValue, x, z, false) },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(5))){
            double x = 0.44d;
            double z = 0.75d;
            renderItem(pBlockEntity.getItem(5), new double[] { PositionRender(FacingValue, x, z, true), this.y_base,PositionRender(FacingValue, x, z, false) },
                    Vector3f.YP.rotationDegrees(180f - player.yBob), pMatrixStack, pBuffer, pPartialTicks,
                    pCombinedOverlay, lightLevel, 0.2f);
        }
        if(isValidToRender(pBlockEntity.getItem(6))){
            renderItem(pBlockEntity.getItem(6), new double[] { 0.5d, y_base+0.25d, 0.5d },
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

    public double PositionRender(Direction pDirection, double x, double z, boolean isx){
        switch (pDirection) {
            case SOUTH:
                return isx ? 1d - z : 1d - x;
            case NORTH:
                return isx ? z : x;
            case EAST:
                return isx ? 1d - x : 1d - z;
            default:
                return isx ? x : z;
        }
    }
}
