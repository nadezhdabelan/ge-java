package com.ge.consume;


import com.ge.dto.Message;
import com.ge.supply.MessageHandler;

/**
 * Generator of messages which notifies handlers
 *
 */
public interface Subject {

    void registerHandler(MessageHandler handlerMessage);
    void removeHandler(MessageHandler handlerMessage);
    void notifyHandlers(Message messageObj);

}
