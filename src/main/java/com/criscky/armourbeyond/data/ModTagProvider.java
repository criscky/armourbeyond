package com.criscky.armourbeyond.data;

import com.criscky.armourbeyond.armourbeyond;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTagProvider extends ItemTagsProvider {
    public ModTagProvider(DataGenerator pGenerator, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, pBlockTagsProvider, armourbeyond.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags() {
        ITag.INamedTag<Item> ArmorTag = ItemTags.bind(new ResourceLocation(armourbeyond.MOD_ID, "upgradable_armor").toString());


        tag(ArmorTag).add(Items.LEATHER_HELMET);
        tag(ArmorTag).add(Items.LEATHER_CHESTPLATE);
        tag(ArmorTag).add(Items.LEATHER_LEGGINGS);
        tag(ArmorTag).add(Items.LEATHER_BOOTS);


        tag(ArmorTag).add(Items.CHAINMAIL_HELMET);
        tag(ArmorTag).add(Items.CHAINMAIL_CHESTPLATE);
        tag(ArmorTag).add(Items.CHAINMAIL_LEGGINGS);
        tag(ArmorTag).add(Items.CHAINMAIL_BOOTS);

        tag(ArmorTag).add(Items.IRON_HELMET);
        tag(ArmorTag).add(Items.IRON_CHESTPLATE);
        tag(ArmorTag).add(Items.IRON_LEGGINGS);
        tag(ArmorTag).add(Items.IRON_BOOTS);

        tag(ArmorTag).add(Items.GOLDEN_HELMET);
        tag(ArmorTag).add(Items.GOLDEN_CHESTPLATE);
        tag(ArmorTag).add(Items.GOLDEN_LEGGINGS);
        tag(ArmorTag).add(Items.GOLDEN_BOOTS);

        tag(ArmorTag).add(Items.DIAMOND_HELMET);
        tag(ArmorTag).add(Items.DIAMOND_CHESTPLATE);
        tag(ArmorTag).add(Items.DIAMOND_LEGGINGS);
        tag(ArmorTag).add(Items.DIAMOND_BOOTS);

        tag(ArmorTag).add(Items.NETHERITE_HELMET);
        tag(ArmorTag).add(Items.NETHERITE_CHESTPLATE);
        tag(ArmorTag).add(Items.NETHERITE_LEGGINGS);
        tag(ArmorTag).add(Items.NETHERITE_BOOTS);

    }

}
