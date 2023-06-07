package net.veroxuniverse.ancientmyths.block;

import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.veroxuniverse.ancientmyths.AncientMythsMod;
import net.veroxuniverse.ancientmyths.block.custom.AncientPedestal;
import net.veroxuniverse.ancientmyths.item.ItemsRegistry;
import net.veroxuniverse.ancientmyths.util.CustomCreativeTabs;

import java.util.function.Supplier;

public class BlocksRegistry {

    //DeferredRegisters

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AncientMythsMod.MOD_ID);

    //Registries

    public static final RegistryObject<Block> ANCIENT_PEDESTAL = registerBlockWithoutBlockItem("ancient_pedestal",
            () -> new AncientPedestal(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()
                    .strength(4f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> ANCIENT_STONE = registerBlock("ancient_stone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f).requiresCorrectToolForDrops()), CustomCreativeTabs.ANCIENT_MYTHS_TAB);

    public static final RegistryObject<Block> ANCIENT_COBBLESTONE = registerBlock("ancient_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(4f).requiresCorrectToolForDrops()), CustomCreativeTabs.ANCIENT_MYTHS_TAB);


    //Registry Methods

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ItemsRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}