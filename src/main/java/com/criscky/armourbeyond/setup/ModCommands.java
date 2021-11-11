package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.setup.commands.BaseCommand;
import com.criscky.armourbeyond.setup.commands.SendStatus;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraftforge.event.RegisterCommandsEvent;

import java.util.ArrayList;

public class ModCommands {
    private static final ArrayList<BaseCommand> commands = new ArrayList<>();


    public static void registerCommands(final RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        commands.add(new SendStatus("armourbeyond", 0, true));

        commands.forEach(command -> {
            if (command.isEnabled() && command.setExecution() != null) {
                dispatcher.register(command.getBuilder());
            }
        });
    }

}
