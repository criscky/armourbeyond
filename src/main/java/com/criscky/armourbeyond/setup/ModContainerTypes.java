package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.setup.client.screens.Injector2Screen;
import com.criscky.armourbeyond.setup.containers.Injector2Container;
import com.criscky.armourbeyond.setup.containers.InjectorContainer;
import com.criscky.armourbeyond.setup.client.screens.InjectorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.network.IContainerFactory;

public class ModContainerTypes {
    public static final RegistryObject<ContainerType<InjectorContainer>> INJECTOR = register("injector", InjectorContainer::new);
    public static final RegistryObject<ContainerType<Injector2Container>> INJECTOR2 = register("injector2", Injector2Container::new);

    private ModContainerTypes() {}

    static void register() {}

    @OnlyIn(Dist.CLIENT)
    public static void registerScreens(FMLClientSetupEvent event) {
        ScreenManager.register(INJECTOR.get(), InjectorScreen::new);
        ScreenManager.register(INJECTOR2.get(), Injector2Screen::new);
    }

    private static <T extends Container> RegistryObject<ContainerType<T>> register(String name, IContainerFactory<T> factory) {
        return Registration.CONTAINERS.register(name, () -> IForgeContainerType.create(factory));
    }
}
