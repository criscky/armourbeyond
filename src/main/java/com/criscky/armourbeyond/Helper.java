package com.criscky.armourbeyond;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {

    public static int getRank(ItemStack item){
        int rank = -1;
        if(item.getTagElement("Rank") != null){
            rank = item.getTagElement("Rank").getInt(new ResourceLocation(armourbeyond.MOD_ID, "rank").toString());
        }
        return rank;
    }
    public static int getLevel(ItemStack item){
        int level = -1;
        if(item.getTagElement("Rank") != null){
            level = item.getTagElement("Rank").getInt(new ResourceLocation(armourbeyond.MOD_ID, "level").toString());
        }
        return level;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


    public static float getDefenseSlot(EquipmentSlotType slot, PlayerEntity player, double max){
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

    public static float getToughnessSlot(EquipmentSlotType slot, PlayerEntity player, double max){
        if(!player.getItemBySlot(slot).isEmpty()) {
            float armortough = GetToughnessItem(player.getItemBySlot(slot).getItem());
            return (float) Math.max(Math.min((getRank(player.getItemBySlot(slot)))*(max/8), max-armortough), 0);
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

}
