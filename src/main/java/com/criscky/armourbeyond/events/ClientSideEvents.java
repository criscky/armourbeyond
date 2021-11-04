package com.criscky.armourbeyond.events;

import com.criscky.armourbeyond.ArmourBeyond;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.criscky.armourbeyond.Helper.getLevel;
import static com.criscky.armourbeyond.Helper.getRank;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ArmourBeyond.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientSideEvents {
    @SubscribeEvent
    public static void AddToolTip(ItemTooltipEvent event){
        int rank = getRank(event.getItemStack());
        if(rank>-1){
            event.getToolTip().add(new StringTextComponent("Rank: "+rank).withStyle(TextFormatting.BLUE));
        }
        int level = getLevel(event.getItemStack());
        if(level>-1){
            event.getToolTip().add(new StringTextComponent("Level: "+level).withStyle(TextFormatting.RED));
        }
    }
}
