/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import configurations.Parameters;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author connected
 */
public class ConnectionFactory {
    
    public Connection getConnection() {
        StringBuilder dataBase = new StringBuilder("jdbc:postgresql://");
        
        dataBase.append(Parameters.adressDataBase);
        dataBase.append(":");
        dataBase.append(Parameters.portDataBase);
        dataBase.append("/");
        dataBase.append(Parameters.nameDataBase);
        
        try {
            return DriverManager.getConnection(dataBase.toString(), "connected", "");
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }   
}
