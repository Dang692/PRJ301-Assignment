<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Account" %>
<%@ page import="model.Employee" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="dal.AccountDAO" %>
<%@ page import="dal.EmployeeDAO" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head><title>Home</title></head>
<body>
    <h2>Welcome, <%= session.getAttribute("employeeName") %></h2>
        
    <%
        Account acc = (Account) session.getAttribute("account"); 
        String user = acc.getUsername();
        String role = new AccountDAO().getRole(user) ;
        int eid = acc.getEid();
        ArrayList<Employee> hierachy = new EmployeeDAO().getAllSubor(eid);
        ArrayList<Employee> directSubor = new ArrayList<Employee>();
        ArrayList<Employee> otherSubor = new ArrayList<Employee>();
        for (Employee subor : hierachy){
            if(subor.getLevel() == 1){
                directSubor.add(subor);
            } else if(subor.getLevel() > 1) {
                otherSubor.add(subor);
            }
        }
        
    %>   
    
    <h2> Các cấp dưới trực tiếp:   </h2>
    
    <% 
        for (Employee subor : directSubor){
    %>  
    
    <span> <%= subor.getEname() + ", " %> </span>   
    
    <% } %>
    
    <div></div>
    
    <h2> Tất cả các cấp dưới khác :   </h2>
    <% 
        for (Employee subor : otherSubor){
    %>   
    
    <span> <%= subor.getEname() + ", " %> </span>
    
    <% } %>
    
    <div style="margin-top: 20px;">
    
    </div>
    
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
