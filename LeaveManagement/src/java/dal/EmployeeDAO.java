package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;

public class EmployeeDAO extends DBContext {

    // Hàm lấy employee_name từ username
    public String getEmployeeNameByUsername(String username) {
        String sql = "SELECT e.employee_name "
                   + "FROM Account a "
                   + "JOIN Employee e ON a.employee_id = e.employee_id "
                   + "WHERE a.username = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("employee_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Nếu không tìm thấy
    }

    
    
}
