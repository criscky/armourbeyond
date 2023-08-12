package com.criscky.armourbeyond.setup;

import com.criscky.armourbeyond.armourbeyond;
import com.criscky.armourbeyond.setup.messages.Injector2Message;
import com.criscky.armourbeyond.setup.messages.InjectorMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class ModNetworks {
    protected static String NETWORK_VERSION = "0.1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(armourbeyond.MOD_ID, "network"),
            () -> NETWORK_VERSION, version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION));
    public static final SimpleChannel CHANNEL2 = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(armourbeyond.MOD_ID, "network2"),
            () -> NETWORK_VERSION, version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION));

    public static <MSG> void sendToAllTracking(SimpleChannel channel, MSG message, TileEntity tile) {
        sendToAllTracking(channel, message, tile.getLevel(), tile.getBlockPos());
    }

    @SuppressWarnings("resource")
    public static <MSG> void sendToAllTracking(SimpleChannel channel, MSG message, World world, BlockPos pos) {
        if (world instanceof ServerWorld)
            ((ServerWorld) world).getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false)
                    .forEach(p -> sendTo(channel, message, p));
        else
            channel.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunk(pos.getX() >> 4, pos.getZ() >> 4)),
                    message);
    }


    public static void register() {
        CHANNEL.registerMessage(0, InjectorMessage.class, InjectorMessage::encode, InjectorMessage::decode, InjectorMessage::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL2.registerMessage(0, Injector2Message.class, Injector2Message::encode, Injector2Message::decode, Injector2Message::handle, Optional.of(NetworkDirection.PLAY_TO_CLIENT));

    }

    /**
     * Send this message to the specified player.
     *
     * @param message - the message to send
     * @param player  - the player to send it to
     */
    public static <MSG> void sendTo(SimpleChannel channel, MSG message, ServerPlayerEntity player) {
        channel.sendTo(message, player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
    }


}
