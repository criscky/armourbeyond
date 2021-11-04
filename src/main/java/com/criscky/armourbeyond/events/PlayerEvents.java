package com.criscky.armourbeyond.events;

import com.criscky.armourbeyond.ArmourBeyond;
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
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.criscky.armourbeyond.Helper.getRank;

@Mod.EventBusSubscriber(modid = ArmourBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {
    static String uuidDefense = "d4238af0-7d38-4331-bb3f-a9164b506965";
    static String uuidToughness = "ca57b828-5ef5-48f9-9c13-e3688d423ea0";



    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event ){
        if(event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            RemoveAttributes(player);
            //System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYY");
        }
    }

    @SubscribeEvent
    public static void onChangeArmor(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) event.getEntity();
            UpdateAttributes(player);
            //System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }



    private static void UpdateAttributes(PlayerEntity player){
        System.out.println(player.getItemBySlot(EquipmentSlotType.HEAD).getItem().toString());
        System.out.println(player.getItemBySlot(EquipmentSlotType.CHEST).getItem().toString());
        System.out.println(player.getItemBySlot(EquipmentSlotType.LEGS).getItem().toString());
        System.out.println(player.getItemBySlot(EquipmentSlotType.FEET).getItem().toString());


        float toughness = 0;
        if(!player.getItemBySlot(EquipmentSlotType.HEAD).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(EquipmentSlotType.HEAD).getItem());
            toughness += Math.min((getRank(player.getItemBySlot(EquipmentSlotType.HEAD))+1)*0.5+armortough, 4);
        }
        if(!player.getItemBySlot(EquipmentSlotType.CHEST).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(EquipmentSlotType.CHEST).getItem());
            toughness += Math.min((getRank(player.getItemBySlot(EquipmentSlotType.CHEST))+1)*0.5+armortough, 4);
        }
        if(!player.getItemBySlot(EquipmentSlotType.LEGS).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(EquipmentSlotType.LEGS).getItem());
            toughness += Math.min((getRank(player.getItemBySlot(EquipmentSlotType.LEGS))+1)*0.5+armortough, 4);
        }
        if(!player.getItemBySlot(EquipmentSlotType.FEET).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(EquipmentSlotType.FEET).getItem());
            toughness += Math.min((getRank(player.getItemBySlot(EquipmentSlotType.FEET))+1)*0.5+armortough, 4);
        }
        System.out.println("Toughness: "+toughness);
    }
    private static void RemoveAttributes(PlayerEntity player){
        player.getAttributes().removeAttributeModifiers(createAttributeMapDefense(0));
        player.getAttributes().removeAttributeModifiers(createAttributeMapToughness(0));
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
