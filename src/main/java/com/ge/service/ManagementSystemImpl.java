package com.ge.service;


import com.ge.consume.MessageGenerator;
import com.ge.supply.MessageHandler;

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
    public void stop(){
        messageGenerator.stop();
        messageGenerator.getHandlerList().stream()
                .forEach(h -> h.stopHandle());
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
