package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;
import java.sql.*;
import java.util.HashMap;
import java.util.List;

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

    public HashMap<Integer, String> getEmployeeNamesByIds(List<Integer> eids) {
        HashMap<Integer, String> employeeNames = new HashMap<>();

        if (eids == null || eids.isEmpty()) {
            return employeeNames;
        }

        StringBuilder sql = new StringBuilder("SELECT employee_id, employee_name FROM Employee WHERE employee_id IN (");
        for (int i = 0; i < eids.size(); i++) {
            sql.append("?");
            if (i < eids.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(")");

        try {
            PreparedStatement stm = connection.prepareStatement(sql.toString());
            for (int i = 0; i < eids.size(); i++) {
                stm.setInt(i + 1, eids.get(i));
            }
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int eid = rs.getInt("employee_id");
                String name = rs.getString("employee_name");
                employeeNames.put(eid, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeNames;
    }
}

    

