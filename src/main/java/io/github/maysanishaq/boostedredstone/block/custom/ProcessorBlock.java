package io.github.maysanishaq.boostedredstone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.Nullable;

public class ProcessorBlock extends Block implements EntityBlock {

    public static final BooleanProperty NORTH_LIT = BooleanProperty.create("north_lit");
    public static final BooleanProperty SOUTH_LIT = BooleanProperty.create("south_lit");
    public static final BooleanProperty EAST_LIT =  BooleanProperty.create("east_lit");
    public static final BooleanProperty WEST_LIT =  BooleanProperty.create("west_lit");

    public ProcessorBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(NORTH_LIT, false)
                        .setValue(SOUTH_LIT, false)
                        .setValue(EAST_LIT, false)
                        .setValue(WEST_LIT, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH_LIT, SOUTH_LIT, EAST_LIT, WEST_LIT);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new ProcessorBlockEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> type) {
        return EntityBlock.super.getTicker(level, blockState, type);
    }
}
