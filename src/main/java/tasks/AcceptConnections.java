/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tasks;

import dto.Connections;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author connected
 */
public class AcceptConnections extends RecursiveTask<Connections>{
    static List<AcceptConnections> threads;
    SSLServerSocket serverSocket;

    public AcceptConnections(SSLServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        threads = new ArrayList<>();
        System.out.println(serverSocket.getInetAddress());
    }
    
    @Override
    protected Connections compute() {
        try {
            while(threads.size() <= 1) {
                SSLSocket socket;
                AcceptConnections task;

                System.out.println("");
                System.out.println("Waiting for connection -> " + serverSocket.getInetAddress());
                
                socket = (SSLSocket) serverSocket.accept();
                
                //task = new AcceptConnections(serverSocket);
                //task.fork();
                
                //threads.add(task);
                
                System.out.println("Connection established with -> "+socket.getInetAddress()); 
                System.out.println("");
              
            }
            //threads.remove(this);
            
        } catch (IOException ex) {
            Logger.getLogger(AcceptConnections.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
