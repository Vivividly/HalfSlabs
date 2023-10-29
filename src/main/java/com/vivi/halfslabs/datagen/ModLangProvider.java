package com.vivi.halfslabs.datagen;

import com.vivi.halfslabs.HalfSlabs;
import com.vivi.halfslabs.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(DataGenerator gen) {
        super(gen, HalfSlabs.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        Registration.BLOCK_MAP.forEach((slabId, blockPair) -> {

            ResourceLocation parentNamespace = ForgeRegistries.BLOCKS.getKey(blockPair.getSecond().get());
            String slabString = slabId.getPath().substring(0, slabId.getPath().indexOf("_half_slab"));
            slabString += "_slab";
            ResourceLocation newId = new ResourceLocation(parentNamespace.getNamespace(), slabString);

            Block slab = ForgeRegistries.BLOCKS.getValue(newId);
            String name = slab.getName().getString().substring(0, slab.getName().getString().indexOf(" Slab"));

            add(blockPair.getFirst().get(), name + " Half Slab");
        });

        add("itemGroup.half_slabs", "Half Slabs");
    }
}
