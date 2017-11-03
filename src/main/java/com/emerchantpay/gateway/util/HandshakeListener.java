package com.emerchantpay.gateway.util;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

class HandshakeListener implements HandshakeCompletedListener {

    private static Logger logger;

    static {
        logger = Logger.getLogger("Handshake");
        logger.addHandler(new ConsoleHandler());
        logger.setLevel(Level.INFO);
        logger.setUseParentHandlers(false);
    }

    public void handshakeCompleted(HandshakeCompletedEvent e) {
        logger.log(Level.INFO,"[Debug] {0}",
                new Object[] {"Handshake is succesful!!!"});
        logger.log(Level.INFO, "[Debug] {0}",
                new Object[] {"Using cipher suite: " + e.getCipherSuite()});
    }
}
