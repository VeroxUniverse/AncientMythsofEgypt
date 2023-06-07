package net.veroxuniverse.ancientmyths.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import net.veroxuniverse.ancientmyths.client.model.AncientPedestalItemModel;
import net.veroxuniverse.ancientmyths.client.model.AncientPedestalModel;
import net.veroxuniverse.ancientmyths.item.blockitem.AncientPedestalItem;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import javax.annotation.Nullable;

public class AncientPedestalItemRenderer extends GeoItemRenderer<AncientPedestalItem> {

    public AncientPedestalItemRenderer() {
        super(new AncientPedestalItemModel());
    }
}