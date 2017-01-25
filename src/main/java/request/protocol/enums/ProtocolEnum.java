/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package request.protocol.enums;

import request.action.*;
import request.interfaces.RequestAction;

import javax.net.ssl.SSLSocket;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author connected
 */
public enum ProtocolEnum implements RequestAction {

    SEND_PUBLICKEY(1) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendPublicKey().execute(socket);
        }
    },

    RECEIVE_PUBLICKEY(2) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceivePublicKey().execute(socket);
        }
    },
    REQUEST_KEY_OF_CHAT(3) {
        @Override
        public void execute(final SSLSocket socket) {
            new RequestKeyOfChat().execute(socket);
        }
    },
    SEND_KEY_OF_CHAT(4) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendKeyOfChat().execute(socket);
        }
    },
    SEND_MESSAGE(5) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendMessage().execute(socket);
        }
    },
    RECEIVE_MESSAGE(6) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveMessage().execute(socket);
        }
    },
    REQUEST_UPDATE(7) {
        @Override
        public void execute(final SSLSocket socket) {
            new RequestUpdate().execute(socket);
        }
    },
    SEND_UPDATE(8) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendUpdate().execute(socket);
        }
    },
    SEND_DATE_OF_KEY(9) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendDateOfKey().execute(socket);
        }
    },
    SEND_RESULT_OF_STATUS_KEY(10) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendResultOfStatusKey().execute(socket);
        }
    },
    RECEIVE_DATE_OF_KEY(11) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveDateOfKey().execute(socket);
        }
    },
    RECEIVE_KEY_STATUS(12) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveKeyStatus().execute(socket);
        }
    },
    SEND_AUTHENTICATION(13) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendAuthentication().execute(socket);
        }
    },
    RECEIVE_AUTHENTICATION(14) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveAuthentication().execute(socket);
        }
    },
    SEND_RESULT_OF_AUTHENTICATION(15) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendResultOfAuthentication().execute(socket);
        }
    },
    RECEIVE_RESULT_OF_AUTHENTICATION(16) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveResultOfAuthentication().execute(socket);
        }
    },
    SEND_CONTACTS(17) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendContacts().execute(socket);
        }
    },
    RECEIVE_CONTACTS(18) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveContacts().execute(socket);
        }
    },
    SEND_STATE_OF_CONTACTS(19) {
        @Override
        public void execute(final SSLSocket socket) {
            new SendStateOfContacts().execute(socket);
        }
    },
    RECEIVE_CONTACTS_STATE(20) {
        @Override
        public void execute(final SSLSocket socket) {
            new ReceiveContactsState().execute(socket);
        }
    },
    ACTION_NOT_FOUND(127) {
        @Override
        public void execute(final SSLSocket socket) {
            new ActionNotFound().execute(socket);
        }
    };

    private int id;
    
    ProtocolEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Optional<ProtocolEnum> valueOf(int id) {
        return Arrays.stream(ProtocolEnum.values())
                .filter(protocolEnum -> protocolEnum.getId() == id)
                .findFirst();
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
