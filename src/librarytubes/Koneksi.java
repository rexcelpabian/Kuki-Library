/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarytubes;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Rexcel
 */
public class Koneksi {
    Connection koneksi=null;
    public static Connection koneksiDb(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection Koneksi = DriverManager.getConnection("jdbc:mysql://localhost/db_library", "root","");
            return Koneksi;
        }
        
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    
    }
}
