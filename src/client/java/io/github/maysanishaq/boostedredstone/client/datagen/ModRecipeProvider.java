package io.github.maysanishaq.boostedredstone.client.datagen;

import io.github.maysanishaq.boostedredstone.block.ModBlocks;
import io.github.maysanishaq.boostedredstone.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.PROCESSOR_BLOCK)
                        .define('I', Items.GOLD_INGOT)
                        .define('#', Items.REDSTONE_BLOCK)
                        .define('*', Items.REDSTONE)
                        .define(')', Items.GLOW_INK_SAC)
                        .pattern("I)I")
                        .pattern("*#*")
                        .pattern("I*I")
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .unlockedBy(getHasName(Items.REDSTONE_BLOCK), has(Items.REDSTONE_BLOCK))
                        .unlockedBy(getHasName(Items.REDSTONE), has(Items.REDSTONE))
                        .unlockedBy(getHasName(Items.GLOW_INK_SAC), has(Items.GLOW_INK_SAC))
                        .save(output);

                shaped(RecipeCategory.TOOLS, ModItems.MECHANIC_KEY)
                        .define('I', Items.GOLD_INGOT)
                        .define(',', Items.GOLD_NUGGET)
                        .pattern("I  ")
                        .pattern(" ,,")
                        .pattern(" ,,")
                        .unlockedBy(getHasName(Items.GOLD_INGOT), has(Items.GOLD_INGOT))
                        .unlockedBy(getHasName(Items.GOLD_NUGGET), has(Items.GOLD_NUGGET))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "Boosted Redstone Recipes";
    }
}
