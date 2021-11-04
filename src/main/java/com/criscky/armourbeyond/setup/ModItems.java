package com.criscky.armourbeyond.setup;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> DEVITEM1 = Registration.ITEMS.register("devitem1", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> DEVITEM2 = Registration.ITEMS.register("devitem2", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));

    static void register() {}
}
