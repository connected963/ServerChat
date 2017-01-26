/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dataBase.dto.Connections;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 *
 * @author root
 */
public class ChatServer {

    public ChatServer() throws IOException {
        final ThreadManager threadManager = new ThreadManager();
        threadManager.manangeServerRequests();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new ChatServer();
    }

}
