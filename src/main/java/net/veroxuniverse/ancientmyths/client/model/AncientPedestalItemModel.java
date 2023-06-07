package net.veroxuniverse.ancientmyths.client.model;

import net.minecraft.resources.ResourceLocation;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import net.veroxuniverse.ancientmyths.item.blockitem.AncientPedestalItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AncientPedestalItemModel extends AnimatedGeoModel<AncientPedestalItem> {
    @Override
    public ResourceLocation getModelResource(AncientPedestalItem ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID, "geo/ancient_pedestal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AncientPedestalItem ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID, "textures/machines/ancient_pedestal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AncientPedestalItem ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID,"animations/ancient_pedestal.animation.json");
    }
}