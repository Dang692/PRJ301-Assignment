package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import dal.RequestDAO;
import model.Account;
import model.Request;

@WebServlet(name = "PendingRequestsServlet", urlPatterns = {"/pendingRequests"})
public class PendingRequestsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        RequestDAO requestDAO = new RequestDAO();
        List<Request> pendingRequests = requestDAO.getPendingRequestsByManagerId(account.getEid());

        request.setAttribute("pendingRequests", pendingRequests);
        request.getRequestDispatcher("pendingRequests.jsp").forward(request, response);
    }
}
