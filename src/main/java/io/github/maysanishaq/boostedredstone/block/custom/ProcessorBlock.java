package io.github.maysanishaq.boostedredstone.block.custom;

import io.github.maysanishaq.boostedredstone.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
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
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level instanceof ServerLevel) {
            if(itemStack.is(Items.WRITTEN_BOOK)){
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if(blockEntity instanceof ProcessorBlockEntity processorBE){
                    WrittenBookContent content = itemStack.get(DataComponents.WRITTEN_BOOK_CONTENT);
                    if(content != null) {
                        StringBuilder sb = new StringBuilder();
                        content.pages().forEach(page -> sb.append(page.raw().getString()).append("\n"));

                        processorBE.setTasksText(sb.toString());
                        player.sendSystemMessage(Component.literal("Book content saved!!"));
                        return InteractionResult.SUCCESS;
                    }
                }
            } else {
                if(level instanceof ServerLevel) {
                    BlockEntity blockEntity = level.getBlockEntity(pos);

                    if (blockEntity instanceof ProcessorBlockEntity processorBE) {
                        String text = processorBE.getTasksText();

                        if (text.isEmpty()) {
                            player.sendSystemMessage(Component.literal("This block is empty!"));
                        } else {
                            player.sendSystemMessage(Component.literal("Content: \n" + text));
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
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
        // if (level instanceof ServerLevel && type == ModBlockEntities.PROCESSOR_BLOCK_ENTITY) {
        //     (BlockEntityTicker<T>) (lvl, pos, state, be) ->
        //             ProcessorBlockEntity.tick(lvl, pos, state, (ProcessorBlockEntity) be)
        // }
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return switch(direction) {
            case NORTH -> state.getValue(NORTH_LIT) ? 15: 0;
            case SOUTH -> state.getValue(SOUTH_LIT) ? 15: 0;
            case EAST  -> state.getValue(EAST_LIT)  ? 15: 0;
            case WEST  -> state.getValue(WEST_LIT)  ? 15: 0;
            default -> 0;
        };
    }
}
