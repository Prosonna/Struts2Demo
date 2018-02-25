/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author user
 */
public class DatabaseAccess {
    static final String  MYSQL_JDBC_DRIVER= "com.mysql.jdbc.Driver";
    static final String ORACLE_JDBC_DRIVER ="oracle.jdbc.driver.OracleDriver";
    static final String MYSQL_DB_URL ="jdbc:mysql://localhost:3306/test";
    static final String ORACLE_DB_URL ="jdbc:oracle:driver:username/password@//localhost:3306/test";
    static final String USER = "system";
    static final String PASS = "";
    static Connection connection = null;
    
    
    static void connectToMySql() throws Exception{
        Class.forName(MYSQL_JDBC_DRIVER);
        connection = DriverManager.getConnection(MYSQL_DB_URL,USER,PASS);
    }
    static void closeMysql()throws Exception{
        connection.close();
    }
    
}
