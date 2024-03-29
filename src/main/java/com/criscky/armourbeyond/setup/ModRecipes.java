package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.armourbeyond;
import com.criscky.armourbeyond.crafting.recipes.Injector2Recipe;
import com.criscky.armourbeyond.crafting.recipes.InjectorRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;

public class ModRecipes {
    public static final class Types {
        /*public static final IRecipeType<InjectorRecipe> INJECTION = IRecipeType.register(
                ArmourBeyond.MOD_ID + ":injection");*/
        public static final IRecipeType<InjectorRecipe> INJECTION = registerType(new ResourceLocation(armourbeyond.MOD_ID, "injection"));
        public static final IRecipeType<Injector2Recipe> INJECTION2 = registerType(new ResourceLocation(armourbeyond.MOD_ID, "injection2"));

        private Types() {}
    }


    public static final RegistryObject<IRecipeSerializer<?>> INJECTION = Registration.RECIPE_SERIALIZERS.register(
            "injection", InjectorRecipe.Serializer::new);
    public static final RegistryObject<IRecipeSerializer<?>> INJECTION2 = Registration.RECIPE_SERIALIZERS.register(
            "injection2", Injector2Recipe.Serializer::new);


    private ModRecipes() {}

    static void register() {}


    private static <T extends IRecipe<?>> IRecipeType<T> registerType(ResourceLocation name) {
        return Registry.register(Registry.RECIPE_TYPE, name, new IRecipeType<T>() {
            @Override
            public String toString() {
                return name.toString();
            }
        });
    }
}
