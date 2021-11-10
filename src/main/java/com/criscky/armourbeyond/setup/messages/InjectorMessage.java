package com.criscky.armourbeyond.setup.messages;

import com.criscky.armourbeyond.setup.tileentities.InjectorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class InjectorMessage {
    private static InjectorTileEntity tileEntity1;
    ItemStack item;
    private BlockPos pos;

    private boolean failed;

    public InjectorMessage(){

    }
    private InjectorMessage(boolean failed) {
        this.failed = failed;
    }

    public InjectorMessage(BlockPos pos, ItemStack item){
        this.pos = pos;
        this.item = item;
    }

    public static void encode(InjectorMessage message, PacketBuffer buffer){
        buffer.writeItem(message.item);
        buffer.writeBlockPos(message.pos);
    }
    public static InjectorMessage decode( PacketBuffer buffer){
        try {
            ItemStack item = buffer.readItem();
            BlockPos pos = buffer.readBlockPos();
            return new InjectorMessage(pos, item);
        }
        catch (IndexOutOfBoundsException e) {
            return new InjectorMessage(true);
        }
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    private static void handleClient(InjectorMessage msg, NetworkEvent.Context ctx) {
        if (!msg.failed) {
            World world = Minecraft.getInstance().level;
            if (world != null) {
                TileEntity tileEntity = world.getBlockEntity(msg.pos);
                if (tileEntity instanceof InjectorTileEntity) {
                    InjectorTileEntity tileEntity1 = (InjectorTileEntity) tileEntity;
                    tileEntity1.setItem(6, msg.item);
                    tileEntity1.removeItem(0, 1);
                    tileEntity1.removeItem(1, 1);
                    tileEntity1.removeItem(2, 1);
                    tileEntity1.removeItem(3, 1);
                    tileEntity1.removeItem(4, 1);
                    tileEntity1.removeItem(5, 1);
                    tileEntity1.GetFields().set(0, 0);
                }
            }
        }
    }

    public static void handle(InjectorMessage msg, Supplier<Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClient(msg, ctx)));
    }



}
