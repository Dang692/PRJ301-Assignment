package controller;

import dal.EmployeeDAO;
import dal.RequestDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import model.Employee;
import model.Request;

public class SuborScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    Account account = (Account) request.getSession().getAttribute("account");
    if (account == null) {
        response.sendRedirect("login");
        return;
    }

    try {
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));

        // Lấy danh sách các cấp dưới của người dùng
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Employee> subordinates = employeeDAO.getSubordinatesByManagerId(account.getEid());
        RequestDAO requestDAO = new RequestDAO();
        List<Request> requests = requestDAO.getSubordinateRequestsByDateRange(account.getEid(), fromDate, toDate);
        Map<Integer, Map<Date, String>> schedule = new LinkedHashMap<>();
        
        

        // Tạo schedule chứa tất cả các cấp dưới
        
        for (Employee subordinate : subordinates) {
            Map<Date, String> empSchedule = new LinkedHashMap<>();
            Calendar cal = Calendar.getInstance();
            cal.setTime(fromDate);
            while (!cal.getTime().after(toDate)) {
                empSchedule.put(new Date(cal.getTimeInMillis()), "làm"); // mặc định là làm việc
                cal.add(Calendar.DATE, 1);
            }
            schedule.put(subordinate.getEid(), empSchedule);
        }

        
        

        // Cập nhật trạng thái nghỉ dựa trên các yêu cầu nghỉ phép
        for (Employee subordinate : subordinates) {
            for (Request req : requests) {
                if (req.getEid() == subordinate.getEid()) { // kiểm tra request của cấp dưới đang xét
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fromDate);

                    while (!cal.getTime().after(toDate)) {
                        Date currentDate = new Date(cal.getTimeInMillis());
                        if (!currentDate.before(req.getFrom()) && !currentDate.after(req.getTo())) {
                            Map<Date, String> currentDateSchedule = schedule.get(subordinate.getEid());
                            currentDateSchedule.put(currentDate, "nghỉ");
                            schedule.put(subordinate.getEid(), currentDateSchedule);
                        }
                        cal.add(Calendar.DATE, 1);
                    }
                }
            }
        }

        // Đặt schedule lên request để truyền sang JSP
        request.setAttribute("schedule", schedule);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);
        request.setAttribute("subordinates", subordinates);

        request.getRequestDispatcher("suborSchedule.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("error.jsp");
    }
}

}
