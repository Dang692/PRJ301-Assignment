package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Account;

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check login session
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }
}
