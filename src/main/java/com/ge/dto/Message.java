package com.ge.dto;

/**
 * Message with priority
 */
public interface Message {

    int getPriority();
    int getId();
    void setStatus(AcknowledgementEnum status);
    AcknowledgementEnum getStatus();
}
