/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;

import com.opensymphony.xwork2.ActionSupport;
import Model.Login;
/**
 *
 * @author user
 */
public class LoginAction extends ActionSupport{
    private String username;
    private String password;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String execute() throws Exception {
       
        Login login = new Login();
        if(login.getLoginInfo(username, password)){
            error="You are Successfully loged in!!!!";
            return SUCCESS;
        }
        error="Your username and password combination is wrong!";
        return INPUT;
    }

    @Override
     public void validate() {
        if(getUsername().length() == 0){
            addFieldError("username", "Username cann't be Empty");
        }
        else if(getPassword().length() == 0){
            addFieldError("password", "Password Can't be Empty");
        }
    }
}
