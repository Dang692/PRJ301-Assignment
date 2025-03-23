<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.Request" %>
<%@page import="java.util.*" %>
<%@page import="dal.EmployeeDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn cần duyệt</title>
</head>
<body>
    <h2>Danh sách đơn cần duyệt</h2>
    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th></th>
            <th>Ngày tạo</th>
            <th>Trạng thái</th>
            <th></th>
        </tr>
        <%
            
            List<Request> requests = (List<Request>) request.getAttribute("pendingRequests");
            if (requests != null && !requests.isEmpty()) {
            List<Integer> eids = new ArrayList<>();
        for (Request req : requests) {
            if (!eids.contains(req.getEid())) {
                eids.add(req.getEid());
            }
        }

        // Lấy HashMap chứa tên nhân viên dựa trên eid
        EmployeeDAO empDao = new EmployeeDAO();
        HashMap<Integer, String> employeeNames = empDao.getEmployeeNamesByIds(eids);

        for (Request req : requests) {
            String name = employeeNames.get(req.getEid()); // Lấy tên nhân viên từ HashMap
%>
            <tr>
                <td><%= name %></td>
                <td><%= req.getCreated_date() %></td>
                <td><%= req.getStatus() == 0 ? "Chưa duyệt" : "Đã duyệt" %></td>
                <td><a href="viewRequest?rid=<%= req.getRid() %>&name=<%= name %>">Xem đơn</a></td>
                    </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="4">Không có đơn cần duyệt</td>
            </tr>
        <%
            }
        %>
            
        
    </table>
    <br>
    <a href="home.jsp">Quay về Trang chủ</a>
</body>
</html>
