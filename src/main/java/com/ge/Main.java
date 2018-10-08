package com.ge;

import com.ge.service.ManagementSystem;
import com.ge.service.ManagementSystemImpl;
import com.ge.supply.MessageHandler;
import com.ge.supply.PriorityMessageHandler;

import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) throws InterruptedException {

        ManagementSystem managementSystem = new ManagementSystemImpl();
        MessageHandler handlerMessage1 = new PriorityMessageHandler(managementSystem.getAck(),"SystemB1");
        MessageHandler handlerMessage2 = new PriorityMessageHandler(managementSystem.getAck(),"SystemB2");
        MessageHandler handlerMessag3 = new PriorityMessageHandler(managementSystem.getAck(),"SystemB3");
        managementSystem.setHandlers(handlerMessage1, handlerMessage2, handlerMessag3);
        managementSystem.start();

        TimeUnit.SECONDS.sleep(30);

        managementSystem.stop();

    }
}
