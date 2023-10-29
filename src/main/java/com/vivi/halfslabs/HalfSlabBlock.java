package com.vivi.halfslabs;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HalfSlabBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<HalfSlabType> TYPE = EnumProperty.create("type", HalfSlabType.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;


    public HalfSlabBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(TYPE, HalfSlabType.BOTTOM).setValue(WATERLOGGED, false));
    }




    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos pos = pContext.getClickedPos();
        BlockState blockstate = pContext.getLevel().getBlockState(pos);
        HalfSlabs.LOGGER.info(blockstate.toString());

        if(blockstate.is(this)) {
            HalfSlabType type;
            double clickPos = (pContext.getClickLocation().y - (double)pos.getY()) * 16;

            if(clickPos <= blockstate.getValue(TYPE).y1()) {
                type = oneDown(blockstate.getValue(TYPE));
            }
            else {
                type = oneUp(blockstate.getValue(TYPE));
            }

            return blockstate.setValue(TYPE, type);
        }

        BlockState newState = this.defaultBlockState().setValue(TYPE, HalfSlabType.BOTTOM).setValue(WATERLOGGED, pContext.getLevel().getFluidState(pos).getType() == Fluids.WATER);
        Direction direction = pContext.getClickedFace();
        if(direction == Direction.DOWN) return newState.setValue(TYPE, HalfSlabType.TOP);
        if(direction == Direction.UP) return newState.setValue(TYPE, HalfSlabType.BOTTOM);

        double clickPos = pContext.getClickLocation().y - (double)pos.getY();
        if(clickPos <= 0.25) return newState.setValue(TYPE, HalfSlabType.BOTTOM);
        if(clickPos <= 0.5) return newState.setValue(TYPE, HalfSlabType.MIDDLE_BOTTOM);
        if(clickPos <= 0.75) return newState.setValue(TYPE, HalfSlabType.MIDDLE_TOP);
        return newState.setValue(TYPE, HalfSlabType.TOP);

    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pUseContext) {
        ItemStack itemstack = pUseContext.getItemInHand();
        HalfSlabType type = pState.getValue(TYPE);
        if (type != HalfSlabType.FULL && itemstack.is(this.asItem())) {

            if (pUseContext.replacingClickedOnBlock()) {
                double clickPos = pUseContext.getClickLocation().y - (double)pUseContext.getClickedPos().getY();
                Direction direction = pUseContext.getClickedFace();

                return switch (type) {
                    case TOP ->            direction == Direction.DOWN                               || ((clickPos <= 0.75) && direction.getAxis().isHorizontal());
                    case TOP_TWO ->        direction == Direction.DOWN                               || ((clickPos <= 0.5) && direction.getAxis().isHorizontal());
                    case TOP_THREE ->      direction == Direction.DOWN                               || ((clickPos <= 0.25) && direction.getAxis().isHorizontal());
                    case MIDDLE_TOP ->    (direction == Direction.DOWN || direction == Direction.UP) || (clickPos > 0.75 || clickPos <= 0.5);
                    case MIDDLE_TWO ->    (direction == Direction.DOWN || direction == Direction.UP) || (clickPos > 0.75 || clickPos <= 0.25);
                    case MIDDLE_BOTTOM -> (direction == Direction.DOWN || direction == Direction.UP) || (clickPos > 0.5 || clickPos <= 0.25);
                    case BOTTOM ->         direction == Direction.UP                                 || ((clickPos > 0.25) && direction.getAxis().isHorizontal());
                    case BOTTOM_TWO ->     direction == Direction.UP                                 || ((clickPos > 0.5) && direction.getAxis().isHorizontal());
                    case BOTTOM_THREE ->   direction == Direction.UP                                 || ((clickPos > 0.75) && direction.getAxis().isHorizontal());
                    default -> throw new IllegalStateException("Unexpected value: " + type);
                };
            }
            return true;
        }
        return false;
    }

    private HalfSlabType oneDown(HalfSlabType other) {
        return switch(other) {
            case TOP -> HalfSlabType.TOP_TWO;
            case TOP_TWO -> HalfSlabType.TOP_THREE;
            case TOP_THREE -> HalfSlabType.FULL;
            case MIDDLE_TOP -> HalfSlabType.MIDDLE_TWO;
            case MIDDLE_BOTTOM -> HalfSlabType.BOTTOM_TWO;
            case MIDDLE_TWO -> HalfSlabType.BOTTOM_THREE;
            default -> throw new IllegalStateException("No state lower than state " + other);
        };
    }
    private HalfSlabType oneUp(HalfSlabType other) {
        return switch (other) {
            case BOTTOM -> HalfSlabType.BOTTOM_TWO;
            case BOTTOM_TWO -> HalfSlabType.BOTTOM_THREE;
            case BOTTOM_THREE -> HalfSlabType.FULL;
            case MIDDLE_BOTTOM -> HalfSlabType.MIDDLE_TWO;
            case MIDDLE_TOP -> HalfSlabType.TOP_TWO;
            case MIDDLE_TWO -> HalfSlabType.TOP_THREE;
            default -> throw new IllegalStateException("No state higher than state " + other);
        };
    }



    // ============================ //
    // Mostly copied from SlabBlock //
    // ============================ //

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return pState.getValue(TYPE).getShape();
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return pState.getValue(TYPE) != HalfSlabType.FULL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return pState.getValue(TYPE) != HalfSlabType.FULL && SimpleWaterloggedBlock.super.placeLiquid(pLevel, pPos, pState, pFluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return pState.getValue(TYPE) != HalfSlabType.FULL && SimpleWaterloggedBlock.super.canPlaceLiquid(pLevel, pPos, pState, pFluid);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        if (pType == PathComputationType.WATER) {
            return pLevel.getFluidState(pPos).is(FluidTags.WATER);
        }
        return false;
    }


    public enum HalfSlabType implements StringRepresentable {
        TOP("top", 12, 16),
        TOP_TWO("top_two", 8, 16),
        TOP_THREE("top_three", 4, 16),
        MIDDLE_TOP("middle_top", 8, 12),
        MIDDLE_TWO("middle_two", 4, 12),
        MIDDLE_BOTTOM("middle_bottom", 4, 8),
        BOTTOM("bottom", 0, 4),
        BOTTOM_TWO("bottom_two", 0, 8),
        BOTTOM_THREE("bottom_three", 0, 12),
        FULL("full"),
        ;


        private final String name;
        private final VoxelShape shape;
        private final double y1;
        private final double y2;

        private HalfSlabType(String pName) {
            this(pName, 0, 16);
        }
        private HalfSlabType(String pName, double y1, double y2) {
            this.name = pName;
            this.y1 = y1;
            this.y2 = y2;
            this.shape = Block.box(0, y1, 0, 16, y2, 16);
        }

        public String toString() {
            return this.name;
        }

        public @NotNull String getSerializedName() {
            return this.name;
        }

        public VoxelShape getShape() {
            return shape;
        }

        public double y1() {
            return y1;
        }

        public double y2() {
            return y2;
        }
    }
}
