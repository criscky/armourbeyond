package com.criscky.armourbeyond.crafting.recipes;

import com.criscky.armourbeyond.armourbeyond;
import com.criscky.armourbeyond.setup.ModItems;
import com.criscky.armourbeyond.setup.ModRecipes;
import com.criscky.armourbeyond.setup.ModTags;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.stream.IntStream;

import static com.criscky.armourbeyond.Helper.getLevel;
import static com.criscky.armourbeyond.Helper.getRank;

public class Injector2Recipe implements IRecipe<IInventory> {
    //Random rand = new Random();

    private final ResourceLocation id;
    private final String group;
    private final IRecipeType<?> type;
    private final ItemStack result;
    private final NonNullList<Ingredient> ingredients;
    private final IRecipeSerializer<?> serializer;


    //private final ItemStack resultArmor;

    public Injector2Recipe(ResourceLocation pId, ItemStack pResult, NonNullList<Ingredient> pIngredients) {
        this.id = pId;
        this.type = ModRecipes.Types.INJECTION2;
        this.serializer = ModRecipes.INJECTION2.get();
        this.group = "";
        this.result = pResult;
        this.ingredients = pIngredients;

        //resultArmor = ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(IInventory pInv, World pLevel) {
        //RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
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

    @Nonnull
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

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<Injector2Recipe> {
        @Override
        public Injector2Recipe fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
            NonNullList<Ingredient> nonnulllist = itemsFromJson(JSONUtils.getAsJsonArray(pJson, "ingredients"));
            ResourceLocation itemId = new ResourceLocation(JSONUtils.getAsString(pJson, "result"));
            int count = JSONUtils.getAsInt(pJson, "count", 1);

            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > 6) {
                throw new JsonParseException("Too many ingredients for the recipe the max is 6");
            } else {
                ItemStack result = new ItemStack(ForgeRegistries.ITEMS.getValue(itemId), count);
                return new Injector2Recipe(pRecipeId, result, nonnulllist);
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
        public Injector2Recipe fromNetwork(ResourceLocation pRecipeId, PacketBuffer pBuffer) {
            int i = pBuffer.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(pBuffer));
            }
            ItemStack result = pBuffer.readItem();

            return new Injector2Recipe(pRecipeId, result, nonnulllist);
        }

        @Override
        public void toNetwork(PacketBuffer pBuffer, Injector2Recipe pRecipe) {
            pBuffer.writeVarInt(pRecipe.ingredients.size());

            for(Ingredient ingredient : pRecipe.ingredients) {
                ingredient.toNetwork(pBuffer);
            }

            pBuffer.writeItem(pRecipe.result);
        }
    }
}
