package com.ge.service;


import com.ge.dto.Message;
import com.ge.dto.MessageOfSystem;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ge.dto.AcknowledgementEnum.ERROR;
import static com.ge.dto.AcknowledgementEnum.HANDLED;


public class AckImpl implements Ack {

    private final Set<MessageOfSystem> messageList;

    public AckImpl(){
        messageList = Collections.synchronizedSet(new HashSet<MessageOfSystem>());
    }


    @Override
    public void acknowledgement(Message message, String system) {
        MessageOfSystem messageOfSystem = new MessageOfSystem(message, system);
        if (HANDLED.equals(message.getStatus())) {
            messageList.remove(messageOfSystem);
        } else {
            messageList.add(messageOfSystem);
        }
    }

    @Override
    public void addMessage(Message message, String system) {
        MessageOfSystem messageOfSystem = new MessageOfSystem(message, system);
        messageList.add(messageOfSystem);
    }

    public Set<MessageOfSystem> getMessageList() {
        return messageList;
    }

    public void remove(List<MessageOfSystem> messageOfSystem) {
        messageList.remove(messageOfSystem);
    }
}
