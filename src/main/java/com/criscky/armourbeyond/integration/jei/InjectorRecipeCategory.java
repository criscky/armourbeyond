package com.criscky.armourbeyond.integration.jei;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.crafting.recipes.InjectorRecipe;
import com.criscky.armourbeyond.setup.ModBlocks;
import com.criscky.armourbeyond.setup.ModItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.stream.Stream;

public class InjectorRecipeCategory implements IRecipeCategory<InjectorRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(ArmourBeyond.MOD_ID, "injector");
    public final static ResourceLocation TEXTURE = new ResourceLocation(ArmourBeyond.MOD_ID, "textures/gui/injector.png");

    private final IDrawable background;
    private final IDrawable icon;

    public InjectorRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 172, 80);
        this.icon = helper.createDrawableIngredient(new ItemStack(ModBlocks.INJECTOR.get()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends InjectorRecipe> getRecipeClass() {
        return InjectorRecipe.class;
    }

    @Override
    public String getTitle() {
        return "Injector";
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setIngredients(InjectorRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());

    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, InjectorRecipe recipe, IIngredients ingredients) {
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
