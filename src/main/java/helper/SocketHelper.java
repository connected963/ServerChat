/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import configurations.Parameters;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;



/**
 *
 * @author pedro.sirtoli
 */
public class SocketHelper {
    
    public static void sendText(SSLSocket socket, String text){
        DataOutputStream dataOutputStream;

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendObject(SSLSocket socket, Object object){
        ObjectOutputStream objectOutputStream;
        
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void sendFile(SSLSocket socket, File file){
        FileInputStream fileInputStream;
        DataOutputStream dataOutputStream;
        byte[] bytes;
        int count;
        
        try {
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF(file.getName());
            
            fileInputStream = new FileInputStream(file);
            
            bytes = new byte[socket.getReceiveBufferSize()];
            
            while((count = fileInputStream.read(bytes, 0, socket.getReceiveBufferSize())) >= 0){
                dataOutputStream.write(bytes, 0, count);
                dataOutputStream.flush();
            }
            
            
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static String readText(SSLSocket socket){
        String text = null;
        DataInputStream dataInputStream;
        
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            text = dataInputStream.readUTF();            
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;
    }
    
    public static Object readObject(SSLSocket socket){
        Object object = null;
        ObjectInputStream objectInputStream;
        
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return object;
    }
    
    public static File readFile(SSLSocket socket, String folder){
        File file = null;
        String fileName;
        DataInputStream dataInputStream;
        FileOutputStream fileOutputStream;
        byte[] bytes;
        int count;
         
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            fileName = dataInputStream.readUTF();
            
            if(IoHelper.isImage(fileName)){
                fileOutputStream = new FileOutputStream(Parameters.pathImages + "/" + folder + "/" + fileName);
            } else {
                fileOutputStream = new FileOutputStream(Parameters.pathFiles + "/" + folder + "/" + fileName);
            }
            
            bytes = new byte[socket.getSendBufferSize()];
            
            while((count = dataInputStream.read(bytes, 0, socket.getSendBufferSize())) >= 0){
                fileOutputStream.write(bytes, 0, count);
            }
            
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, e);
        } 
        
        return file;
    }
}
