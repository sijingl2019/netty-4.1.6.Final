package com.kingo.im.attribute;

import io.netty.util.AttributeKey;
import com.kingo.im.session.Session;

public interface Attributes {
    AttributeKey<Session> SESSION = AttributeKey.newInstance("com/kingo/im/session");

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
