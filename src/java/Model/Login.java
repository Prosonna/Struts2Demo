/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class Login {
    
    private Statement stmnt = null;
    
    public  boolean getLoginInfo(String username, String password) throws Exception{
        DatabaseAccess.connectToMySql();
        String s = "select * from login where username = '"+username+"' and password='" +password+"'";
        stmnt = DatabaseAccess.connection.createStatement();
        ResultSet result = stmnt.executeQuery(s);
        while(result.next()){
            return true;
        }
        return false;
    }
    
    
    
    /*public static void main(String[] args) throws Exception{
        Login log = new Login();
        ResultSet s = log.getLoginInfo("aashik");
        
        while(s.next()){
            System.out.println(s.getString("username"));
            System.out.println(s.getString("password"));
        }
        
    }*/
    
}
