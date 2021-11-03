package com.criscky.armourbeyond.crafting.recipes;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class InjectorRecipe implements IRecipe<IInventory> {
    private final ResourceLocation id;
    private final String group;
    private final IRecipeType<?> type;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    private final IRecipeSerializer<?> serializer;

    public InjectorRecipe(IRecipeType<?> pType, IRecipeSerializer<?> pSerializer, ResourceLocation pId, String pGroup, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.type = pType;
        this.serializer = pSerializer;
        this.group = pGroup;
        this.result = pResult;
        this.ingredients = pIngredients;
    }


    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(IInventory pInv) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public IRecipeType<?> getType() {
        return this.type;
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InjectorRecipe> {
        @Override
        public InjectorRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(json, "result"));
            int count = JSONUtils.getAsInt(json, "count", 1);

            ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);

            //return new InjectorRecipe(recipeId, ingredient, result);
            return null;
        }

        @Nullable
        @Override
        public InjectorRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            //return new InjectorRecipe(recipeId, ingredient, result);
            return null;
        }

        @Override
        public void toNetwork(PacketBuffer buffer, InjectorRecipe recipe) {
            /*recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);*/
        }
    }
}
