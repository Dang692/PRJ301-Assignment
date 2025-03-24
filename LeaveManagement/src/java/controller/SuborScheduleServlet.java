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
import model.Request;

public class SuborScheduleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy khoảng thời gian từ request
        Date fromDate = Date.valueOf(request.getParameter("fromDate"));
        Date toDate = Date.valueOf(request.getParameter("toDate"));

        RequestDAO requestDAO = new RequestDAO();
        EmployeeDAO dao = new EmployeeDAO();

        // Lấy tất cả các cấp dưới của user hiện tại
        List<Integer> subordinates = dao.getAllSubordinates(account.getEid());
        List<Request> requests = requestDAO.getSubordinateRequestsByDateRange(account.getEid(), fromDate, toDate);

        // Tạo map lưu trữ trạng thái làm/nghỉ của từng nhân viên theo từng ngày
        Map<String, Map<Date, String>> schedule = new LinkedHashMap<>();
        Calendar cal = Calendar.getInstance();

        // Khởi tạo dữ liệu mặc định "làm" cho tất cả các cấp dưới trong khoảng thời gian
        for (int subId : subordinates) {
            String empName = dao.getEmployeeNameById(subId);
            schedule.putIfAbsent(empName, new LinkedHashMap<>());

            cal.setTime(fromDate);
            while (!cal.getTime().after(toDate)) {
                schedule.get(empName).put(new Date(cal.getTimeInMillis()), "làm");
                cal.add(Calendar.DATE, 1);
            }
        }

        // Cập nhật trạng thái "nghỉ" dựa trên các yêu cầu nghỉ phép trong khoảng thời gian
        for (Request req : requests) {
            String empName = dao.getEmployeeNameById(req.getEid());
            cal.setTime(req.getFrom());
            while (!cal.getTime().after(req.getTo())) {
                if (!cal.getTime().before(fromDate) && !cal.getTime().after(toDate)) {
                    schedule.get(empName).put(new Date(cal.getTimeInMillis()), "nghỉ");
                }
                cal.add(Calendar.DATE, 1);
            }
        }

        request.setAttribute("schedule", schedule);
        request.setAttribute("fromDate", fromDate);
        request.setAttribute("toDate", toDate);

        request.getRequestDispatcher("suborSchedule.jsp").forward(request, response);
    }
}
