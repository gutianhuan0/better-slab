package com.betterslab;

import com.betterslab.block.GenericVerticalSlabBlock;
import com.betterslab.block.ModBlocks;
import com.betterslab.block.entity.GenericVerticalSlabEntity;
import com.betterslab.block.entity.ModBlockEntities;
import com.betterslab.config.BetterSlabConfig;
import com.betterslab.handler.BreakBlockHandler;
import com.betterslab.networking.ModNetworking;
import com.placeanywhere.core.FreeBlocks;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.SlabBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterSlab implements ModInitializer {
    public static final String MOD_ID = "betterslab";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        BetterSlabConfig.load();
        ModBlocks.registerModBlocks();
        ModBlockEntities.register();
        ModNetworking.registerServerListeners();
        BreakBlockHandler.register();






        FreeBlocks.placeCallback = (world, x, y, z, qx, qy, qz, qw, state) -> {
            if (!BetterSlabConfig.get().verticalSlab) return null;
            if (!(state.getBlock() instanceof SlabBlock)) return null;

            net.minecraft.block.Block sourceSlab = state.getBlock();
            Direction facing = FreeBlocks.placeFacing.get();
            if (facing == null) facing = Direction.NORTH;

            var verticalState = ModBlocks.GENERIC_VERTICAL_SLAB.getDefaultState()
                    .with(GenericVerticalSlabBlock.FACING, facing)
                    .with(GenericVerticalSlabBlock.DOUBLE, false)
                    .with(GenericVerticalSlabBlock.WATERLOGGED, false);


            NbtCompound fullNbt;
            try {
                var be = ((net.minecraft.block.BlockEntityProvider) ModBlocks.GENERIC_VERTICAL_SLAB)
                        .createBlockEntity(BlockPos.ofFloored(x, y, z), verticalState);
                if (be instanceof GenericVerticalSlabEntity gvsEntity) {
                    gvsEntity.setSourceSlab(sourceSlab);
                    be.setWorld(world);
                    fullNbt = be.createNbtWithId(world.getRegistryManager());
                } else {
                    fullNbt = new NbtCompound();
                    fullNbt.putString("source_slab", Registries.BLOCK.getId(sourceSlab).toString());
                }
            } catch (Throwable t) {
                fullNbt = new NbtCompound();
                fullNbt.putString("source_slab", Registries.BLOCK.getId(sourceSlab).toString());
                LOGGER.error("[BetterSlab] 生成竖半砖 NBT 失败", t);
            }

            LOGGER.debug("[BetterSlab] 半砖→竖半砖: {} → facing={}", sourceSlab, facing);
            return new FreeBlocks.PlaceResult(verticalState, fullNbt);
        };

        LOGGER.info("Better Slab mod initialized!");
    }
}
