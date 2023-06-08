package net.veroxuniverse.ancientmyths.block.tile;

import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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


public class AncientPedestalTile extends ArcanePedestalTile implements IAnimatable {

    protected ItemStack item;
    public ItemEntity renderEntity;

    private static final AnimationBuilder IDLE = new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);

    //ItemStack item;
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
    public AnimationFactory getFactory() {
        return this.FACTORY;
    }

}