package com.kingo.im.protocol.request;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import static com.kingo.im.protocol.command.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    private String userName;

    private String password;

    @Override
    public Byte getCommand() {

        return LOGIN_REQUEST;
    }
}
