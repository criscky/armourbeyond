package com.criscky.armourbeyond;

import com.criscky.armourbeyond.setup.Registration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("armourbeyond")
public class ArmourBeyond
{
    // Directly reference a log4j logger.
    public static final String MOD_ID = "armourbeyond";
    private static final Logger LOGGER = LogManager.getLogger();

    public ArmourBeyond() {
        Registration.register();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
