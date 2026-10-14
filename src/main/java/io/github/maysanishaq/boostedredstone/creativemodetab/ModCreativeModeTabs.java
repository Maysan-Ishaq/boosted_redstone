package io.github.maysanishaq.boostedredstone.creativemodetab;

import io.github.maysanishaq.boostedredstone.BoostedRedstone;
import io.github.maysanishaq.boostedredstone.item.ModItems;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {

    public static final CreativeModeTab BOOSTED_REDSTONE_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, "boosted_redstone_items"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MECHANIC_KEY))
                    .title(Component.translatable("creativemodetab.boosted-redstone.boosted_redstone_items"))
                    .displayItems(((parameters, output) -> {
                        output.accept(ModItems.MECHANIC_KEY);
                    }))
                    .build()
    );

    public static void registerModCreativeModeTabs() {
        BoostedRedstone.LOGGER.info("registering mod creative mode tabs for mod " + BoostedRedstone.MOD_ID);
    }

}
