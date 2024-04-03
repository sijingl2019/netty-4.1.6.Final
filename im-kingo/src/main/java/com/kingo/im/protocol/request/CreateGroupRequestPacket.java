package com.kingo.im.protocol.request;

import lombok.Data;
import com.kingo.im.protocol.Packet;

import java.util.List;

import static com.kingo.im.protocol.command.Command.CREATE_GROUP_REQUEST;

@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;

    @Override
    public Byte getCommand() {

        return CREATE_GROUP_REQUEST;
    }
}
