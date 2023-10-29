package com.vivi.halfslabs.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vivi.halfslabs.HalfSlabBlock;
import com.vivi.halfslabs.HalfSlabBlock.HalfSlabType;
import com.vivi.halfslabs.HalfSlabs;
import com.vivi.halfslabs.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ModBlockstateProvider extends BlockStateProvider {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final DataGenerator gen;

    public ModBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, HalfSlabs.MOD_ID, exFileHelper);
        this.gen = gen;
    }

    @Override
    protected void registerStatesAndModels() {

        List<Block> blacklist = List.of(
                Blocks.QUARTZ_BLOCK,
                Blocks.SMOOTH_RED_SANDSTONE,
                Blocks.SMOOTH_SANDSTONE,
                Blocks.SMOOTH_QUARTZ,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER,
                Blocks.SANDSTONE,
                Blocks.CUT_SANDSTONE,
                Blocks.RED_SANDSTONE,
                Blocks.CUT_RED_SANDSTONE
        );

        halfSlabBlockWithItem("half_slabs:quartz_half_slab", Blocks.QUARTZ_BLOCK, "minecraft:block/quartz_block_side", "minecraft:block/quartz_block_top", "minecraft:block/quartz_block_top");
        halfSlabBlockWithItem("half_slabs:smooth_red_sandstone_half_slab", Blocks.SMOOTH_RED_SANDSTONE, "minecraft:block/red_sandstone_top", "minecraft:block/red_sandstone_top", "minecraft:block/red_sandstone_top");
        halfSlabBlockWithItem("half_slabs:smooth_sandstone_half_slab", Blocks.SMOOTH_SANDSTONE, "minecraft:block/sandstone_top", "minecraft:block/sandstone_top", "minecraft:block/sandstone_top");
        halfSlabBlockWithItem("half_slabs:smooth_quartz_half_slab", Blocks.QUARTZ_BLOCK, "minecraft:block/quartz_block_bottom", "minecraft:block/quartz_block_bottom", "minecraft:block/quartz_block_bottom");
        halfSlabBlockWithItem(blockFromId("half_slabs:waxed_oxidized_cut_copper_half_slab"), Blocks.OXIDIZED_CUT_COPPER);
        halfSlabBlockWithItem(blockFromId("half_slabs:waxed_weathered_cut_copper_half_slab"), Blocks.WEATHERED_CUT_COPPER);
        halfSlabBlockWithItem(blockFromId("half_slabs:waxed_exposed_cut_copper_half_slab"), Blocks.EXPOSED_CUT_COPPER);
        halfSlabBlockWithItem(blockFromId("half_slabs:waxed_cut_copper_half_slab"), Blocks.CUT_COPPER);
        halfSlabBlockWithItem("half_slabs:sandstone_half_slab", Blocks.SANDSTONE, "minecraft:block/sandstone", "minecraft:block/sandstone_bottom", "minecraft:block/sandstone_top");
        halfSlabBlockWithItem("half_slabs:cut_sandstone_half_slab", Blocks.CUT_SANDSTONE, "minecraft:block/cut_sandstone", "minecraft:block/sandstone_top", "minecraft:block/sandstone_top");
        halfSlabBlockWithItem("half_slabs:red_sandstone_half_slab", Blocks.RED_SANDSTONE, "minecraft:block/red_sandstone", "minecraft:block/red_sandstone_bottom", "minecraft:block/red_sandstone_top");
        halfSlabBlockWithItem("half_slabs:cut_red_sandstone_half_slab", Blocks.CUT_RED_SANDSTONE, "minecraft:block/cut_red_sandstone", "minecraft:block/red_sandstone_top", "minecraft:block/red_sandstone_top");

        Registration.BLOCK_MAP.values().forEach(blockPair -> {
            if(blacklist.contains(blockPair.getSecond().get())) return;

            halfSlabBlockWithItem(blockPair.getFirst().get(), blockPair.getSecond().get());
        });
//        halfSlabBlockWithItem(Registration.OAK_HALF_SLAB.get(), Blocks.OAK_PLANKS);
    }


    private void halfSlabBlockWithItem(Block block, Block parent) {
        ResourceLocation path = blockTexture(parent);

        HalfSlabBlock halfSlabBlock = (HalfSlabBlock) block;
        halfSlabBlock(halfSlabBlock, path, blockTexture(parent));
        simpleBlockItem(halfSlabBlock, models().getExistingFile(blockTexture(block)));
    }

    private void halfSlabBlock(HalfSlabBlock block, ResourceLocation full, ResourceLocation texture) {
        halfSlabBlock(block, full, texture, texture, texture);
    }

    private void halfSlabBlockWithItem(String id, Block full, String side, String bottom, String top) {
        halfSlabBlock((HalfSlabBlock) blockFromId(id), blockTexture(full), new ResourceLocation(side), new ResourceLocation(bottom), new ResourceLocation(top));
        simpleBlockItem(blockFromId(id), models().getExistingFile(blockTexture(blockFromId(id))));
    }
    private void halfSlabBlock(HalfSlabBlock block, ResourceLocation full, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {

        getVariantBuilder(block)
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.BOTTOM).addModels(new ConfiguredModel(halfSlab(name(block), HalfSlabType.BOTTOM, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.BOTTOM_TWO).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.BOTTOM_TWO.getSerializedName(), HalfSlabType.BOTTOM_TWO, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.BOTTOM_THREE).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.BOTTOM_THREE.getSerializedName(), HalfSlabType.BOTTOM_THREE, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.MIDDLE_BOTTOM).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.MIDDLE_BOTTOM.getSerializedName(), HalfSlabType.MIDDLE_BOTTOM, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.MIDDLE_TWO).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.MIDDLE_TWO.getSerializedName(), HalfSlabType.MIDDLE_TWO, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.MIDDLE_TOP).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.MIDDLE_TOP.getSerializedName(), HalfSlabType.MIDDLE_TOP, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.TOP).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.TOP.getSerializedName(), HalfSlabType.TOP, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.TOP_TWO).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.TOP_TWO.getSerializedName(), HalfSlabType.TOP_TWO, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.TOP_THREE).addModels(new ConfiguredModel(halfSlab(name(block) + "_" + HalfSlabType.TOP_THREE.getSerializedName(), HalfSlabType.TOP_THREE, side, bottom, top)))
                .partialState().with(HalfSlabBlock.TYPE, HalfSlabType.FULL).addModels(new ConfiguredModel(models().getExistingFile(full)))
        ;




    }

    public String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }
    public ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
    public Block blockFromId(String name) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(name));
    }

    public ModelFile halfSlab(String name, HalfSlabType type, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        ResourceLocation parent = new ResourceLocation(HalfSlabs.MOD_ID, ModelProvider.BLOCK_FOLDER + "/half_slab/" + type.getSerializedName());
        return models().withExistingParent(name, parent)
                .texture("side", side)
                .texture("bottom", bottom)
                .texture("top", top);
    }

    private Path getPath(ModelFile model) {
        ResourceLocation loc = model.getLocation();
        return gen.getOutputFolder().resolve("assets/" + loc.getNamespace() + "/models/" + loc.getPath() + ".json");
    }
}
