/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tools {
    
    public static void readConfigFile() {
        File f = new File("Config.txt");
        if(!f.exists()) {
            return;
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(f))) {
            Params.HOST = bf.readLine();
            Params.PORT = bf.readLine();
            Params.DB = bf.readLine();
            Params.ACC = bf.readLine();
            Params.PASS = bf.readLine();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FormConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(
                    "jdbc:sqlserver://" + Params.HOST + ":"
                            + Params.PORT + ";databaseName=" + Params.DB,
                    Params.ACC, Params.PASS);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public static void main(String[] args) {
        System.out.println(getConn());
    }
}
