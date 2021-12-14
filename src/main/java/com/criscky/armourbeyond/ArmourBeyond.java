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

@Mod(ArmourBeyond.MOD_ID)
public class ArmourBeyond
{
    // Directly reference a log4j logger.
    public static final String MOD_ID = "armourbeyond";
    private static final Logger LOGGER = LogManager.getLogger();

    public ArmourBeyond() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();



        Registration.register();
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::commonSetup);
    }


    public void commonSetup(final FMLCommonSetupEvent event){
        ModNetworks.register();
    }


}
