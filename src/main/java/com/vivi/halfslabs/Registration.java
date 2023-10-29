package com.vivi.halfslabs;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = HalfSlabs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registration {

    public static final Map<ResourceLocation, Pair<Supplier<Block>, Supplier<Block>>> BLOCK_MAP = new HashMap<>();


    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HalfSlabs.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HalfSlabs.MOD_ID);

    static {

        register("prismarine", Blocks.PRISMARINE);
        register("prismarine_brick", Blocks.PRISMARINE_BRICKS);
        register("dark_prismarine", Blocks.DARK_PRISMARINE);
        register("oak", Blocks.OAK_PLANKS);
        register("spruce", Blocks.SPRUCE_PLANKS);
        register("birch", Blocks.BIRCH_PLANKS);
        register("jungle", Blocks.JUNGLE_PLANKS);
        register("acacia", Blocks.ACACIA_PLANKS);
        register("dark_oak", Blocks.DARK_OAK_PLANKS);
        register("mangrove", Blocks.MANGROVE_PLANKS);
        register("stone", Blocks.STONE);
        register("smooth_stone", Blocks.SMOOTH_STONE);
        register("sandstone", Blocks.SANDSTONE);
        register("cut_sandstone", Blocks.CUT_SANDSTONE);
        register("cobblestone", Blocks.COBBLESTONE);
        register("brick", Blocks.BRICKS);
        register("stone_brick", Blocks.STONE_BRICKS);
        register("mud_brick", Blocks.MUD_BRICKS);
        register("nether_brick", Blocks.NETHER_BRICKS);
        register("quartz", Blocks.QUARTZ_BLOCK);
        register("red_sandstone", Blocks.RED_SANDSTONE);
        register("cut_red_sandstone", Blocks.CUT_RED_SANDSTONE);
        register("purpur", Blocks.PURPUR_BLOCK);
        register("polished_granite", Blocks.POLISHED_GRANITE);
        register("smooth_red_sandstone", Blocks.SMOOTH_RED_SANDSTONE);
        register("mossy_stone_brick", Blocks.MOSSY_STONE_BRICKS);
        register("polished_diorite", Blocks.POLISHED_DIORITE);
        register("mossy_cobblestone", Blocks.MOSSY_COBBLESTONE);
        register("end_stone_brick", Blocks.END_STONE_BRICKS);
        register("smooth_sandstone", Blocks.SMOOTH_SANDSTONE);
        register("smooth_quartz", Blocks.SMOOTH_QUARTZ);
        register("granite", Blocks.GRANITE);
        register("andesite", Blocks.ANDESITE);
        register("red_nether_brick", Blocks.RED_NETHER_BRICKS);
        register("polished_andesite", Blocks.POLISHED_ANDESITE);
        register("diorite", Blocks.DIORITE);
        register("crimson", Blocks.CRIMSON_PLANKS);
        register("warped", Blocks.WARPED_PLANKS);
        register("blackstone", Blocks.BLACKSTONE);
        register("polished_blackstone_brick", Blocks.POLISHED_BLACKSTONE_BRICKS);
        register("polished_blackstone", Blocks.POLISHED_BLACKSTONE);
        register("oxidized_cut_copper", Blocks.OXIDIZED_CUT_COPPER);
        register("weathered_cut_copper", Blocks.WEATHERED_CUT_COPPER);
        register("exposed_cut_copper", Blocks.EXPOSED_CUT_COPPER);
        register("cut_copper", Blocks.CUT_COPPER);
        register("waxed_oxidized_cut_copper", Blocks.WAXED_OXIDIZED_CUT_COPPER);
        register("waxed_weathered_cut_copper", Blocks.WAXED_WEATHERED_CUT_COPPER);
        register("waxed_exposed_cut_copper", Blocks.WAXED_EXPOSED_CUT_COPPER);
        register("waxed_cut_copper", Blocks.WAXED_CUT_COPPER);
        register("cobbled_deepslate", Blocks.COBBLED_DEEPSLATE);
        register("polished_deepslate", Blocks.POLISHED_DEEPSLATE);
        register("deepslate_tile", Blocks.DEEPSLATE_TILES);
        register("deepslate_brick", Blocks.DEEPSLATE_BRICKS);
    }

    private static RegistryObject<Block> register(String name, Block base) {
        return register(name + "_half_slab", () -> new HalfSlabBlock(BlockBehaviour.Properties.copy(base)), base);
    }

    private static RegistryObject<Block> register(String name, Supplier<Block> block, Block base) {
        RegistryObject<Block> out = BLOCKS.register(name, block);
        BLOCK_MAP.put(new ResourceLocation(HalfSlabs.MOD_ID, name), new Pair<>(out, () -> base));
        return out;
    }

    public static void init(IEventBus eventBus) {
        BLOCKS.register(eventBus);

        BLOCKS.getEntries().forEach(block -> {
            ITEMS.register(block.getKey().location().getPath(), () -> new BlockItem(block.get(), new Item.Properties().tab(HalfSlabs.TAB)));
        });

        ITEMS.register(eventBus);
    }
}
