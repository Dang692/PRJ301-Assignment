<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="model.Employee" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="dal.EmployeeDAO" %>
<html>
<head><title>Home</title></head>
<body>
    <h2>Welcome, <%= session.getAttribute("employeeName") %></h2>
        
    <%
        Account acc = (Account) session.getAttribute("account"); 
        String user = acc.getUsername();
        String role = new AccountDAO().getRole(user) ;
        int eid = acc.getEid();
        
    %>
    
    <form action="logout" method="get">
        <input type="submit" value="Log Out">
    </form>
    
    <%
        if(!(role.equalsIgnoreCase("boss"))){
        %>
    <form action="createRequest" method="get">
        <input type="submit" value="Tạo đơn">
    </form>
    <% } %>
    <%
        if(!(role.equalsIgnoreCase("employee"))){
        %>
    <form action="pendingRequests" method="get">
        <input type="submit" value="Xem đơn cần duyệt">
    </form>
    <% } %>
    <%
        if(!(role.equalsIgnoreCase("boss"))){
        %>
    <form action="ownRequests" method="get">
        <input type="submit" value="Xem đơn mình gửi">
    </form>
    <% } %>
    <%
        if(!(role.equalsIgnoreCase("employee"))){
        %>
    <button onclick="window.location.href='suborSchedule.jsp'">
            Xem lịch nghỉ cấp dưới
    </button>
    <% } %>
</body>
</html>
