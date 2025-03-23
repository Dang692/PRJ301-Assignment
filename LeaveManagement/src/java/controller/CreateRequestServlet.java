package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dal.RequestDAO;
import dal.RequestDAO;
import model.Account;
import model.Request;
import java.io.IOException;
import java.sql.Date;


public class CreateRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("createRequest.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            
            
            String reason = request.getParameter("reason");
            String fromDateStr = request.getParameter("from_date");
            String toDateStr = request.getParameter("to_date");

            Date fromDate = Date.valueOf(fromDateStr);
            Date toDate = Date.valueOf(toDateStr);

            Request r = new Request();
            r.setEid(account.getEid()); 
            r.setCreated_date(new Date(System.currentTimeMillis()));           
            r.setFrom(fromDate);
            r.setTo(toDate);
            r.setReason(reason);
            r.setStatus(0);  // Mặc định Pending
            r.setReject_reason(null);

            RequestDAO dao = new RequestDAO();
            dao.insertRequest(r);

            request.setAttribute("success", "Insert  successfully");
            request.getRequestDispatcher("createRequest.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
