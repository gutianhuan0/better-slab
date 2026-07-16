package com.betterslab.client.render;

import com.betterslab.block.MergedSlabBlock;
import com.betterslab.block.entity.MergedSlabEntity;
import com.betterslab.block.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;










public class MergedSlabRenderer implements BlockEntityRenderer<MergedSlabEntity> {

    public static void register() {
        BlockEntityRendererFactories.register(
                ModBlockEntities.MERGED_SLAB_ENTITY,
                ctx -> new MergedSlabRenderer()
        );
    }

    @Override
    public void render(MergedSlabEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        MergedSlabBlock.Orientation orientation = state.get(MergedSlabBlock.ORIENTATION);
        Direction facing = state.get(MergedSlabBlock.FACING);
        Block slabA = entity.getSlabA();
        Block slabB = entity.getSlabB();

        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager brm = client.getBlockRenderManager();

        if (orientation == MergedSlabBlock.Orientation.HORIZONTAL) {
            
            BlockState stateA = getSlabState(slabA, SlabType.BOTTOM);
            renderBlock(brm, stateA, matrices, vertexConsumers, light, overlay);
            
            BlockState stateB = getSlabState(slabB, SlabType.TOP);
            renderBlock(brm, stateB, matrices, vertexConsumers, light, overlay);
        } else {
            
            BlockState stateA = getSlabState(slabA, SlabType.BOTTOM);
            BlockState stateB = getSlabState(slabB, SlabType.BOTTOM);
            renderVertical(brm, stateA, facing, matrices, vertexConsumers, light, overlay);
            renderVertical(brm, stateB, facing.getOpposite(), matrices, vertexConsumers, light, overlay);
        }
    }

    
    private BlockState getSlabState(Block slab, SlabType type) {
        if (slab == null) {
            return net.minecraft.block.Blocks.AIR.getDefaultState();
        }
        BlockState state = slab.getDefaultState();
        if (state.contains(SlabBlock.TYPE)) {
            return state.with(SlabBlock.TYPE, type);
        }
        return state;
    }

    
    private void renderBlock(BlockRenderManager brm, BlockState state,
                             MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                             int light, int overlay) {
        matrices.push();
        try {
            brm.renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);
        } catch (Throwable ignored) {
            
        }
        matrices.pop();
    }

    
    private void renderVertical(BlockRenderManager brm, BlockState state, Direction facing,
                                MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                                int light, int overlay) {
        matrices.push();
        applyVerticalTransform(matrices, facing);
        try {
            brm.renderBlockAsEntity(state, matrices, vertexConsumers, light, overlay);
        } catch (Throwable ignored) {
            
        }
        matrices.pop();
    }

    




    private void applyVerticalTransform(MatrixStack matrices, Direction facing) {
        switch (facing) {
            case NORTH -> {
                matrices.translate(0, 1, 0);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            }
            case SOUTH -> {
                matrices.translate(0, 0, 1);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
            }
            case EAST -> {
                matrices.translate(1, 0, 0);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
            }
            case WEST -> {
                matrices.translate(0, 1, 0);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90));
            }
            default -> {}
        }
    }

    @Override
    public boolean rendersOutsideBoundingBox(MergedSlabEntity entity) {
        return true;
    }
}
