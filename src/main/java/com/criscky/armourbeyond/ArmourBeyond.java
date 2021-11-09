package com.criscky.armourbeyond;

import com.criscky.armourbeyond.setup.ModNetworks;
import com.criscky.armourbeyond.setup.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("armourbeyond")
public class ArmourBeyond
{
    // Directly reference a log4j logger.
    public static final String MOD_ID = "armourbeyond";
    private static final Logger LOGGER = LogManager.getLogger();

    public ArmourBeyond() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        Registration.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }


    public void commonSetup(final FMLCommonSetupEvent event){
        ModNetworks.register();
    }


}
