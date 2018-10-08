package com.ge.dto;


import java.io.Serializable;
import java.util.Comparator;
import java.util.Random;

public class MessageObj implements Message, Serializable {

    private Header header;
    private String payload;
    private int id;

    public MessageObj(String nameGenerator, int priority, String payload){
        this.id = new Random().nextInt(100000);
        this.header = new Header(nameGenerator, priority);
        this.payload = payload;
    }

    @Override
    public int getPriority() {
        return header.getPriority();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setAck(AcknowledgementEnum ack) {
        this.header.setAck(ack);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageObj)) return false;
        MessageObj that = (MessageObj) o;
        return this.getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return 31 * this.getId();
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setStatus(AcknowledgementEnum status) {
        this.header.setAck(status);
    }

    @Override
    public AcknowledgementEnum getStatus() {
        return this.header.getAck();
    }

    public static Comparator<Message> idComparator = new Comparator<Message>(){

        @Override
        public int compare(Message message1, Message message2) {
            if(message1.getPriority() < message2.getPriority()){
             return 1;
            }
            if(message1.getPriority() > message2.getPriority()){
                return -1;
            }
            return 0;
        }
    };

    @Override
    public String toString() {
        return "MessageObj{" +
                "header=" + header +
                ", payload='" + payload + '\'' +
                ", id=" + id +
                '}';
    }

}
