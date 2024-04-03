package com.kingo.im.protocol.request;

import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
