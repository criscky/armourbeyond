package com.criscky.armourbeyond.setup.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Double> defense_helmet;
    public static final ForgeConfigSpec.ConfigValue<Double> defense_chestplate;
    public static final ForgeConfigSpec.ConfigValue<Double> defense_leggings;
    public static final ForgeConfigSpec.ConfigValue<Double> defense_boot;

    public static final ForgeConfigSpec.ConfigValue<Double> toughness_helmet;
    public static final ForgeConfigSpec.ConfigValue<Double> toughness_chestplate;
    public static final ForgeConfigSpec.ConfigValue<Double> toughness_leggings;
    public static final ForgeConfigSpec.ConfigValue<Double> toughness_boot;


    public static final ForgeConfigSpec.ConfigValue<Boolean> injector_can_drop;
    //public static final ForgeConfigSpec.ConfigValue<Boolean> injector_unbreakable;
    static {
        BUILDER.push("Max Defense");
        defense_helmet = BUILDER.defineInRange("Helmet (Default: 14)", 14f, 10f, 100f);
        defense_chestplate = BUILDER.defineInRange("Chestplate (Default: 28)", 28f, 10f, 100f);
        defense_leggings = BUILDER.defineInRange("Leggings (Default: 21)", 21f, 10f, 100f);
        defense_boot = BUILDER.defineInRange("Boot (Default: 14)", 14f, 10f, 100f);
        BUILDER.pop();

        BUILDER.push("Max Toughness");
        toughness_helmet = BUILDER.defineInRange("Helmet (Default: 5)", 5f, 5f, 50.0f);
        toughness_chestplate = BUILDER.defineInRange("Chestplate (Default: 5)", 5f, 5f, 50.0f);
        toughness_leggings = BUILDER.defineInRange("Leggings (Default: 5)", 5f, 5f, 50.0f);
        toughness_boot = BUILDER.defineInRange("Boot (Default: 5)", 5f, 5f, 50.0f);
        BUILDER.pop();

        BUILDER.push("Injetor Configs");
        injector_can_drop = BUILDER.comment("Can the injetor drop itself when breaked?").define("Value", true);
        //injector_unbreakable = BUILDER.comment("Is the injetor breakable?").define("Value", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
