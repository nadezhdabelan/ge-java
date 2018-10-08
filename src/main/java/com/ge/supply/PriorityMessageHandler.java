package com.ge.supply;


import com.ge.dto.AcknowledgementEnum;
import com.ge.dto.Message;
import com.ge.service.Ack;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.ge.dto.MessageObj.idComparator;


/**
 * Handler of messages according to pattern Observer
 *
 */
public class PriorityMessageHandler implements MessageHandler {

    private BlockingQueue<Message> messages = new PriorityBlockingQueue<Message>(100, idComparator);
    private final Ack ack;
    private final String name;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public PriorityMessageHandler(Ack ack, String name){
        this.ack = ack;
        this.name = name;
    }


    /**
     * Generator of message notify observers about new messages
     * inform of system acknowledgement about delivery of message in concrete handler
     * @param message
     */
    @Override
    public void notify(Message message) {
        message.setStatus(AcknowledgementEnum.ACK);
        ack.acknowledgement(message, name);
        messages.add(message);

    }

    public void startHandle(){
        handleInner();
    }

    @Override
    public void stopHandle() {
        isRunning.set(false);
        executor.shutdown();
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * handle message and inform of system acknowledgement about handling of message in concrete handler
     */
    private void handleInner() {
        executor.execute(() -> {
            while (isRunning.get()){
                Message m = null;
                try {
                    m = messages.poll(2, TimeUnit.SECONDS);
                    TimeUnit.SECONDS.sleep(1);
                    if(m!= null) {
                        m.setStatus(AcknowledgementEnum.HANDLED);
                        ack.acknowledgement(m, name);
                        System.out.println("Message handled: " + m + " by system " + name);
                        messages.remove(m);
                    }

                } catch (Exception e) {
                    System.err.println("Error in System "+getName() + " message" + e);
                    if(m!=null){
                        m.setStatus(AcknowledgementEnum.ERROR);
                        ack.acknowledgement(m, name);
                    }

                }
            }

        });
    }

}
