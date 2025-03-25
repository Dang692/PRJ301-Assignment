<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.Date" %>
<%@ page import="model.Employee" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lịch nghỉ tổng hợp của cấp dưới</title>
</head>
<body>
    <h2>Lịch nghỉ tổng hợp của cấp dưới</h2>

    <form action="suborSchedule" method="get">
        Từ ngày: <input type="date" name="fromDate" required>
        Đến ngày: <input type="date" name="toDate" required>
        <button type="submit">Xem lịch nghỉ</button>
    </form>

    <%
        Map<Integer, Map<Date, String>> schedule = (Map<Integer, Map<Date, String>>) request.getAttribute("schedule");
        Date fromDate = (Date) request.getAttribute("fromDate");
        Date toDate = (Date) request.getAttribute("toDate");
        List<Employee> subordinates = (List<Employee>)request.getAttribute("subordinates"); 
        if (schedule != null && fromDate != null && toDate != null) {
            Calendar cal = Calendar.getInstance();
    %>

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>Id nhân viên</th>
            <th>Tên nhân viên</th>
            <%
                cal.setTime(fromDate);
                while (!cal.getTime().after(toDate)) {
            %>
            <th><%= new Date(cal.getTimeInMillis()) %></th>
            <%
                    cal.add(Calendar.DATE, 1);
                }
            %>
        </tr>
        <%
            for (Employee sub : subordinates){
                int subId = sub.getEid();
                String subName = sub.getEname();
                Map<Date, String> currentSchedule = schedule.get(subId);
        %>
        
        <tr>
            <td><%= subId %></td>
            <td><%= subName %></td>
            <%
                cal.setTime(fromDate);
                while (!cal.getTime().after(toDate)) {
                    Date currentDate = new Date(cal.getTimeInMillis());
                    String status = currentSchedule.get(currentDate);
            %>
            <td><%= status %></td>
            <%
                    cal.add(Calendar.DATE, 1);
                }
            %>
        </tr>
        <%
            }
        %>
    </table>
    <%
        }
    %>
    <br>
    <a href="home.jsp">Quay về Trang chủ</a>
</body>
</html>
