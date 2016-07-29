/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enums;

/**
 *
 * @author connected
 */
public enum ProtocolEnum {
    SEND_PUBLICKEY(1),
    RECEIVE_PUBLICKEY(2),
    REQUEST_KEY_OF_CHAT(3),
    SEND_KEY_OF_CHAT(4),
    SEND_MESSAGE(5),
    RECEIVE_MESSAGE(6),
    REQUEST_UPDATE(7),
    SEND_UPDATE(8),
    SEND_DATE_OF_KEY(9),
    SEND_RESULT_OF_STATUS_KEY(10),
    RECEIVE_DATE_OF_KEY(11),
    RECEIVE_KEY_STATUS(12),
    SEND_AUTHENTICATION(13),
    RECEIVE_AUTHENTICATION(14),
    SEND_RESULT_OF_AUTHENTICATION(15),
    RECEIVE_RESULT_OF_AUTHENTICATION(16),
    SEND_CONTACTS(17),
    RECEIVE_CONTACTS(18),
    SEND_STATE_OF_CONTACTS(19),
    RECEIVE_CONTACTS_STATE(20),
    
    ;
           
        
    private int id;
    
    private ProtocolEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
