package com.betterslab.mixin;

import com.betterslab.config.BetterSlabConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlabBlock.class)
public class SlabBlockMixin {

    













    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        if (BetterSlabConfig.get().perfectPlacement) {
            return VoxelShapes.fullCube();
        }
        
        return switch (state.get(SlabBlock.TYPE)) {
            case BOTTOM -> Block.createCuboidShape(0, 0, 0, 16, 8, 16);
            case TOP -> Block.createCuboidShape(0, 8, 0, 16, 16, 16);
            default -> VoxelShapes.fullCube();
        };
    }
}
