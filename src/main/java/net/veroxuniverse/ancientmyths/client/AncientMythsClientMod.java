package net.veroxuniverse.ancientmyths.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.BlocksEntitiesRegistry;
import net.veroxuniverse.ancientmyths.client.renderer.AncientPedestalRenderer;

@Mod.EventBusSubscriber(modid = AncientMythsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class AncientMythsClientMod {

    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlocksEntitiesRegistry.ANCIENT_PEDESTAL_TILE.get(), AncientPedestalRenderer::new);
    }

}
