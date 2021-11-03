package com.criscky.armourbeyond.data;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.data.client.blockStateProvider;
import com.criscky.armourbeyond.data.client.itemModelsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ArmourBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();


        gen.addProvider(new blockStateProvider(gen, existingFileHelper));
        gen.addProvider(new itemModelsProvider(gen, existingFileHelper));


        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        gen.addProvider(new ModTagProvider(gen, blockTags, existingFileHelper));

    }

}
