/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configurations;

/**
 *
 * @author root
 */
public class Parameters {
    public static String pathFiles = "/tmp/chatServer/";
    public static String pathImages = "/tmp/chatServer/";
    public static String adressDataBase = "localhost";
    public static String portDataBase = "5432";
    public static String nameDataBase = "connectedChat";
    public static String schemaDataBase = "public";
    public static Integer socketPort = 1212;
    public static Long timeToUpdateThreads = 1000l;
    public static Integer maxNumberOfThreads = 6;
    public static Integer numberOfRequisitionsPerTimeToAddNewThread = 15;
    public static Integer numberOfRequisitionsPerTimeToRemoveThread = 5;
    
}
