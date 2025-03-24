<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Request" %>
<%@page import="java.util.*" %>
<%@page import="dal.EmployeeDAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <h2>Danh sách đơn đã gửi</h2>
        <table border="1" cellpadding="5" cellspacing="0">
            <tr>
               <th>Ngày tạo</th>
               <th>From</th>
               <th>To</th>
                <th>Trạng thái</th>
                <th></th>
            </tr>
            <%            
                List<Request> requests = (List<Request>) request.getAttribute("ownRequests");
                if (requests != null && !requests.isEmpty()) {
                for (Request req : requests){     
            %>
            <tr>

                <td><%= req.getCreated_date() %></td>
                <td><%= req.getFrom() %></td>
                <td><%= req.getTo() %></td>
                <td><%= req.getStatus() == 0 ? "Chưa duyệt" : "Đã duyệt" %></td>
                <td><a href="viewOwnRequest.jsp?status=<%= req.getStatus() %>&reason=<%= req.getReject_reason() %>">Xem kết quả</a></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4">Không có đơn </td>
            </tr>
            <%
                }
            %>


        </table>
        <br>
        <a href="home.jsp">Quay về Trang chủ</a>
    </body>
</html>
