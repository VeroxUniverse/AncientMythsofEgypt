package net.veroxuniverse.ancientmyths.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import net.veroxuniverse.ancientmyths.client.model.AncientPedestalModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

import javax.annotation.Nullable;

public class AncientPedestalRenderer extends GeoBlockRenderer<AncientPedestalTile> {

    public AncientPedestalRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new AncientPedestalModel());
    }

    @Override
    public RenderType getRenderType(AncientPedestalTile animatable, float partialTicks, PoseStack stack,
                                    @Nullable MultiBufferSource renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                    int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }
}