package com.criscky.armourbeyond.data.client;

import com.criscky.armourbeyond.armourbeyond;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class blockStateProvider  extends BlockStateProvider {
    public blockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, armourbeyond.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        //horizontalBlock(ModBlocks.INJECTOR.get(), modelinjector, 0);


    }

    private ModelFile.ExistingModelFile getExistingModel(String blockName) {
        return models().getExistingFile(modLoc(blockName));
    }
}
