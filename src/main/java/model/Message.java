/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author pedro.sirtoli
 */
public class Message implements Serializable {
    private UUID guid;
    private String nickSender;
    private String nickReceiver;
    private String message;

    public Message() {
    }
    
    public Message(String nickSender, String nickReceiver, String message) {
        this.nickSender = nickSender;
        this.nickReceiver = nickReceiver;
        this.message = message;
    }
    
    public Message(UUID guid, String nickSender, String nickReceiver, String message) {
        this.guid = guid;
        this.nickSender = nickSender;
        this.nickReceiver = nickReceiver;
        this.message = message;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.guid);
        hash = 97 * hash + Objects.hashCode(this.nickSender);
        hash = 97 * hash + Objects.hashCode(this.nickReceiver);
        hash = 97 * hash + Objects.hashCode(this.message);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Message other = (Message) obj;
        if (!Objects.equals(this.nickSender, other.nickSender)) {
            return false;
        }
        if (!Objects.equals(this.nickReceiver, other.nickReceiver)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.guid, other.guid)) {
            return false;
        }
        return true;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }

    public String getNickSender() {
        return nickSender;
    }

    public void setNickSender(String nickSender) {
        this.nickSender = nickSender;
    }

    public String getNickReceiver() {
        return nickReceiver;
    }

    public void setNickReceiver(String nickReceiver) {
        this.nickReceiver = nickReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" + "guid=" + guid + ", nickSender=" + nickSender + ", nickReceiver=" + nickReceiver + ", message=" + message + '}';
    }
    
}
