/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

import java.util.List;
import dataBase.dto.Connections;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author pedro.sirtoli
 */
public class Catalog {
    private static Catalog instance;
    private static PrintWriter printf;
    private static File logFile;
    
    public static Catalog getInstance(){
        if(instance==null){
            instance = new Catalog();
        }
        return instance;
    }
    
    public String[] splitNick(String reciever){        
        return reciever.split(";");
    }
    
    public Connections findConnectionByNickname(List<Connections> list, String nickName){
        Optional<Connections> connectionFound;
        Connections connections = null;
        
        connectionFound = list.stream().filter(connection -> connection.getNickname().equals(nickName)).findFirst();
        
        if(connectionFound.isPresent()){
            connections = connectionFound.get();
        }
        
        return connections;
    }
    
    private String getLogFolder() throws URISyntaxException{
        return this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1) + "/log";
    }
    
    private File verifylogFolder(){
        File file = null;
        
        try {     
            file = new File(getLogFolder());
            if(!file.exists()){
                file.mkdir();
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Catalog.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }
    
    public static PrintWriter openFileLog(){
        FileWriter fileWriter;
        String dateNowFormatted = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        
        if(printf == null){
            try {
                logFile = new File(Catalog.getInstance().verifylogFolder().toPath() + "/" +dateNowFormatted + ".log");
                fileWriter = new FileWriter(logFile);
                printf = new PrintWriter(fileWriter);
            } catch (IOException ex) {
                Logger.getLogger(Catalog.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
//        else if(LocalDate.parse(logFile.getName().split(".")[0], "dd-MM-yyyy"))
        
        
        return printf;
    }
}
