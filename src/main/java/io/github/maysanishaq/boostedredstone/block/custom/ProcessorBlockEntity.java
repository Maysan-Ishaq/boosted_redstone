package io.github.maysanishaq.boostedredstone.block.custom;

import io.github.maysanishaq.boostedredstone.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class ProcessorBlockEntity extends BlockEntity {
    private String tasksText = "";

    public ProcessorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.PROCESSOR_BLOCK_ENTITY, worldPosition, blockState);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putString("tasksText", this.tasksText);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        this.tasksText = input.getStringOr("tasksText", "");
    }

    public String getTasksText() {
        return this.tasksText;
    }

    public void setTasksText(String tasksText) {
        this.tasksText = tasksText;
        this.setChanged();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ProcessorBlockEntity processorBlockEntity) {
        if(level.isClientSide()) {
            return;
        }

        int northSignal = level.getSignal(pos.north(), Direction.NORTH);
        int southSignal = level.getSignal(pos.south(), Direction.SOUTH);
        int eastSignal = level.getSignal(pos.east(), Direction.EAST);
        int westSignal = level.getSignal(pos.west(), Direction.WEST);

        String tasks = processorBlockEntity.getTasksText();

        boolean northLit = northSignal > 0;
        boolean southLit = southSignal > 0;
        boolean eastLit  = eastSignal  > 0;
        boolean westLit  = westSignal  > 0;

        boolean northOutputMode = false;
        boolean southOutputMode = false;
        boolean eastOutputMode  = false;
        boolean westOutputMode  = false;



        // INTERPRETER ==================

        northOutputMode = true;

        northLit = (eastLit && westLit);

        // ==============================

        if(!northOutputMode) { northLit = false; }
        if(!southOutputMode) { southLit = false; }
        if(!eastOutputMode) { eastLit = false; }
        if(!westOutputMode) { westLit = false; }

        BlockState newState = state;
        boolean changed = false;

        if(state.getValue(ProcessorBlock.NORTH_LIT) != northLit) {
            newState = newState.setValue(ProcessorBlock.NORTH_LIT, northLit);
            changed = true;
        }

        if(state.getValue(ProcessorBlock.SOUTH_LIT) != southLit) {
            newState = newState.setValue(ProcessorBlock.SOUTH_LIT, southLit);
            changed = true;
        }

        if(state.getValue(ProcessorBlock.EAST_LIT) != eastLit) {
            newState = newState.setValue(ProcessorBlock.EAST_LIT, eastLit);
            changed = true;
        }

        if(state.getValue(ProcessorBlock.WEST_LIT) != westLit) {
            newState = newState.setValue(ProcessorBlock.WEST_LIT, westLit);
            changed = true;
        }

        if(changed) {
            level.setBlock(pos, newState, ProcessorBlock.UPDATE_ALL);
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }
}
