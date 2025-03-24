<%-- 
    Document   : viewOwnRequest
    Created on : Mar 24, 2025, 6:18:13 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String statusParam = request.getParameter("status");
int status =  Integer.parseInt(statusParam) ;

String reason = request.getParameter("reason");

%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% if (status==0) { %>
        <h1>Chưa duyệt</h1>
        <% }else if (status==1) { %>    
        <h1>Approve</h1>
        <% }else{  %>
        <h1>Reject</h1>
        <p> lý do từ chối </p><!-- comment -->
        <textarea  rows="4" cols="40" readonly><%= reason  %></textarea>
        <% } %>
    </body>
</html>
