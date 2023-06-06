package net.veroxuniverse.ancientmyths.block.custom;

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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.veroxuniverse.ancientmyths.block.BlocksEntitiesRegistry;
import net.veroxuniverse.ancientmyths.block.BlocksRegistry;
import net.veroxuniverse.ancientmyths.block.tile.AncientPedestalTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AncientPedestal extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public AncientPedestal(Properties properties) {
        super(properties);
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
        if (worldIn.getBlockEntity(pos) instanceof AncientPedestalTile ancientPedestalTile && (!player.isShiftKeyDown()  && heldItem.getItem() != this.asItem())) {
            ItemStack stack = heldItem.copy();
            stack.setCount(1);
            if(ancientPedestalTile.getItem(0).isEmpty()){
                ancientPedestalTile.setItem(0, stack);
                if(!player.isCreative()){
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }else if(ancientPedestalTile.getItem(0).sameItem(stack) && ancientPedestalTile.getItem(0).getMaxStackSize() > ancientPedestalTile.getItem(0).getCount() + stack.getCount()){
                ancientPedestalTile.getItem(0).grow(1);
                if(!player.isCreative()){
                    heldItem.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }else{
                popResource(worldIn, pos, ancientPedestalTile.getItem(0).copy());
                ancientPedestalTile.setItem(0, ItemStack.EMPTY);
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
