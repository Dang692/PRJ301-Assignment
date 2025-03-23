package controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hủy session
        HttpSession session = request.getSession();
        session.invalidate();

        // Không xóa cookie remember, chỉ logout
        response.sendRedirect("login.jsp");
    }
}
