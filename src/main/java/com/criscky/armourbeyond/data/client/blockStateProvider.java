package com.criscky.armourbeyond.data.client;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class blockStateProvider  extends BlockStateProvider {
    public blockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ArmourBeyond.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockModelBuilder modelinjector = models().cubeBottomTop("injector",
                modLoc("block/sides"),
                modLoc("block/down"),
                modLoc("block/top"));
        //horizontalBlock(ModBlocks.INJECTOR.get(), modelinjector, 0);


    }

    private void orientableMachineBlock(Block block, String name) {
        ModelFile.ExistingModelFile offModel = getExistingModel(name);
        ModelFile.ExistingModelFile onModel = getExistingModel(name + "_on");
        getVariantBuilder(block).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            boolean lit = state.getValue(BlockStateProperties.LIT);
            return ConfiguredModel.builder()
                    .modelFile(lit ? onModel : offModel)
                    .rotationY((int) facing.getOpposite().toYRot())
                    .build();
        });
    }
    private ModelFile.ExistingModelFile getExistingModel(String blockName) {
        return models().getExistingFile(modLoc(blockName));
    }
}
