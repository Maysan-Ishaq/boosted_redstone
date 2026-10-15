package io.github.maysanishaq.boostedredstone.block.custom;

import io.github.maysanishaq.boostedredstone.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
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
}
