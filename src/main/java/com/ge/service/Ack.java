package com.ge.service;


import com.ge.dto.Message;
import com.ge.dto.MessageOfSystem;

import java.util.List;
import java.util.Set;

/**
 * System of acknowledgement
 * which is gather messages for different handlers and resend if message was not handle
 */
public interface Ack {

    void acknowledgement(Message message, String system);

    void addMessage(Message message, String system);

    Set<MessageOfSystem> getMessageList();

    void remove(List<MessageOfSystem> messageOfSystem);

}
