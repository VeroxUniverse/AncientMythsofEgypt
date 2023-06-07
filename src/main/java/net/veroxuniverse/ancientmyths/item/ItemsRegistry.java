package net.veroxuniverse.ancientmyths.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.BlocksRegistry;
import net.veroxuniverse.ancientmyths.block.custom.AncientPedestal;
import net.veroxuniverse.ancientmyths.item.blockitem.AncientPedestalItem;
import net.veroxuniverse.ancientmyths.util.CustomCreativeTabs;

@SuppressWarnings("unused")
public class ItemsRegistry {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AncientMythsMod.MOD_ID);

    //Registries

    public static final RegistryObject<Item> AMBER = ITEMS.register("amber",
            () -> new Item(new Item.Properties().tab(CustomCreativeTabs.ANCIENT_MYTHS_TAB)));

    //Blocks

    public static final RegistryObject<Item> ANCIENT_PEDESTAL_ITEM = ITEMS.register("ancient_pedestal",
            () -> new AncientPedestalItem(BlocksRegistry.ANCIENT_PEDESTAL.get(),
                    new Item.Properties().tab(CustomCreativeTabs.ANCIENT_MYTHS_TAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
