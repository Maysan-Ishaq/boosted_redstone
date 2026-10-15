package io.github.maysanishaq.boostedredstone.block;

import io.github.maysanishaq.boostedredstone.BoostedRedstone;
import io.github.maysanishaq.boostedredstone.block.custom.ProcessorBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class ModBlocks {
    public static final Block PROCESSOR_BLOCK = registerBlock("processor_block",
            properties -> new ProcessorBlock(properties.strength(3f)
                .requiresCorrectToolForDrops().sound(SoundType.METAL)
        )
    );


    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {

        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(
            Registries.BLOCK,
            Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name)
        )));

        registerBlockItem(name, toRegister);

        return Registry.register(
            BuiltInRegistries.BLOCK,
            Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name),
            toRegister
        );
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(
            BuiltInRegistries.ITEM,
            Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name),
            new BlockItem(block, new Item
                .Properties()
                .useBlockDescriptionPrefix()
                .setId(ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name)
                )))
        );
    }

    public static void registerModBlocks() {
        BoostedRedstone.LOGGER.info("registering mod blocks for mod " + BoostedRedstone.MOD_ID);

    }
}
