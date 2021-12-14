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

        if(pInv.getItem(0).getItem().is(ItemTags.bind(new ResourceLocation(armourbeyond.MOD_ID, "upgradable_armor").toString()))) {
            int rank = getRank(pInv.getItem(0));
            if(rank ==-1 && IntStream.of(1, 2, 3, 4, 5).allMatch(j -> pInv.getItem(j).getItem() ==  Items.BREAD)){
                OkUpgrade = true;
            }else if(getLevel(pInv.getItem(0))>=0 && getLevel(pInv.getItem(0)) < 10 && IntStream.of(1, 2, 3, 4, 5).allMatch(j -> pInv.getItem(j).getItem() == ModItems.ETERNITY_SHARD.get())){
                OkUpgrade = true;
            }else if(getLevel(pInv.getItem(0))==10){
                Item item1 = pInv.getItem(1).getItem();
                if(rank == 0 && item1 == ModItems.UPGRADE_WOOD.get()){
                    OkUpgrade = true;
                }else if(rank == 1 && item1 == ModItems.UPGRADE_STONE.get()){
                    OkUpgrade = true;
                }else if(rank == 2 && item1 == ModItems.UPGRADE_IRON.get()){
                    OkUpgrade = true;
                }else if(rank == 3 && item1 == ModItems.UPGRADE_GOLD.get()){
                    OkUpgrade = true;
                }else if(rank == 4 && item1 == ModItems.UPGRADE_DIAMOND.get()){
                    OkUpgrade = true;
                }else if(rank == 5 && item1 == ModItems.UPGRADE_EMERALD.get()){
                    OkUpgrade = true;
                }else if(rank == 6 && item1 == ModItems.UPGRADE_NETHERITE.get()){
                    OkUpgrade = true;
                }else if(rank == 7 && item1 == ModItems.UPGRADE_ETERNAL.get()){
                    OkUpgrade = true;
                }
            }
        }else
            OkUpgrade = true;

        return i == this.ingredients.size() && (net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null) && OkUpgrade;
    }

    @Nonnull
    @Override
    public ItemStack assemble(IInventory pInv) {
        //if(pInv.getItem(0).getItem().is(ItemTags.bind(new ResourceLocation(ArmourBeyond.MOD_ID, "upgradable_armor").toString()))) {
        if(pInv.getItem(0).getItem().is(ModTags.Items.UPGRADABLE_ARMOR)) {
            return RankUp(pInv.getItem(0).copy());
        }
            return this.result.copy();
    }

    public ItemStack RankUp(ItemStack item){
        String rankString = new ResourceLocation(armourbeyond.MOD_ID, "rank").toString();
        String levelString = new ResourceLocation(armourbeyond.MOD_ID, "level").toString();

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
                throw new JsonParseException("Too many ingredients for the recipe the max is 6");
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
