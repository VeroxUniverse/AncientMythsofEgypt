package net.veroxuniverse.ancientmyths.block;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;

public class BlocksEntitiesRegistry {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AncientMythsMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<AncientPedestalTile>> ANCIENT_PEDESTAL_TILE =
            BLOCK_ENTITY_TYPES.register("ancient_pedestal_tile", () ->
                    BlockEntityType.Builder.of(AncientPedestalTile::new,
                            BlocksRegistry.ANCIENT_PEDESTAL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
