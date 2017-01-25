/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import communication.exceptions.CommunicationFailException;
import configurations.Parameters;
import helper.IoHelper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;



/**
 *
 * @author pedro.sirtoli
 */
public class SocketHelper {

    public static void sendInt(final SSLSocket socket, final int integer) {
        DataOutputStream dataOutputStream;

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(integer);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new CommunicationFailException("Fail sending integer", ex);
        }
    }
    
    public static void sendText(final SSLSocket socket, final String text){
        DataOutputStream dataOutputStream;

        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(text);
            dataOutputStream.flush();
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            throw new CommunicationFailException("Fail sending text", ex);
        }
    }
    
    public static void sendObject(final SSLSocket socket, final Object object){
        ObjectOutputStream objectOutputStream;
        
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            throw new CommunicationFailException("Fail sending object", e);
        }
    }
    
    public static void sendFile(final SSLSocket socket, final Path path){
        final DataOutputStream dataOutputStream;
        final byte[] bytes;
        int count;
        
        try (FileInputStream fileInputStream = new FileInputStream(path.toFile())){

            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataOutputStream.writeUTF(path.getFileName().toString());

            
            bytes = new byte[socket.getReceiveBufferSize()];
            
            while((count = fileInputStream.read(bytes, 0, socket.getReceiveBufferSize())) >= 0){
                dataOutputStream.write(bytes, 0, count);
                dataOutputStream.flush();
            }
            
            
        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            throw new CommunicationFailException("Fail sending file", e);
        }
    }

    public static int readInteger(final SSLSocket socket){
        final int integer;
        final DataInputStream dataInputStream;

        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            integer = dataInputStream.readInt();
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationFailException("Fail reading integer", ex);
        }
        return integer;
    }

    public static String readText(final SSLSocket socket){
        final String text;
        final DataInputStream dataInputStream;
        
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            text = dataInputStream.readUTF();            
        } catch (IOException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationFailException("Fail reading text", ex);
        }
        return text;
    }
    
    public static Object readObject(final SSLSocket socket){
        final Object object;
        final ObjectInputStream objectInputStream;
        
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            object = objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, ex);
            throw new CommunicationFailException("Fail reading object", ex);
        } 
        return object;
    }
    
    public static Path readFile(final SSLSocket socket, final String folder){
        final Path pathFile;
        final String fileName;
        final DataInputStream dataInputStream;
        final FileOutputStream fileOutputStream;
        final byte[] bytes;
        int count;
         
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            fileName = dataInputStream.readUTF();

            if(IoHelper.isImage(fileName)){
                pathFile = Paths.get(Parameters.pathImages + "/" + folder + "/" + fileName);
            } else {
                pathFile = Paths.get(Parameters.pathFiles + "/" + folder + "/" + fileName);
            }

            fileOutputStream = new FileOutputStream(pathFile.toFile());
            bytes = new byte[socket.getSendBufferSize()];
            
            while((count = dataInputStream.read(bytes, 0, socket.getSendBufferSize())) >= 0){
                fileOutputStream.write(bytes, 0, count);
            }

            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (Exception e) {
            Logger.getLogger(SocketHelper.class.getName()).log(Level.SEVERE, null, e);
            throw new CommunicationFailException("Fail reading file", e);
        }
        
        return pathFile;
    }
}
