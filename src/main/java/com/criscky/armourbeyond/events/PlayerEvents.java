package com.criscky.armourbeyond.events;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.Helper;
import com.criscky.armourbeyond.setup.configs.CommonConfig;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.criscky.armourbeyond.Helper.getDefenseSlot;
import static com.criscky.armourbeyond.Helper.getToughnessSlot;

@Mod.EventBusSubscriber(modid = ArmourBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    static String uuidDefense = "d4238af0-7d38-4331-bb3f-a9164b506965";
    static String uuidToughness = "ca57b828-5ef5-48f9-9c13-e3688d423ea0";


    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event ){
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            UpdateAttributes(player);
        }
    }
    @SubscribeEvent
    public static void onPlayerLeft(EntityLeaveWorldEvent event){
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            RemoveAttributes(player);
        }
    }

    @SubscribeEvent
    public static void onChangeArmor(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntity();
            UpdateAttributes(player);
        }
    }



    private static void UpdateAttributes(PlayerEntity player){
        float toughness = getToughnessSlot(EquipmentSlotType.HEAD, player, CommonConfig.toughness_helmet.get()) +
                getToughnessSlot(EquipmentSlotType.CHEST, player, CommonConfig.toughness_chestplate.get()) +
                getToughnessSlot(EquipmentSlotType.LEGS, player, CommonConfig.toughness_leggings.get()) +
                getToughnessSlot(EquipmentSlotType.FEET, player, CommonConfig.toughness_boot.get());

        float defense = getDefenseSlot(EquipmentSlotType.HEAD, player, CommonConfig.defense_helmet.get()) +
                getDefenseSlot(EquipmentSlotType.CHEST, player, CommonConfig.defense_chestplate.get()) +
                getDefenseSlot(EquipmentSlotType.LEGS, player, CommonConfig.defense_leggings.get()) +
                getDefenseSlot(EquipmentSlotType.FEET, player, CommonConfig.defense_boot.get());


        if(toughness>0 || defense>0) {
            player.getAttributes().addTransientAttributeModifiers(createAttributeMapDefense(Helper.round(defense,2)));
            player.getAttributes().addTransientAttributeModifiers(createAttributeMapToughness(Helper.round(toughness, 2)));
        }else{
            RemoveAttributes(player);
        }
    }
    private static void RemoveAttributes(PlayerEntity player){
        player.getAttributes().removeAttributeModifiers(createAttributeMapDefense(0));
        player.getAttributes().removeAttributeModifiers(createAttributeMapToughness(0));
    }




    protected static Multimap<Attribute, AttributeModifier> createAttributeMapDefense(double value) {
        Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();
        attributesDefault.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString(uuidDefense), ArmourBeyond.MOD_ID+":defense_modifier", value, AttributeModifier.Operation.ADDITION));
        return attributesDefault;
    }
    protected static Multimap<Attribute, AttributeModifier> createAttributeMapToughness(double value) {
        Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();
        attributesDefault.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.fromString(uuidToughness), ArmourBeyond.MOD_ID+":toughness_modifier", value, AttributeModifier.Operation.ADDITION));
        return attributesDefault;
    }

}
