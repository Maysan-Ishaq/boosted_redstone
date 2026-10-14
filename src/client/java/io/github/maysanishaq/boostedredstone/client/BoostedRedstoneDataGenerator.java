package io.github.maysanishaq.boostedredstone.client;

import io.github.maysanishaq.boostedredstone.client.datagen.ModBlockLootTableProvider;
import io.github.maysanishaq.boostedredstone.client.datagen.ModBlockTagsProvider;
import io.github.maysanishaq.boostedredstone.client.datagen.ModModelProvider;
import io.github.maysanishaq.boostedredstone.client.datagen.ModRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class BoostedRedstoneDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModBlockTagsProvider::new);
		pack.addProvider(ModBlockLootTableProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
