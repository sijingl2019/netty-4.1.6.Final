package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.LOGOUT_RESPONSE;

@Data
public class LogoutResponsePacket extends Packet {

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGOUT_RESPONSE;
    }
}
