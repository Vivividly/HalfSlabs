package com.vivi.halfslabs.datagen;

import com.vivi.halfslabs.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> recipeConsumer) {
        Registration.BLOCK_MAP.forEach((slabId, blockPair) -> {
            ResourceLocation parentNamespace = ForgeRegistries.BLOCKS.getKey(blockPair.getSecond().get());
            String name = slabId.getPath().substring(0, slabId.getPath().indexOf("_half_slab"));
            name += "_slab";
            ResourceLocation newId = new ResourceLocation(parentNamespace.getNamespace(), name);

            Block slab = ForgeRegistries.BLOCKS.getValue(newId);
            slab(recipeConsumer, blockPair.getFirst().get(), slab);
        });
    }
}
