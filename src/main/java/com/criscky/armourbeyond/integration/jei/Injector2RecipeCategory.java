package com.criscky.armourbeyond.integration.jei;

import com.criscky.armourbeyond.armourbeyond;
import com.criscky.armourbeyond.crafting.recipes.Injector2Recipe;
import com.criscky.armourbeyond.setup.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class Injector2RecipeCategory implements IRecipeCategory<Injector2Recipe> {
    public final static ResourceLocation UID = new ResourceLocation(armourbeyond.MOD_ID, "injector2");
    public final static ResourceLocation TEXTURE = new ResourceLocation(armourbeyond.MOD_ID, "textures/gui/injector2.png");

    private final IDrawable background;
    private final IDrawable icon;

    public Injector2RecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 172, 80);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.INJECTOR2.get()));
    }

    @Override
    public @NotNull ResourceLocation getUid() {
        return UID;
    }

    @Override
    public @NotNull Class<? extends Injector2Recipe> getRecipeClass() {
        return Injector2Recipe.class;
    }

    @Override
    public @NotNull String getTitle() {
        return "Injector2";
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(Injector2Recipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, @NotNull Injector2Recipe recipe, @NotNull IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 43, 28);
        recipeLayout.getItemStacks().init(1, true, 43, 4);
        recipeLayout.getItemStacks().init(2, true, 71, 24);
        recipeLayout.getItemStacks().init(3, true, 57, 52);
        recipeLayout.getItemStacks().init(4, true, 29, 52);
        recipeLayout.getItemStacks().init(5, true, 15, 24);

        recipeLayout.getItemStacks().init(6, false, 125, 28);
        recipeLayout.getItemStacks().set(ingredients);

    }


}
