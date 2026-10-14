package io.github.maysanishaq.boostedredstone.item;

import io.github.maysanishaq.boostedredstone.BoostedRedstone;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {

    public static final Item MECHANIC_KEY = registerItem("mechanic_key", Item::new);



    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(
                BuiltInRegistries.ITEM,
                Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(
                        Registries.ITEM,
                        Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, name)
                )))
        );
    }

    public static void registerModItems() {
        BoostedRedstone.LOGGER.info("registering mod items for mod " + BoostedRedstone.MOD_ID);

    }
}
