package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.setup.tileentities.Injector2TileEntity;
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
    public static final RegistryObject<TileEntityType<Injector2TileEntity>> INJECTOR2 = register(
            "injector2",
            Injector2TileEntity::new,
            ModBlocks.INJECTOR2
    );

    static void register() {}

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return Registration.TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
}
