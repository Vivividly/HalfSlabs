package com.vivi.halfslabs.datagen;


import com.vivi.halfslabs.HalfSlabs;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HalfSlabs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new ModBlockstateProvider(gen, fileHelper));
        gen.addProvider(event.includeClient(), new ModLangProvider(gen));
        gen.addProvider(event.includeServer(), new ModRecipeProvider(gen));
        gen.addProvider(event.includeServer(), new ModLootTableProvider(gen));
        gen.addProvider(event.includeServer(), new ModBlockTagProvider(gen, fileHelper));
    }
}
