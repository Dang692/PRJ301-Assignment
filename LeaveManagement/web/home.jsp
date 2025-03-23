<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <%
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>

    <h2>Welcome, <%= account.getUsername() %>!</h2>
    <a href="logout">Log out</a>
</body>
</html>
