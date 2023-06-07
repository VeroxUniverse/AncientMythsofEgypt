package net.veroxuniverse.ancientmyths.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.veroxuniverse.ancientmyths.block.BlocksEntitiesRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


public class AncientPedestalTile extends BlockEntity implements WorldlyContainer, IAnimatable {

    protected ItemStack stack = ItemStack.EMPTY;
    public ItemEntity renderEntity;


    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
    ItemStack item;
    private final AnimationFactory FACTORY = GeckoLibUtil.createFactory(this);

    public AncientPedestalTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(BlocksEntitiesRegistry.ANCIENT_PEDESTAL_TILE.get(), pWorldPosition, pBlockState);
        this.item = ItemStack.EMPTY;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller",
                0, event -> {
            event.getController().setAnimation(IDLE);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public int[] getSlotsForFace(Direction pSide) {
        return new int[0];
    }

    @Override
    public boolean canPlaceItemThroughFace(int pIndex, ItemStack pItemStack, @Nullable Direction pDirection) {
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int pIndex, ItemStack pStack, Direction pDirection) {
        return false;
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.item.isEmpty();
    }

    @Override
    public ItemStack getItem(int i) {
        return i == 0 ? this.item : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int i, int j) {
        ItemStack item = this.removeItemNoUpdate(i);
        this.update(j);
        return item;
    }

    public void update(int j) {
        this.setChanged();
        if (this.getLevel() != null) {
            this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), j);
        }

    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        if (i == 0) {
            ItemStack item1 = this.item;
            this.item = ItemStack.EMPTY;
            return item1;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        if (i == 0) {
            this.item = itemStack;
            this.setChanged();
        }
    }

    public void clearContent() {
        this.item = ItemStack.EMPTY;
    }


    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("item")) {
            this.item = ItemStack.of(compoundTag.getCompound("item"));
        }

    }

    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.item.isEmpty()) {
            compoundTag.put("item", this.item.save(new CompoundTag()));
        }

    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    public boolean stillValid(Player player) {
        return this.worldPosition.distSqr(player.blockPosition()) <= 16.0;
    }

    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.FACTORY;
    }

}