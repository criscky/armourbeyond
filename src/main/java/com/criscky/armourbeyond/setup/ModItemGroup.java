package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.armourbeyond;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModItemGroup extends ItemGroup {

    public static final ModItemGroup ARMOUR_BEYOND = new ModItemGroup(ItemGroup.TABS.length,
            armourbeyond.MOD_ID);

    public ModItemGroup(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Items.NETHERITE_CHESTPLATE);
    }

    static void register(){}
}
