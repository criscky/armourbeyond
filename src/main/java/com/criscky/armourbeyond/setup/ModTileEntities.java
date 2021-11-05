package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModTileEntities {
    public static final RegistryObject<TileEntityType<InjectorTileEntity>> INJECTOR = register(
            "injector",
            InjectorTileEntity::new,
            ModBlocks.INJECTOR
    );

    static void register() {}

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
}
