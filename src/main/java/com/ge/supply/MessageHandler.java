package com.ge.supply;


import com.ge.dto.Message;

public interface MessageHandler {

    void notify(Message message);
    void startHandle();
    void stopHandle() throws InterruptedException;
    String getName();
}
