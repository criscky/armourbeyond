package com.criscky.armourbeyond.setup.commands;

import com.criscky.armourbeyond.setup.configs.CommonConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.text.StringTextComponent;

import static com.criscky.armourbeyond.Helper.*;

public class SendStatus extends  BaseCommand{
    public SendStatus(String name, int permissionLevel, boolean enabled) {
        super(name, permissionLevel, enabled);
    }

    @Override
    public LiteralArgumentBuilder<CommandSource> setExecution() {
        return builder.then(Commands.literal("armor_status")
                .executes(source -> execute(source.getSource())));
    }


    private int execute(CommandSource pSource) throws CommandSyntaxException {
        ServerPlayerEntity pPlayer = pSource.getPlayerOrException();
        pSource.sendSuccess(new StringTextComponent(message(pPlayer)), false);

        return Command.SINGLE_SUCCESS;
    }

    private String message(ServerPlayerEntity pPlayer){
        String message_return = "";
        float defenseSlotHead = getDefenseSlot(EquipmentSlotType.HEAD, pPlayer, CommonConfig.defense_helmet.get());
        float toughnessSlotHead = getToughnessSlot(EquipmentSlotType.HEAD, pPlayer, CommonConfig.toughness_helmet.get());

        float defenseSlotBody = getDefenseSlot(EquipmentSlotType.CHEST, pPlayer, CommonConfig.defense_chestplate.get());
        float toughnessSlotBody = getToughnessSlot(EquipmentSlotType.CHEST, pPlayer, CommonConfig.defense_chestplate.get());

        float defenseSlotLegs = getDefenseSlot(EquipmentSlotType.LEGS, pPlayer, CommonConfig.defense_leggings.get());
        float toughnessSlotLegs = getToughnessSlot(EquipmentSlotType.LEGS, pPlayer, CommonConfig.toughness_leggings.get());

        float defenseSlotFeet = getDefenseSlot(EquipmentSlotType.FEET, pPlayer, CommonConfig.defense_boot.get());
        float toughnessSlotFeet = getToughnessSlot(EquipmentSlotType.FEET, pPlayer, CommonConfig.toughness_boot.get());

        message_return += "Head: "+round(defenseSlotHead, 2)+" Defense | "+round(toughnessSlotHead, 2)+" Toughness\n";
        message_return += "Chest: "+round(defenseSlotBody, 2)+" Defense | "+round(toughnessSlotBody, 2)+" Toughness\n";
        message_return += "Legs: "+round(defenseSlotLegs, 2)+" Defense | "+round(toughnessSlotLegs, 2)+" Toughness\n";
        message_return += "Feet: "+round(defenseSlotFeet, 2)+" Defense | "+round(toughnessSlotFeet, 2)+" Toughness\n";

        message_return += "Total rounded: "+round(defenseSlotHead+defenseSlotBody+defenseSlotLegs+defenseSlotFeet, 2)+" Defense | "+
                round(toughnessSlotHead+toughnessSlotBody+toughnessSlotLegs+toughnessSlotFeet, 2)+" Toughness";

        return message_return;
    }
}
