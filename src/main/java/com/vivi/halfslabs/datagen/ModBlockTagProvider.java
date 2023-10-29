package com.vivi.halfslabs.datagen;

import com.vivi.halfslabs.HalfSlabs;
import com.vivi.halfslabs.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.IReverseTag;

import java.util.List;
import java.util.stream.Stream;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, HalfSlabs.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {


        List<Block> mineablePickaxe = List.of(
                "half_slabs:prismarine_half_slab",
                "half_slabs:prismarine_brick_half_slab",
                "half_slabs:dark_prismarine_half_slab",
                "half_slabs:stone_half_slab",
                "half_slabs:smooth_stone_half_slab",
                "half_slabs:sandstone_half_slab",
                "half_slabs:cut_sandstone_half_slab",
                "half_slabs:cobblestone_half_slab",
                "half_slabs:brick_half_slab",
                "half_slabs:stone_brick_half_slab",
                "half_slabs:nether_brick_half_slab",
                "half_slabs:quartz_half_slab",
                "half_slabs:red_sandstone_half_slab",
                "half_slabs:cut_red_sandstone_half_slab",
                "half_slabs:purpur_half_slab",
                "half_slabs:polished_granite_half_slab",
                "half_slabs:smooth_red_sandstone_half_slab",
                "half_slabs:mossy_stone_brick_half_slab",
                "half_slabs:polished_diorite_half_slab",
                "half_slabs:mossy_cobblestone_half_slab",
                "half_slabs:end_stone_brick_half_slab",
                "half_slabs:smooth_sandstone_half_slab",
                "half_slabs:smooth_quartz_half_slab",
                "half_slabs:granite_half_slab",
                "half_slabs:andesite_half_slab",
                "half_slabs:red_nether_brick_half_slab",
                "half_slabs:polished_andesite_half_slab",
                "half_slabs:diorite_half_slab",
                "half_slabs:blackstone_half_slab",
                "half_slabs:polished_blackstone_brick_half_slab",
                "half_slabs:polished_blackstone_half_slab",
                "half_slabs:oxidized_cut_copper_half_slab",
                "half_slabs:weathered_cut_copper_half_slab",
                "half_slabs:exposed_cut_copper_half_slab",
                "half_slabs:cut_copper_half_slab",
                "half_slabs:waxed_oxidized_cut_copper_half_slab",
                "half_slabs:waxed_weathered_cut_copper_half_slab",
                "half_slabs:waxed_exposed_cut_copper_half_slab",
                "half_slabs:waxed_cut_copper_half_slab",
                "half_slabs:cobbled_deepslate_half_slab",
                "half_slabs:polished_deepslate_half_slab",
                "half_slabs:deepslate_tile_half_slab",
                "half_slabs:deepslate_brick_half_slab",
                "half_slabs:mud_brick_half_slab"
        ).stream().map(id -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id))).toList();
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(mineablePickaxe.toArray(new Block[0]));

        List<Block> mineableAxe = List.of(
                "half_slabs:oak_half_slab",
                "half_slabs:spruce_half_slab",
                "half_slabs:birch_half_slab",
                "half_slabs:jungle_half_slab",
                "half_slabs:acacia_half_slab",
                "half_slabs:dark_oak_half_slab",
                "half_slabs:crimson_half_slab",
                "half_slabs:warped_half_slab",
                "half_slabs:mangrove_half_slab"
        ).stream().map(id -> ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id))).toList();
        this.tag(BlockTags.MINEABLE_WITH_AXE).add(mineableAxe.toArray(new Block[0]));




    }
}