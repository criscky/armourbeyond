package com.criscky.armourbeyond.setup.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> render_item_injector;

    static{
        BUILDER.push("Client Configs");

        render_item_injector = BUILDER.comment("Should the Injector Render the items that are inside of it?").define("Value", true);

        SPEC = BUILDER.build();
    }
}
