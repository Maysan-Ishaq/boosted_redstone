package io.github.maysanishaq.boostedredstone.block;

import io.github.maysanishaq.boostedredstone.BoostedRedstone;
import io.github.maysanishaq.boostedredstone.block.custom.ProcessorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final BlockEntityType<ProcessorBlockEntity> PROCESSOR_BLOCK_ENTITY = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Identifier.fromNamespaceAndPath(BoostedRedstone.MOD_ID, "processor_block_entity")),
            FabricBlockEntityTypeBuilder.create(ProcessorBlockEntity::new, ModBlocks.PROCESSOR_BLOCK).build()
    );

    public static void registerBlockEntities() {
        BoostedRedstone.LOGGER.info("registering mod block entities for mod " + BoostedRedstone.MOD_ID);

    }
}
