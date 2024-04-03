package com.kingo.im.protocol.response;

import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_RESPONSE;
    }
}
