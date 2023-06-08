package net.veroxuniverse.ancientmyths.block.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.veroxuniverse.ancientmyths.block.BlocksEntitiesRegistry;
import net.veroxuniverse.ancientmyths.block.BlocksRegistry;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class AncientPedestal extends BaseEntityBlock implements SimpleWaterloggedBlock {

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

    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return SHAPE;
    }

    public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
        return !groundState.is(BlocksRegistry.ANCIENT_PEDESTAL.get());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelReader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState groundState = levelReader.getBlockState(blockpos);
        return this.mayPlaceOn(groundState, levelReader, blockpos);
    }

    public InteractionResult use(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack heldItem = player.getItemInHand(handIn);
        if (!worldIn.isClientSide && worldIn.getBlockEntity(pos) instanceof AncientPedestalTile ancientPedestalTile && (!player.isShiftKeyDown() && heldItem.getItem() != this.asItem())) {
            if (handIn != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            if(ancientPedestalTile.getItem(0).isEmpty()){
                ancientPedestalTile.setItem(0, stack);
                if(!player.isCreative()){
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }else{
                if(!player.isCreative()){
                    popResource(worldIn, pos, ancientPedestalTile.getItem(0).copy());
                }
                ancientPedestalTile.setItem(0, ItemStack.EMPTY);
                if (heldItem.getItem() != null) {
                    ancientPedestalTile.setItem(0, stack);
                    if(!player.isCreative()){
                        heldItem.shrink(1);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
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

    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tileentity = worldIn.getBlockEntity(pos);
        if (tileentity instanceof AncientPedestalTile) {
            Containers.dropContents(worldIn, pos, (AncientPedestalTile) tileentity);
            worldIn.updateNeighbourForOutputSignal(pos, this);
        }
        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

}
