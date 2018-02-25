<%-- 
    Document   : index
    Created on : May 24, 2015, 7:20:43 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        
        
        <s:form action="LoginAction">
        <table>
            <tr> 
                <td> Username: </td>
                <td> <s:textfield name="username"/> </td>
            </tr> <br>
            <tr> 
                <td> Password: </td>
                <td> <s:password name="password"/> </td>
            </tr> <br>
            
            <tr>
                <td></td>
                <td> <s:submit value="Login"/> </td>
            </tr>
            
            
        </table>
            <s:property value="error"/> 
      </s:form>
        
    </body>
</html>
