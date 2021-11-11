package com.criscky.armourbeyond.events;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.setup.ModCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("armourbeyond")
@Mod.EventBusSubscriber(modid = ArmourBeyond.MOD_ID)
public class ModCommonEvents {
    @SubscribeEvent
    public static void onCommandRegister(final RegisterCommandsEvent event) {
        ModCommands.registerCommands(event);
    }

}
