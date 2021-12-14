package com.criscky.armourbeyond.data;

import com.criscky.armourbeyond.armourbeyond;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, armourbeyond.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
    }
}
