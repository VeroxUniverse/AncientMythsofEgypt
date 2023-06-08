package net.veroxuniverse.ancientmyths.block.custom;

import com.hollingsworth.arsnouveau.common.block.ModBlock;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.veroxuniverse.ancientmyths.block.BlocksEntitiesRegistry;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class AncientPedestal extends ModBlock implements EntityBlock, SimpleWaterloggedBlock {

    public AncientPedestal(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(12, 9, 7, 14, 10, 9),
            Block.box(10, 0, 7, 12, 7, 9),
            Block.box(10, 7, 7, 14, 9, 9),
            Block.box(12, 0, 7, 15, 2, 9),
            Block.box(13, 2, 7, 15, 3, 9),
            Block.box(7, 9, 12, 9, 10, 14),
            Block.box(7, 0, 10, 9, 7, 12),
            Block.box(7, 7, 10, 9, 9, 14),
            Block.box(7, 0, 12, 9, 2, 15),
            Block.box(7, 2, 13, 9, 3, 15),
            Block.box(7, 9, 2, 9, 10, 4),
            Block.box(7, 0, 4, 9, 7, 6),
            Block.box(7, 7, 2, 9, 9, 6),
            Block.box(7, 0, 1, 9, 2, 4),
            Block.box(7, 2, 1, 9, 3, 3),
            Block.box(2, 9, 7, 4, 10, 9),
            Block.box(4, 0, 7, 6, 7, 9),
            Block.box(2, 7, 7, 6, 9, 9),
            Block.box(1, 0, 7, 4, 2, 9),
            Block.box(1, 2, 7, 3, 3, 9),
            Block.box(7, 6, 7, 9, 7, 9),
            Block.box(6, 2, 6, 10, 6, 10),
            Block.box(6, 0, 6, 10, 2, 10),
            Block.box(5, 0, 5, 6, 1, 7),
            Block.box(5, 0, 9, 6, 1, 11),
            Block.box(10, 0, 9, 11, 1, 11),
            Block.box(10, 0, 5, 11, 1, 7),
            Block.box(6, 0, 5, 7, 1, 6),
            Block.box(6, 0, 10, 7, 1, 11),
            Block.box(9, 0, 10, 10, 1, 11),
            Block.box(9, 0, 5, 10, 1, 6),
            Block.box(3, 10, 3, 13, 12, 13),
            Block.box(7, 7.5, 7, 9, 9.5, 9)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @NotNull InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (handIn != InteractionHand.MAIN_HAND)
            return InteractionResult.PASS;
        if (!world.isClientSide && world.getBlockEntity(pos) instanceof AncientPedestalTile tile) {
            if (tile.getStack() != null && player.getItemInHand(handIn).isEmpty()) {
                ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.getStack());
                world.addFreshEntity(item);
                tile.setStack(ItemStack.EMPTY);
            } else if (!player.getInventory().getSelected().isEmpty()) {
                if (tile.getStack() != null) {
                    ItemEntity item = new ItemEntity(world, player.getX(), player.getY(), player.getZ(), tile.getStack());
                    world.addFreshEntity(item);
                }
                tile.setStack(player.getInventory().removeItem(player.getInventory().selected, 1));
            }
            world.sendBlockUpdated(pos, state, state, 2);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<net.minecraft.world.level.block.Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @NotNull
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState stateIn, @NotNull Direction side, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return stateIn;
    }

    @Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(@NotNull BlockState blockState, Level worldIn, @NotNull BlockPos pos) {
        ArcanePedestalTile tile = (ArcanePedestalTile) worldIn.getBlockEntity(pos);
        if (tile == null || tile.getStack().isEmpty()) return 0;
        return 15;
    }

    @Override
    public void neighborChanged(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Block pBlock, @NotNull BlockPos pFromPos, boolean pIsMoving) {
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
        if (!pLevel.isClientSide && pLevel.getBlockEntity(pPos) instanceof ArcanePedestalTile tile) {
            if(tile.hasSignal != pLevel.hasNeighborSignal(pPos)) {
                tile.hasSignal = !tile.hasSignal;
                tile.updateBlock();
            }
        }
    }

    @Override
    public void playerWillDestroy(@NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        if (worldIn.getBlockEntity(pos) instanceof AncientPedestalTile tile && tile.getStack() != null) {
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), tile.getStack()));
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return BlocksEntitiesRegistry.ANCIENT_PEDESTAL_TILE.get().create(pPos, pState);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }


}
