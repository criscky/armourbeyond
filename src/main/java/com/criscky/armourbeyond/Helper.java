package com.criscky.armourbeyond;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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

}
