/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase.dto;

import javax.net.ssl.SSLSocket;
import java.net.Socket;
import java.util.Objects;

/**
 *
 * @author pedro.sirtoli
 */
public class Connections {
    private final SSLSocket clientSocket;
    private final String nickname;

    public Connections(final SSLSocket clientSocket, final String nickname) {
        this.clientSocket = clientSocket;
        this.nickname = nickname;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.clientSocket);
        hash = 97 * hash + Objects.hashCode(this.nickname);
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
        final Connections other = (Connections) obj;
        if (!Objects.equals(this.nickname, other.nickname)) {
            return false;
        }
        if (!Objects.equals(this.clientSocket, other.clientSocket)) {
            return false;
        }
        return true;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "Connections{" + "clientSocket=" + clientSocket + ", nickname=" + nickname + '}';
    }

}
