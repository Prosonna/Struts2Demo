<%-- 
    Document   : List
    Created on : May 31, 2015, 8:20:27 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List</title>
    </head>
    <body>

        <table>
            <s:iterator value="list">
                <tr>
                    <td><s:property value="firstName"/> </td>
                    <td><s:property value="lastName"/> </td>
                    <td><s:property value="email"/> </td>
                    <td><s:property value="address"/> </td>
                    <td><img src="<s:property value="imageFileName"/>"    width="100" height="100" /> </td>
                </tr>
            </s:iterator>


        </table>


    </body>
</html>
