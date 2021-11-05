package com.criscky.armourbeyond.data;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModBlocks;
import com.criscky.armourbeyond.setup.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(ModBlocks.INJECTOR.get())
                .define('1', Items.COAL_BLOCK)
                .define('2', Items.CAULDRON)
                .define('3', Items.GOLD_INGOT)
                .define('4', Items.IRON_INGOT)
                .define('5', ModItems.ETERNITY_SHARD.get())
                .pattern("454")
                .pattern("323")
                .pattern("111")
                .unlockedBy("has_item", has(Items.DIRT))
                .save(consumer, modId("injectorcrafting"));
    }
    private static ResourceLocation modId(String path) {
        return new ResourceLocation(ArmourBeyond.MOD_ID, path);
    }

}
