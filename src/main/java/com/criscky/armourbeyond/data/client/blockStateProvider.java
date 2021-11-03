package com.criscky.armourbeyond.data.client;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class blockStateProvider  extends BlockStateProvider {
    public blockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArmourBeyond.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.INJECTOR.get());
    }
}
