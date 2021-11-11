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
        message_return += "Head: "+round(getDefenseSlot(EquipmentSlotType.HEAD, pPlayer, CommonConfig.defense_helmet.get()), 2)+" Defense | "+round(getToughnessSlot(EquipmentSlotType.HEAD, pPlayer, CommonConfig.toughness_helmet.get()), 2)+" Toughness\n";
        message_return += "Chest: "+round(getDefenseSlot(EquipmentSlotType.CHEST, pPlayer, CommonConfig.defense_chestplate.get()), 2)+" Defense | "+round(getToughnessSlot(EquipmentSlotType.CHEST, pPlayer, CommonConfig.defense_chestplate.get()), 2)+" Toughness\n";
        message_return += "Legs: "+round(getDefenseSlot(EquipmentSlotType.LEGS, pPlayer, CommonConfig.defense_leggings.get()), 2)+" Defense | "+round(getToughnessSlot(EquipmentSlotType.LEGS, pPlayer, CommonConfig.toughness_leggings.get()), 2)+" Toughness\n";
        message_return += "Feet: "+round(getDefenseSlot(EquipmentSlotType.FEET, pPlayer, CommonConfig.defense_boot.get()), 2)+" Defense | "+round(getToughnessSlot(EquipmentSlotType.FEET, pPlayer, CommonConfig.toughness_boot.get()), 2)+" Toughness\n";

        return message_return;
    }
}
