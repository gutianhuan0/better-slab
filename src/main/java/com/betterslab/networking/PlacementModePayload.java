package com.betterslab.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record PlacementModePayload(int modeId) implements CustomPayload {
    public static final CustomPayload.Id<PlacementModePayload> ID =
            new CustomPayload.Id<>(Identifier.of("betterslab", "placement_mode"));

    public static final PacketCodec<ByteBuf, PlacementModePayload> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.VAR_INT,
                    PlacementModePayload::modeId,
                    PlacementModePayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
