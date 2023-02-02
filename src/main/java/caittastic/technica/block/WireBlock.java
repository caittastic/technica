package caittastic.technica.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class WireBlock extends PipeBlock {
    public WireBlock(Properties properties) {
        //apothem is ?????
        super(0.3125F, properties);
        //states for all possible connection sides
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, Boolean.FALSE)
                .setValue(EAST, Boolean.FALSE)
                .setValue(SOUTH, Boolean.FALSE)
                .setValue(WEST, Boolean.FALSE)
                .setValue(UP, Boolean.FALSE)
                .setValue(DOWN, Boolean.FALSE));
    }

    //get the blockstate for placement
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.getStateForPlacement(pContext.getLevel(), pContext.getClickedPos());
    }

    //check adjacent blocks for the correct blockstate
    public BlockState getStateForPlacement(BlockGetter blockGetter, BlockPos blockPos) {
        return this.defaultBlockState()
                //set blockstate if has a valid block in this direction
                .setValue(DOWN, blockGetter.getBlockState(blockPos.below()).is(this))
                .setValue(UP, blockGetter.getBlockState(blockPos.above()).is(this))
                .setValue(NORTH, blockGetter.getBlockState(blockPos.north()).is(this))
                .setValue(EAST, blockGetter.getBlockState(blockPos.east()).is(this))
                .setValue(SOUTH, blockGetter.getBlockState(blockPos.south()).is(this))
                .setValue(WEST, blockGetter.getBlockState(blockPos.west()).is(this));
    }

    public BlockState updateShape(BlockState blockState, Direction facing, BlockState facingState, LevelAccessor level, BlockPos blockPos, BlockPos facingPos) {
        boolean flag = facingState.is(this);
        return blockState.setValue(PROPERTY_BY_DIRECTION.get(facing), Boolean.valueOf(flag));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }
}
