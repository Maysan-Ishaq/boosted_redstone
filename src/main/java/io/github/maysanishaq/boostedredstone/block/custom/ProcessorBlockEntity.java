package io.github.maysanishaq.boostedredstone.block.custom;

import io.github.maysanishaq.boostedredstone.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.util.Locale;

public class ProcessorBlockEntity extends BlockEntity {
    private String tasksText = "";

    private Globals luaGlobals;
    private boolean scriptCompiled = false;


    public boolean northOut = false, southOut = false, eastOut = false, westOut = false;

    public ProcessorBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntities.PROCESSOR_BLOCK_ENTITY, worldPosition, blockState);
        initLuaEnvironment();
    }

    private void initLuaEnvironment() {
        luaGlobals = JsePlatform.standardGlobals();

        updateLuaAPI(false, false, false, false);
    }

    private void updateLuaAPI(boolean nIn, boolean sIn, boolean eIn, boolean wIn) {
        luaGlobals.set("getInput", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                String dir = arg.checkjstring().toUpperCase(Locale.ROOT);
                return switch (dir) {
                    case "N" -> LuaValue.valueOf(nIn);
                    case "S" -> LuaValue.valueOf(sIn);
                    case "E" -> LuaValue.valueOf(eIn);
                    case "W" -> LuaValue.valueOf(wIn);
                    default -> LuaValue.FALSE;
                };
            }
        });

        luaGlobals.set("setOutput", new TwoArgFunction() {
            @Override
            public LuaValue call(LuaValue arg1, LuaValue arg2) {
                String dir = arg1.checkjstring().toUpperCase(Locale.ROOT);
                boolean val = arg2.checkboolean();
                switch (dir) {
                    case "N" -> northOut = val;
                    case "S" -> southOut = val;
                    case "E" -> eastOut = val;
                    case "W" -> westOut = val;
                }
                return LuaValue.NIL;
            }
        });
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
        compileScript();
    }

    public String getTasksText() {
        return this.tasksText;
    }

    public void setTasksText(String tasksText) {
        this.tasksText = tasksText;
        compileScript();
        this.setChanged();
    }

    private void compileScript() {
        initLuaEnvironment();
        if(tasksText != null && !tasksText.isEmpty()) {
            try {
                luaGlobals.load(tasksText, "processorScript");
                scriptCompiled = true;
            } catch (Exception e) {
                scriptCompiled = false;
            }
        } else {
            scriptCompiled = false;
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ProcessorBlockEntity processorBlockEntity) {
        if(level.isClientSide()) return;

        boolean northLit = level.getSignal(pos.north(), Direction.NORTH) > 0;
        boolean southLit = level.getSignal(pos.south(), Direction.SOUTH) > 0;
        boolean eastLit = level.getSignal(pos.east(), Direction.EAST) > 0;
        boolean westLit = level.getSignal(pos.west(), Direction.WEST) > 0;

        processorBlockEntity.updateLuaAPI(northLit, southLit, eastLit, westLit);

        if(processorBlockEntity.scriptCompiled) {
            try {
                processorBlockEntity.luaGlobals.load(processorBlockEntity.tasksText, "ProcessorScript").call();

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        BlockState newState = state;
        boolean changed = false;

        if(state.getValue(ProcessorBlock.NORTH_LIT) != processorBlockEntity.northOut) {
            newState = newState.setValue(ProcessorBlock.NORTH_LIT, processorBlockEntity.northOut);
            changed = true;
        }
        if(state.getValue(ProcessorBlock.SOUTH_LIT) != processorBlockEntity.southOut) {
            newState = newState.setValue(ProcessorBlock.SOUTH_LIT, processorBlockEntity.southOut);
            changed = true;
        }
        if(state.getValue(ProcessorBlock.EAST_LIT) != processorBlockEntity.eastOut) {
            newState = newState.setValue(ProcessorBlock.EAST_LIT, processorBlockEntity.eastOut);
            changed = true;
        }
        if(state.getValue(ProcessorBlock.WEST_LIT) != processorBlockEntity.westOut) {
            newState = newState.setValue(ProcessorBlock.WEST_LIT, processorBlockEntity.westOut);
            changed = true;
        }

        if(changed) {
            level.setBlock(pos, newState, ProcessorBlock.UPDATE_ALL);
            level.updateNeighbourForOutputSignal(pos, state.getBlock());
        }
    }
}
