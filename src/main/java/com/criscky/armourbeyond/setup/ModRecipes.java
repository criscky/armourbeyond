package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.crafting.recipes.InjectorRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;

public class ModRecipes {
    public static final class Types {
        public static final IRecipeType<InjectorRecipe> INJECTION = IRecipeType.register(
                ArmourBeyond.MOD_ID + ":injection");

        private Types() {}
    }

    public static final class Serializers {
        public static final RegistryObject<IRecipeSerializer<?>> INJECTION = Registration.RECIPE_SERIALIZERS.register(
                "injection", InjectorRecipe.Serializer::new);

        private Serializers() {}
    }

    private ModRecipes() {}

    static void register() {}
}
