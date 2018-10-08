package com.ge.service;


import com.ge.supply.MessageHandler;

/**
 * System of complex management of generators and handlers
 */
public interface ManagementSystem {

    void start();
    void setHandlers(MessageHandler... handlers);
    void stop() throws InterruptedException;
    Ack getAck();
}
