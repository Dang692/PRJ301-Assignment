package controller;

import dal.AccountDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Account;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        AccountDAO dao = new AccountDAO();
        Account account = dao.checkLogin(username, password);

        if (account != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("account", account);

            // Xử lý cookie nếu có "remember me"
            if (remember != null) {
                Cookie cookie = new Cookie("remember_" + username, password);
                cookie.setMaxAge(60 * 60 * 24 * 7); // Lưu 7 ngày
                response.addCookie(cookie);
            }

            response.sendRedirect("home.jsp");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", "Invalid username or password");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
