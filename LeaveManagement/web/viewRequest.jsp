<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Request" %>
<%
    Request requestObj = (Request) request.getAttribute("request");
    String name = (String) request.getAttribute("name");
    if (requestObj == null) {
        out.println("<h3>Không tìm thấy yêu cầu.</h3>");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chi tiết Đơn Xin Nghỉ</title>
</head>
<body>
    <h2>Chi tiết Đơn Xin Nghỉ</h2>
    <table border="1" cellpadding="10">
        <tr>
            <th>Mã số: </th>
            <td><%= requestObj.getEid() %></td>
        </tr>
        <tr>
            <th>Họ tên: </th>
            <td><%= name %></td>
        </tr>
        <tr>
            <th>Ngày tạo</th>
            <td><%= requestObj.getCreated_date() %></td>
        </tr>
        <tr>
            <th>Ngày bắt đầu nghỉ</th>
            <td><%= requestObj.getFrom() %></td>
        </tr>
        <tr>
            <th>Ngày kết thúc nghỉ</th>
            <td><%= requestObj.getTo() %></td>
        </tr>
        <tr>
            <th>Lý do</th>
            <td><%= requestObj.getReason() %></td>
        </tr>
        <tr>
            <th>Trạng thái</th>
            <td><%= requestObj.getStatus() == 0 ? "Chưa duyệt" : "Đã duyệt" %></td>
        </tr>
        
    </table>

    
        <h3>Lý do từ chối :</h3>
        <form action="viewRequest" method="post" style="margin-top: 20px;">
            <input type="hidden" name="rid" value="<%= requestObj.getRid() %>">
            <textarea name="reject_reason" rows="4" cols="40"><%= requestObj.getReject_reason()  %></textarea><br><br>
            <input type="submit" name="action" value="approve">
            <input type="submit" name="action" value="reject">
        </form>
    

    <br>
    <a href="pendingRequests">Quay lại danh sách đơn cần duyệt</a>
</body>
</html>
