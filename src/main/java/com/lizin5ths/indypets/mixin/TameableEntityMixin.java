package com.lizin5ths.indypets.mixin;

import com.lizin5ths.indypets.Follower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin extends AnimalEntity implements Follower {
    @Unique
    private static TrackedData<Boolean> ALLOWED_TO_FOLLOW;

    // Required to compile:
    protected TameableEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // These three injects just add similar code for tracking selective following.
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void injectStatic(CallbackInfo callbackInfo) {
        ALLOWED_TO_FOLLOW = DataTracker.registerData(TameableEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void initFollowData(CallbackInfo callbackInfo) {
        TameableEntity self = (TameableEntity) (Object) this;
        self.getDataTracker().startTracking(ALLOWED_TO_FOLLOW, false);
    }
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeFollowDataToNbt(NbtCompound tag, CallbackInfo callbackInfo) {
        tag.putBoolean("AllowedToFollow", isFollowing());
    }
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readFollowDataFromNbt(NbtCompound tag, CallbackInfo callbackInfo) {
        setFollowing(tag.getBoolean("AllowedToFollow"));
    }

    // Using AllowedToFollowAccessor to pass this function to FollowOwnerGoalMixin.
    @Unique
    @Override
    public boolean isFollowing() {
        TameableEntity self = (TameableEntity) (Object) this;
        return self.getDataTracker().get(ALLOWED_TO_FOLLOW);
    }

    @Unique
    @Override
    public void setFollowing(boolean value) {
        TameableEntity self = (TameableEntity) (Object) this;
        self.getDataTracker().set(ALLOWED_TO_FOLLOW, value);
    }

}
