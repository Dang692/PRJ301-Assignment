package controller;

import dal.RequestDAO;
import dal.EmployeeDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Request;

import java.io.IOException;

@WebServlet(name = "ViewRequestServlet", urlPatterns = {"/viewRequest"})
public class ViewRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int rid = Integer.parseInt(request.getParameter("rid"));
            String employeeName = request.getParameter("name");
            RequestDAO reqDao = new RequestDAO();
            Request req = reqDao.getRequestById(rid);
            request.setAttribute("request", req);
            request.setAttribute("name", employeeName);

            request.getRequestDispatcher("viewRequest.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int rid = Integer.parseInt(request.getParameter("rid"));
            String action = request.getParameter("action");        
            RequestDAO reqDao = new RequestDAO();
            if ("approve".equals(action)) {
                reqDao.updateRequestStatusById(rid, 1, null);
            } else if ("reject".equals(action)) {   
                String rejectRea = request.getParameter("reject_reason");
                reqDao.updateRequestStatusById(rid, -1, rejectRea);
            }

            response.sendRedirect("home.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
