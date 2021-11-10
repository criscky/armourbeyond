package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.client.tileentityrender.InjectorRenderer;
import com.criscky.armourbeyond.setup.configs.ClientConfig;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = create(ForgeRegistries.TILE_ENTITIES);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = create(ForgeRegistries.CONTAINERS);

    public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = create(ForgeRegistries.RECIPE_SERIALIZERS);


    public static void register(IEventBus modEventBus) {


        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        CONTAINERS.register(modEventBus);

        ITEMS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);


        ModContainerTypes.register();
        ModTileEntities.register();
        ModItemGroup.register();
        ModBlocks.register();
        ModItems.register();
        ModRecipes.register();


        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC, "armour-beyond-client.toml");


    }


    private static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, ArmourBeyond.MOD_ID);
    }


    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ArmourBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class Client {
        private Client() {}

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModContainerTypes.registerScreens(event);

            RenderTypeLookup.setRenderLayer(ModBlocks.INJECTOR.get(), RenderType.cutout());
            ClientRegistry.bindTileEntityRenderer(ModTileEntities.INJECTOR.get(), InjectorRenderer::new);

        }
    }



}
