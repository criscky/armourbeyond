package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.setup.blocks.Injector;
import com.criscky.armourbeyond.setup.blocks.Injector2;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final RegistryObject<Block> INJECTOR = register("injector", () -> new Injector());
    public static final RegistryObject<Block> INJECTOR2 = register("injector2", () -> new Injector2());


    static void register(){}

    private static <T extends Block> RegistryObject<T> registerNoItem(String name, Supplier<T> block){
        return Registration.BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block){
        RegistryObject<T> ret = registerNoItem(name, block);
        Registration.ITEMS.register(name, () -> new BlockItem(ret.get(), new Item.Properties().tab(ModItemGroup.ARMOUR_BEYOND)));
        return ret;
    }

}
