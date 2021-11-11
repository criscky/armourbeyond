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

    static {
        BUILDER.push("Max Defense");
        defense_helmet = BUILDER.defineInRange("Helmet", 14f, 10f, 100f);
        defense_chestplate = BUILDER.defineInRange("Chestplate", 28f, 10f, 100f);
        defense_leggings = BUILDER.defineInRange("Leggings", 21f, 10f, 100f);
        defense_boot = BUILDER.defineInRange("Boot", 14f, 10f, 100f);
        BUILDER.pop();

        BUILDER.push("Max Toughness");
        toughness_helmet = BUILDER.defineInRange("Helmet", 5f, 5f, 50.0f);
        toughness_chestplate = BUILDER.defineInRange("Chestplate", 5f, 5f, 50.0f);
        toughness_leggings = BUILDER.defineInRange("Leggings", 5f, 5f, 50.0f);
        toughness_boot = BUILDER.defineInRange("Boot", 5f, 5f, 50.0f);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
