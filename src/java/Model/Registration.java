/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;
import Controller.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author user
 */
public class Registration {
    private Statement stmnt = null;
    public boolean insertintoRegistration(RegistrationAction reg) throws Exception{
        DatabaseAccess.connectToMySql();
        /*String sql = "insert into registration values('"
                +reg.getEmail() +"','"
                +reg.getPassword()+"','"
                +reg.getFirstName()+ "','"
                +reg.getLastName() + "','"
                +reg.getAddress() +"',"
                +Integer.parseInt(reg.getAge())+")";*/
        
        String sql = "insert into registration values(?,?,?,?,?,?,?)";
        PreparedStatement s = DatabaseAccess.connection.prepareStatement(sql);
        s.setString(1, reg.getEmail());
        s.setString(2, reg.getPassword());
        s.setString(3, reg.getFirstName());
        s.setString(4, reg.getLastName());
        s.setString(5, reg.getAddress());
        s.setInt(6, Integer.parseInt(reg.getAge()));
        s.setBlob(7, new FileInputStream(reg.getImage()));
        int i = s.executeUpdate();
        
        //stmnt = DatabaseAccess.connection.createStatement();
        /*String sql2 = "insert into login values('"
                + reg.getEmail()+ "','"
                +reg.getPassword()+"')";*/
        
        String sql2= "insert into login values(?,?)";
        s = DatabaseAccess.connection.prepareStatement(sql2);
        s.setString(1, reg.getEmail());
        s.setString(2, reg.getPassword());
        
        i += s.executeUpdate();
        if (i == 2){
            return true;
        }
        else{
            return false;
        }
    }
    
    public ResultSet getRegistrationInfo() throws Exception{
        DatabaseAccess.connectToMySql();
        stmnt = DatabaseAccess.connection.createStatement();
        String sql = "select * from registration";
        return stmnt.executeQuery(sql);
    } 
}
