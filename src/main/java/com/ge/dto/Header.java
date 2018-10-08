package com.ge.dto;

import java.io.Serializable;

public class Header implements Serializable {

    private String generatorName;
    private int priority;
    private AcknowledgementEnum ack;


    public Header(String name, int priority) {
        this.generatorName = name;
        this.priority = priority;
        this.ack = AcknowledgementEnum.GENERATED;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public void setGeneratorName(String generatorName) {
        this.generatorName = generatorName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public AcknowledgementEnum getAck() {
        return ack;
    }

    public void setAck(AcknowledgementEnum ack) {
        this.ack = ack;
    }

    @Override
    public String toString() {
        return "Header{" +
                "generatorName='" + generatorName + '\'' +
                ", priority=" + priority +
                ", ack=" + ack +
                '}';
    }
}
