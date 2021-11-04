package com.criscky.armourbeyond.data.client;

import com.criscky.armourbeyond.ArmourBeyond;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class itemModelsProvider extends ItemModelProvider {

    public itemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArmourBeyond.MOD_ID, existingFileHelper);
    }


    @Override
    protected void registerModels() {
        withExistingParent("injector", modLoc("block/injector"));

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        builder(itemGenerated, "devitem1");
        builder(itemGenerated, "devitem2");

        builder(itemGenerated, "eternshard");
        builder(itemGenerated, "upgradewood");
        builder(itemGenerated, "upgradestone");
        builder(itemGenerated, "upgradeiron");
        builder(itemGenerated, "upgradegold");
        builder(itemGenerated, "upgradediamond");
        builder(itemGenerated, "upgradeemerald");
        builder(itemGenerated, "upgradenetherite");
        builder(itemGenerated, "upgradeeternal");
    }

    private void builder(ModelFile itemgenerated, String name) {
        getBuilder(name).parent(itemgenerated).texture("layer0", "item/" +name);
    }
}
