package com.criscky.armourbeyond.events;

import com.criscky.armourbeyond.ArmourBeyond;
import com.criscky.armourbeyond.Helper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.criscky.armourbeyond.Helper.getLevel;
import static com.criscky.armourbeyond.Helper.getRank;

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
        float toughness = getToughnessSlot(EquipmentSlotType.HEAD, player, 5F, 0.625F) +
                getToughnessSlot(EquipmentSlotType.CHEST, player, 5F, 0.625F) +
                getToughnessSlot(EquipmentSlotType.LEGS, player, 5F, 0.625F) +
                getToughnessSlot(EquipmentSlotType.FEET, player, 5F, 0.625F);

        float defense = getDefenseSlot(EquipmentSlotType.HEAD, player, 10) +
                getDefenseSlot(EquipmentSlotType.CHEST, player, 20) +
                getDefenseSlot(EquipmentSlotType.LEGS, player, 15) +
                getDefenseSlot(EquipmentSlotType.FEET, player, 10);


        if(toughness>0 && defense>0) {
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


    private static float getDefenseSlot(EquipmentSlotType slot, PlayerEntity player, float max){
        if(!player.getItemBySlot(slot).isEmpty()) {
            float armordefense = GetDefenseItem(player.getItemBySlot(slot).getItem());
            return (float) Math.max(Math.min(((getRank(player.getItemBySlot(slot)))*10 +
                    (getLevel(player.getItemBySlot(slot))))
                    *(max/90), max-armordefense), 0);
        }else{
            return 0;
        }
    }
    private static float GetDefenseItem(Item item){
        if(item instanceof ArmorItem){
            return ((ArmorItem) item).getDefense();
        }else{
            return 0;
        }
    }

    private static float getToughnessSlot(EquipmentSlotType slot, PlayerEntity player, float max, float multiplier){
        if(!player.getItemBySlot(slot).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(slot).getItem());
            return (float) Math.max(Math.min((getRank(player.getItemBySlot(slot)))*multiplier, max-armortough), 0);
        }else{
            return 0;
        }
    }
    private static float GetToughnessItem(Item item){
        if(item instanceof ArmorItem){
            return ((ArmorItem) item).getToughness();
        }else{
            return 0;
        }
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
