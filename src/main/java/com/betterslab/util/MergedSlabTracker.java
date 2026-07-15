package com.betterslab.util;

import com.betterslab.BetterSlab;
import com.betterslab.block.MergedSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MergedSlabTracker {
    private static final Map<String, MergedEntry> mergedData = new ConcurrentHashMap<>();

    public record MergedEntry(Block slab, SlabType type) {}

    private static String key(World world, BlockPos pos) {
        return world.getRegistryKey().getValue().toString() + ":" + pos.getX() + "," + pos.getY() + "," + pos.getZ();
    }

    public static void setMerged(World world, BlockPos pos, Block secondSlab, SlabType secondType) {
        mergedData.put(key(world, pos), new MergedEntry(secondSlab, secondType));
        BetterSlab.LOGGER.debug("Merged slab tracked at {} with {} ({})", pos, secondSlab, secondType);
    }

    public static void setMerged(World world, BlockPos pos, Block secondSlab) {
        setMerged(world, pos, secondSlab, SlabType.TOP);
    }

    public static MergedEntry getMerged(World world, BlockPos pos) {
        return mergedData.get(key(world, pos));
    }

    public static void remove(World world, BlockPos pos) {
        mergedData.remove(key(world, pos));
    }

    public static boolean hasMerged(World world, BlockPos pos) {
        return mergedData.containsKey(key(world, pos));
    }

    public static boolean isMergedSlabBlock(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() instanceof MergedSlabBlock;
    }
}
