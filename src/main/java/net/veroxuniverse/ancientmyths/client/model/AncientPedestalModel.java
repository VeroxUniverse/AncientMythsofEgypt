package net.veroxuniverse.ancientmyths.client.model;

import net.minecraft.resources.ResourceLocation;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AncientPedestalModel extends AnimatedGeoModel<AncientPedestalTile> {
    @Override
    public ResourceLocation getModelResource(AncientPedestalTile ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID, "geo/ancient_pedestal.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AncientPedestalTile ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID, "textures/machines/ancient_pedestal.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AncientPedestalTile ancientPedestalTile) {
        return new ResourceLocation(AncientMythsMod.MOD_ID,"animations/ancient_pedestal.animation.json");
    }
}