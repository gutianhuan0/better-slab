package com.betterslab.client.render;

import com.betterslab.block.GenericVerticalSlabBlock;
import com.betterslab.block.entity.GenericVerticalSlabEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.BlockPos;





public class GenericVerticalSlabRenderer implements BlockEntityRenderer<GenericVerticalSlabEntity> {

    public static void register() {
        BlockEntityRendererFactories.register(
                com.betterslab.block.entity.ModBlockEntities.GENERIC_VERTICAL_SLAB_ENTITY,
                ctx -> new GenericVerticalSlabRenderer()
        );
    }

    @Override
    public void render(GenericVerticalSlabEntity entity, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState state = entity.getCachedState();
        Direction facing = state.get(GenericVerticalSlabBlock.FACING);
        boolean dbl = state.get(GenericVerticalSlabBlock.DOUBLE);
        BlockState sourceState = entity.getSourceSlabState();

        MinecraftClient client = MinecraftClient.getInstance();
        BlockRenderManager brm = client.getBlockRenderManager();

        
        renderHalf(brm, sourceState, facing, matrices, vertexConsumers, light, overlay);

        if (dbl) {
            
            renderHalf(brm, sourceState, facing.getOpposite(), matrices, vertexConsumers, light, overlay);
        }
    }

    


    private void renderHalf(BlockRenderManager brm, BlockState sourceState, Direction facing,
                            MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                            int light, int overlay) {
        matrices.push();
        applyVerticalTransform(matrices, facing);
        try {
            brm.renderBlockAsEntity(sourceState, matrices, vertexConsumers, light, overlay);
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
    public boolean rendersOutsideBoundingBox(GenericVerticalSlabEntity entity) {
        return true;
    }
}
