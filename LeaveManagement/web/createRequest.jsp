<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Account"%>
<%
    
    Account account = (Account) session.getAttribute("account");
    if (account == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tạo Đơn Xin Nghỉ</title>
</head>
<body>
    <h2>Tạo Đơn Xin Nghỉ</h2>
    <form action="createRequest" method="post">
        Lý do:<br>
        <textarea name="reason" rows="5" cols="30" required></textarea><br><br>
        Từ ngày: <input type="date" name="from_date" required><br><br>
        Đến ngày: <input type="date" name="to_date" required><br><br>
        <input type="submit" value="Gửi Đơn">
    </form>
    <br>
    <% 
    String success = (String) request.getAttribute("success");
    if (success != null) {
%>
        <div class="error-message"><%= success %></div>
<%
    }
%>
    <a href="home.jsp">Quay về Trang chủ</a>
</body>
</html>
