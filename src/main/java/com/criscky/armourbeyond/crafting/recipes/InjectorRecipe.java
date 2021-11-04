package com.criscky.armourbeyond.crafting.recipes;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModRecipes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.IntStream;

import static com.criscky.armourbeyond.Helper.getLevel;
import static com.criscky.armourbeyond.Helper.getRank;

public class InjectorRecipe implements IRecipe<IInventory> {
    private static final Logger LOGGER = LogManager.getLogger();
    Random rand = new Random();

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

        boolean OkUpgrade = false;

        if(pInv.getItem(0).getItem().is(ItemTags.bind(new ResourceLocation(ArmourBeyond.MOD_ID, "upgradable_armor").toString()))) {
            if(getRank(pInv.getItem(0))==-1 && IntStream.of(1, 2, 3, 4, 5).allMatch(j -> pInv.getItem(j).getItem() ==  Items.BREAD)){
                OkUpgrade = true;
            }else if(getLevel(pInv.getItem(0))>=0 && getLevel(pInv.getItem(0)) < 10 && IntStream.of(1, 2, 3, 4, 5).allMatch(j -> pInv.getItem(j).getItem() == Items.SAND)){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==0 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.SPRUCE_LOG){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==1 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.STONE){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==2 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.IRON_INGOT){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==3 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.GOLD_INGOT){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==4 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.DIAMOND){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==5 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.EMERALD){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==6 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.NETHERITE_INGOT){
                OkUpgrade = true;
            }else if(getRank(pInv.getItem(0))==7 && getLevel(pInv.getItem(0))==10 && pInv.getItem(1).getItem() == Items.NETHER_STAR){
                OkUpgrade = true;
            }
        }else
            OkUpgrade = true;

        return i == this.ingredients.size() && (net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null) && OkUpgrade;
    }

    @Nonnull
    @Override
    public ItemStack assemble(IInventory pInv) {
        if(pInv.getItem(0).getItem().is(ItemTags.bind(new ResourceLocation(ArmourBeyond.MOD_ID, "upgradable_armor").toString()))) {
            ItemStack item;
            String rankString = new ResourceLocation(ArmourBeyond.MOD_ID, "rank").toString();
            String levelString = new ResourceLocation(ArmourBeyond.MOD_ID, "level").toString();

            item = pInv.getItem(0).copy();
            CompoundNBT tag = item.getOrCreateTagElement("Rank");
            if(tag.contains(rankString)){
                if(tag.getInt(levelString)==10){
                    tag.putInt(rankString, tag.getInt(rankString)+1);
                    tag.putInt(levelString, 0);
                }else{
                    tag.putInt(levelString, tag.getInt(levelString)+1);
                }

            }else{
                tag.putInt(rankString, 0);
                tag.putInt(levelString, 0);
            }

            return item;
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

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
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
