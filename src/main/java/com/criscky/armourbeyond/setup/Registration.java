package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.ArmourBeyond;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registration {
    public static void register() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    }


    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ArmourBeyond.MOD_ID);
    }
}
