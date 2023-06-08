package net.veroxuniverse.ancientmyths.client.renderer;

import com.hollingsworth.arsnouveau.client.ClientInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import net.veroxuniverse.ancientmyths.client.model.AncientPedestalModel;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.Objects;

public class AncientPedestalRenderer extends GeoBlockRenderer<AncientPedestalTile> {

    MultiBufferSource buffer;
    AncientPedestalTile tile;
    ResourceLocation text;


    public AncientPedestalRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new AncientPedestalModel());
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (bone.getName().equals("floating_item") && tile.getStack() != null) {
            float offsetY = (float)Math.sin((double)((float)ClientInfo.ticksInGame / 8.0F)) * 0.06F;

            double x = tile.getBlockPos().getX();
            double y = tile.getBlockPos().getY();
            double z = tile.getBlockPos().getZ();
            if (tile.renderEntity == null || !ItemStack.matches(tile.renderEntity.getItem(), tile.getStack())) {
                tile.renderEntity = new ItemEntity(Objects.requireNonNull(tile.getLevel()), x, y, z, tile.getStack());
            }
            stack.pushPose();
            RenderUtils.translateMatrixToBone(stack, bone);
            stack.translate(0, 1.1 + offsetY, 0);
            stack.scale(0.75f, 0.75f, 0.75f);
            stack.mulPose(Vector3f.YP.rotationDegrees(((float) ClientInfo.ticksInGame) * 3f));
            ItemStack itemstack = tile.renderEntity.getItem();
            Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemTransforms.TransformType.GROUND, packedLightIn, OverlayTexture.NO_OVERLAY, stack, this.buffer, (int) tile.getBlockPos().asLong());
            stack.popPose();
            bufferIn = buffer.getBuffer(RenderType.entityCutoutNoCull(text));
        }
        super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void renderEarly(AncientPedestalTile animatable, PoseStack stackIn, float ticks, MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float partialTicks) {
        this.tile = animatable;
        this.buffer = renderTypeBuffer;
        this.text = this.getTextureLocation(animatable);
        super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, partialTicks);
    }

    @Override
    public void render(BlockEntity tile, float v, PoseStack matrixStack, MultiBufferSource iRenderTypeBuffer, int lightIn, int overlayIn) {
        try {
            super.render(tile, v, matrixStack, iRenderTypeBuffer, lightIn, overlayIn);
            AncientPedestalTile tileEntityIn = (AncientPedestalTile) tile;
            this.tile = tileEntityIn;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

   /*

    @Override
    public RenderType getRenderType(AncientPedestalTile animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                    int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    */

}