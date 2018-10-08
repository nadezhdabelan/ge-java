package com.ge.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Message handled of concrete handler with parameter of attempts to delivery
 *
 */
public class MessageOfSystem implements Serializable {

    private Message message;
    private String nameSystem;
    private int attempt;

    public MessageOfSystem(Message message, String nameSystem) {
        this.message = message;
        this.nameSystem = nameSystem;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getNameSystem() {
        return nameSystem;
    }

    public void setNameSystem(String nameSystem) {
        this.nameSystem = nameSystem;
    }

    public int getAttempt() {
        return attempt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageOfSystem)) return false;
        MessageOfSystem that = (MessageOfSystem) o;
        return Objects.equals(message, that.message) &&
                Objects.equals(nameSystem, that.nameSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, nameSystem);
    }

    @Override
    public String toString() {
        return "MessageOfSystem{" +
                "message=" + message +
                ", nameSystem='" + nameSystem + '\'' +
                ", attempt=" + attempt +
                '}';
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void tryAttempt() {
        attempt ++;
    }
}
