<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.*" %>
<%@page import="java.sql.Date" %>
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
        Map<String, Map<Date, String>> schedule = (Map<String, Map<Date, String>>) request.getAttribute("schedule");
        Date fromDate = (Date) request.getAttribute("fromDate");
        Date toDate = (Date) request.getAttribute("toDate");

        if (schedule != null && fromDate != null && toDate != null) {
            Calendar cal = Calendar.getInstance();
    %>

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>Nhân viên</th>
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
            for (Map.Entry<String, Map<Date, String>> entry : schedule.entrySet()) {
                String empName = entry.getKey();
                Map<Date, String> empSchedule = entry.getValue();
        %>
        <tr>
            <td><%= empName %></td>
            <%
                cal.setTime(fromDate);
                while (!cal.getTime().after(toDate)) {
                    Date currentDate = new Date(cal.getTimeInMillis());
                    String status = empSchedule.getOrDefault(currentDate, "làm");
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
