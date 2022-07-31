package mine.block.woof.entity;

import mine.block.woof.block.DogBowlBlock;
import mine.block.woof.block.WoofBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class DogEatOutBowlGoal extends MoveToTargetPosGoal {
    private TameableEntity wolf;

    public DogEatOutBowlGoal(WolfEntity mob, double speed) {
        super(mob, speed, 16);
        this.wolf = mob;
    }

    @Override
    public boolean canStart() {
        return !this.wolf.isSitting() && super.canStart();
    }

    @Override
    public void tick() {
        super.tick();
        if(hasReached()) {
            if(!wolf.getWorld().getBlockState(targetPos).isOf(WoofBlocks.DOG_BOWL_BLOCK)) return;
            BlockState state = wolf.getWorld().getBlockState(targetPos).with(DogBowlBlock.FILLED, false);
            wolf.getWorld().setBlockState(targetPos, state);
            wolf.getWorld().playSound(wolf.getX(), wolf.getY(), wolf.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1f, 1f, true);
        }
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(WoofBlocks.DOG_BOWL_BLOCK) && world.getBlockState(pos).get(DogBowlBlock.FILLED);
    }
}