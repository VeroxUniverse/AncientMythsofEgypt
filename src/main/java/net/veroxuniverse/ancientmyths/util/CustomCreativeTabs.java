package net.veroxuniverse.ancientmyths.util;

import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class CustomCreativeTabs {

    public static final CreativeModeTab ANCIENT_MYTHS_TAB = new CreativeModeTab("ancient_myths_tab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ItemsRegistry.DOMINION_ROD.get());
        }
    };

}