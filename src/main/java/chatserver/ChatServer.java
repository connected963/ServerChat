/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatserver;

import dto.Connections;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import tasks.AcceptConnections;

/**
 *
 * @author root
 */
public class ChatServer {

    public static Map<String, Connections> mapConnections;
    public static SSLServerSocket serverSocket;

    public ChatServer() throws IOException {
        AcceptConnections accept;
        ForkJoinPool pool;
        SSLServerSocketFactory factory;
        mapConnections = new ConcurrentHashMap<>();
        
        factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        serverSocket = (SSLServerSocket) factory.createServerSocket(1232);
        
        accept = new AcceptConnections(serverSocket);
        
        pool = new ForkJoinPool();
        
        pool.execute(accept);
        
        
        
        accept.join();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        new ChatServer();
    }

}
