package com.criscky.armourbeyond.setup;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> DEVITEM1 = Registration.ITEMS.register("devitem1", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> DEVITEM2 = Registration.ITEMS.register("devitem2", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));

    public static final RegistryObject<Item> ETERNITY_SHARD = Registration.ITEMS.register("eternshard", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_WOOD = Registration.ITEMS.register("upgradewood", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_STONE = Registration.ITEMS.register("upgradestone", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_IRON = Registration.ITEMS.register("upgradeiron", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_GOLD = Registration.ITEMS.register("upgradegold", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_DIAMOND = Registration.ITEMS.register("upgradediamond", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_EMERALD = Registration.ITEMS.register("upgradeemerald", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_NETHERITE = Registration.ITEMS.register("upgradenetherite", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
    public static final RegistryObject<Item> UPGRADE_ETERNAL = Registration.ITEMS.register("upgradeeternal", () ->
            new Item(new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));

    static void register() {}
}
