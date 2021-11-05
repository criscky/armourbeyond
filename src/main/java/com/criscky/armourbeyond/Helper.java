package com.criscky.armourbeyond;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {

    public static int getRank(ItemStack item){
        int rank = -1;
        if(item.getTagElement("Rank") != null){
            rank = item.getTagElement("Rank").getInt(new ResourceLocation(ArmourBeyond.MOD_ID, "rank").toString());
        }
        return rank;
    }
    public static int getLevel(ItemStack item){
        int level = -1;
        if(item.getTagElement("Rank") != null){
            level = item.getTagElement("Rank").getInt(new ResourceLocation(ArmourBeyond.MOD_ID, "level").toString());
        }
        return level;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
