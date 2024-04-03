package com.kingo.im.protocol.response;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {
    private String userId;

    private String userName;

    private boolean success;

    private String reason;


    @Override
    public Byte getCommand() {

        return LOGIN_RESPONSE;
    }
}
