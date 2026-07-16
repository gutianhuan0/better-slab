package com.betterslab.block;

import com.betterslab.BetterSlab;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ModBlocks {

    
    private static final HashMap<Block, Block> VANILLA_TO_VERTICAL = new HashMap<>();

    
    private static final HashMap<Block, Block> VERTICAL_TO_VANILLA = new HashMap<>();

    
    public static Block GENERIC_VERTICAL_SLAB;

    
    public static Block MERGED_SLAB;

    private static final Block[] VANILLA_SLABS = {
            Blocks.OAK_SLAB, Blocks.SPRUCE_SLAB, Blocks.BIRCH_SLAB, Blocks.JUNGLE_SLAB,
            Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB, Blocks.MANGROVE_SLAB, Blocks.CHERRY_SLAB,
            Blocks.BAMBOO_SLAB, Blocks.BAMBOO_MOSAIC_SLAB, Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB,
            Blocks.STONE_SLAB, Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.STONE_BRICK_SLAB,
            Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.GRANITE_SLAB, Blocks.POLISHED_GRANITE_SLAB, Blocks.DIORITE_SLAB,
            Blocks.POLISHED_DIORITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.SANDSTONE_SLAB,
            Blocks.CUT_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE_SLAB,
            Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.BRICK_SLAB, Blocks.NETHER_BRICK_SLAB, Blocks.RED_NETHER_BRICK_SLAB,
            Blocks.QUARTZ_SLAB, Blocks.SMOOTH_QUARTZ_SLAB, Blocks.PURPUR_SLAB, Blocks.PRISMARINE_SLAB,
            Blocks.PRISMARINE_BRICK_SLAB, Blocks.DARK_PRISMARINE_SLAB, Blocks.END_STONE_BRICK_SLAB, Blocks.BLACKSTONE_SLAB,
            Blocks.POLISHED_BLACKSTONE_SLAB, Blocks.POLISHED_BLACKSTONE_BRICK_SLAB, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_TILE_SLAB,
            Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.MUD_BRICK_SLAB, Blocks.TUFF_SLAB,
            Blocks.POLISHED_TUFF_SLAB, Blocks.TUFF_BRICK_SLAB
    };

    private static final String SLAB_SUFFIX = "_slab";
    private static final String VERTICAL_SUFFIX = "_vertical_slab";

    public static void registerModBlocks() {
        
        for (Block vanillaSlab : VANILLA_SLABS) {
            Identifier vanillaId = Registries.BLOCK.getId(vanillaSlab);
            String path = vanillaId.getPath();
            String baseName = path.substring(0, path.length() - SLAB_SUFFIX.length());
            Identifier id = Identifier.of(BetterSlab.MOD_ID, baseName + VERTICAL_SUFFIX);

            Block verticalSlab = Registry.register(
                    Registries.BLOCK,
                    id,
                    new VerticalSlabBlock(AbstractBlock.Settings.copy(vanillaSlab))
            );

            Registry.register(
                    Registries.ITEM,
                    id,
                    new BlockItem(verticalSlab, new Item.Settings())
            );

            VANILLA_TO_VERTICAL.put(vanillaSlab, verticalSlab);
            VERTICAL_TO_VANILLA.put(verticalSlab, vanillaSlab);
        }

        
        GENERIC_VERTICAL_SLAB = Registry.register(
                Registries.BLOCK,
                Identifier.of(BetterSlab.MOD_ID, "generic_vertical_slab"),
                new GenericVerticalSlabBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque())
        );

        
        MERGED_SLAB = Registry.register(
                Registries.BLOCK,
                Identifier.of(BetterSlab.MOD_ID, "merged_slab"),
                new MergedSlabBlock(AbstractBlock.Settings.copy(Blocks.STONE).nonOpaque())
        );
    }

    
    public static Block getVerticalSlab(Block vanillaSlab) {
        return VANILLA_TO_VERTICAL.get(vanillaSlab);
    }

    
    public static Block getVanillaSlab(Block verticalSlab) {
        return VERTICAL_TO_VANILLA.get(verticalSlab);
    }

    
    public static boolean hasDedicatedVertical(Block slab) {
        return VANILLA_TO_VERTICAL.containsKey(slab);
    }
}
