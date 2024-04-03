package com.kingo.im.protocol.request;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.LOGOUT_REQUEST;

@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {

        return LOGOUT_REQUEST;
    }
}
