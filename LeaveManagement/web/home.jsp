<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<html>
<head><title>Home</title></head>
<body>
    <h2>Welcome, <%= session.getAttribute("employeeName") %></h2>
    <form action="logout" method="get">
        <input type="submit" value="Log Out">
    </form>
    <form action="createRequest" method="get">
        <input type="submit" value="Tạo đơn">
    </form>
    <form action="pendingRequests" method="get">
        <input type="submit" value="Xem đơn cần duyệt">
    </form>
</body>
</html>
