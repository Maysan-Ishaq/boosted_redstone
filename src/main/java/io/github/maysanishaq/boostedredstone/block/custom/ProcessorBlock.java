package io.github.maysanishaq.boostedredstone.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ProcessorBlock extends Block {

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
                        .setValue(EAST_LIT, true)
                        .setValue(WEST_LIT, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH_LIT, SOUTH_LIT, EAST_LIT, WEST_LIT);
    }
}
