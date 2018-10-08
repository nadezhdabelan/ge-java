package com.ge.service;


import com.ge.consume.MessageGenerator;
import com.ge.supply.MessageHandler;

import java.util.concurrent.TimeUnit;

/**
 * simplified system of control of generator and handlers
 */
public class ManagementSystemImpl implements ManagementSystem {

    private MessageGenerator messageGenerator;
    private Ack ack;

    public ManagementSystemImpl(){
        ack = new AckImpl();
        messageGenerator = new MessageGenerator(ack);
    }


    /**
     * start generate messages
     * start handle messages in all registered handlers
     */
    public void start(){
            messageGenerator.start();
            messageGenerator.getHandlerList().stream()
                    .forEach(h -> h.startHandle());

    }

    /**
     * stop generate messages
     * stop handle messages in all registered handlers
     */
    public void stop() throws InterruptedException {
        messageGenerator.stop();
        messageGenerator.getHandlerList().stream()
                .forEach(h -> {
                    try {
                        h.stopHandle();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
        System.exit(0);
    }

    /**
     * register handlers
     * @param handlers
     */
    public void setHandlers(MessageHandler... handlers){
        for(MessageHandler handlerMessage : handlers) {
            messageGenerator.registerHandler(handlerMessage);
        }
    }

    public Ack getAck() {
        return ack;
    }
}
