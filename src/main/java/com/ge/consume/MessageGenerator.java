package com.ge.consume;



import com.ge.dto.AcknowledgementEnum;
import com.ge.dto.Message;
import com.ge.dto.MessageObj;
import com.ge.dto.MessageOfSystem;
import com.ge.service.Ack;
import com.ge.supply.MessageHandler;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Generator of messages and broadcast to all registered handlers
 */
public class MessageGenerator implements Subject {

    private final static String nameSystem = "SystemA";
    private Random random = new Random();
    private List<MessageHandler> handlerList;
    private AtomicBoolean isRunning = new AtomicBoolean(true);
    private Ack ack;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public MessageGenerator(Ack ack){
        handlerList = new ArrayList<MessageHandler>();
        this.ack = ack;
    }

    /**
     * start generate messages
     * @throws InterruptedException
     */
    public void start(){
        generate();
    }

    private void generate(){
        executor.execute(() -> {
            while (isRunning.get()) {
                int count_messages = random.nextInt(15);
                int count_hours = random.nextInt(80);
                for(int i=0; i<count_messages; i++){
                    int priority = new Random().nextInt(10);
                    MessageObj messageObj = new MessageObj(nameSystem, priority, "test");
                    System.out.println(messageObj);
                    notifyHandlers(messageObj);
                }

                try {
                    TimeUnit.HOURS.sleep(count_hours);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resendMessage();
            }
        });
    }

    /**
     * 3 delivery attempts to concrete handler
     * if attempts failed remove undelivered message
     */
    private void resendMessage(){
        ack.getMessageList()
                .stream()
                .peek(m-> m.tryAttempt())
                .filter(m-> m.getAttempt() < 3)
                .forEach(m -> {
                    notifyHandlers(m.getMessage(), m.getNameSystem());
                });
        List<MessageOfSystem> messageOfSystems =
                    ack.getMessageList()
                .stream()
                .filter(m-> m.getAttempt() >= 3)
                .collect(Collectors.toList());
        ack.remove(messageOfSystems);
    }

    /**
     * register handler
     * @param handlerMessage
     */
    @Override
    public void registerHandler(MessageHandler handlerMessage) {
        handlerList.add(handlerMessage);
    }

    /**
     * remove handler
     * @param handlerMessage
     */
    @Override
    public void removeHandler(MessageHandler handlerMessage) {
        handlerList.remove(handlerMessage);
    }

    public List<MessageHandler> getHandlerList() {
        return handlerList;
    }

    /**
     * notify concrete handler about new message
     * @param messageObj
     * @param nameSystem
     */
    private void notifyHandlers(Message messageObj, String nameSystem) {
        handlerList.stream()
                .filter(h ->  nameSystem.equals(h.getName()))
                .forEach(h -> h.notify(messageObj));
    }

    @Override
    public void notifyHandlers(Message messageObj) {
        handlerList.stream()
                .peek(h ->  ack.addMessage(messageObj, h.getName()))
                .forEach(h -> {
                    h.notify(messageObj);
                    messageObj.setStatus(AcknowledgementEnum.SENDED);
                    ack.acknowledgement(messageObj, h.getName());
                });

    }

    public void stop() {
        isRunning.set(false);
        executor.shutdown();
    }
}
