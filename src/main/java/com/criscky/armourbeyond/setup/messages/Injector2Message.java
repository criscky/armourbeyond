package com.criscky.armourbeyond.setup.messages;

import com.criscky.armourbeyond.setup.tileentities.Injector2TileEntity;
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
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class Injector2Message {
    ItemStack item;
    private BlockPos pos;
    private int id;

    private boolean failed;

    public Injector2Message(){

    }

    private Injector2Message(boolean failed) {
        this.failed = failed;
    }

    public Injector2Message(BlockPos pos, ItemStack item, int id){
        this.pos = pos;
        this.item = item;
        this.id = id;
    }

    public static void encode(Injector2Message message, PacketBuffer buffer){
        buffer.writeItem(message.item);
        buffer.writeBlockPos(message.pos);
        buffer.writeInt(message.id);
    }
    public static Injector2Message decode(PacketBuffer buffer){
        try {
            ItemStack item = buffer.readItem();
            BlockPos pos = buffer.readBlockPos();
            int id = buffer.readInt();
            return new Injector2Message(pos, item, id);
        }
        catch (IndexOutOfBoundsException e) {
            return new Injector2Message(true);
        }
    }

    @SuppressWarnings("resource")
    @OnlyIn(Dist.CLIENT)
    private static void handleClient(Injector2Message msg, Context ctx) {
        if (!msg.failed) {
            World world = Minecraft.getInstance().level;
            if (world != null) {
                TileEntity tileEntity = world.getBlockEntity(msg.pos);
                if (tileEntity instanceof Injector2TileEntity) {
                    Injector2TileEntity tileEntity1 = (Injector2TileEntity) tileEntity;
                    tileEntity1.setItem(msg.id, msg.item);
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

    public static void handle(Injector2Message msg, Supplier<Context> contextSupplier) {
        Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> handleClient(msg, ctx)));
    }



}
