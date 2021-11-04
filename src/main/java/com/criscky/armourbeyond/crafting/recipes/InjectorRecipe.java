package com.criscky.armourbeyond.crafting.recipes;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
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


    private final ItemStack resultArmor;

    public InjectorRecipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.type = ModRecipes.Types.INJECTION;
        this.serializer = ModRecipes.INJECTION.get();
        this.group = "";
        this.result = pResult;
        this.ingredients = pIngredients;

        resultArmor = ItemStack.EMPTY;
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
        if(pInv.getItem(0).getItem().is(ItemTags.bind(new ResourceLocation(ArmourBeyond.MOD_ID, "upgradable_armor").toString()))) {
            return pInv.getItem(0).copy();
        }
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
        public InjectorRecipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.getAsJsonArray(pJson, "ingredients"));
            ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(pJson, "result"));
            int count = JSONUtils.getAsInt(pJson, "count", 1);

            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > 6) {
                throw new JsonParseException("Too many ingredients for shapeless recipe the max is 6");
            } else {
                ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);
                return new InjectorRecipe(pRecipeId, result, nonnulllist);
            }
        }


        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
                if (!ingredient.isEmpty()) {
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public InjectorRecipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack result = pBuffer.readItem();

            return new InjectorRecipe(pRecipeId, result, nonnulllist);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, InjectorRecipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
