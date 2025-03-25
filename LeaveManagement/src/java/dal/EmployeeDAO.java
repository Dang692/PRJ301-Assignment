package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public String getEmployeeNameById(int eid) {
    String name = null;
    String sql = "SELECT employee_name FROM Employee WHERE employee_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, eid);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            name = rs.getString("employee_name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return name;
    }
    
    // Thêm hàm lấy tất cả cấp dưới của một manager
public List<Integer> getAllSubordinates(int managerId) {
    List<Integer> subordinates = new ArrayList<>();
    String sql = "SELECT employee_id FROM Employee WHERE manager_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, managerId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            subordinates.add(rs.getInt("employee_id"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return subordinates;
}

public List<Employee> getSubordinatesByManagerId(int managerId) {
    List<Employee> subordinates = new ArrayList<>();
    String sql = "SELECT * FROM Employee WHERE manager_id = ?";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, managerId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Employee emp = new Employee(); 
            emp.setEid(rs.getInt("employee_id"));
            emp.setEname(rs.getString("employee_name"));
            subordinates.add(emp);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return subordinates;
}

    public ArrayList<Employee> getAllSubor(int managerId) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "WITH EmployeeHierarchy AS(\n"
                    + "	SELECT \n"
                    + "	employee_id,\n"
                    + "	employee_name,\n"
                    + "	manager_id,\n"
                    + "	0 as [Level]\n"
                    + "	FROM Employee\n"
                    + "	WHERE employee_id = ?\n"
                    + "\n"
                    + "	UNION ALL\n"
                    + "\n"
                    + "	SELECT e.employee_id,e.employee_name,e.manager_id, m.[Level] + 1 as [Level]  \n"
                    + "	FROM Employee e\n"
                    + "	INNER JOIN EmployeeHierarchy m ON m.employee_id = e.manager_id\n"
                    + ")\n"
                    + "SELECT * FROM \n"
                    + "	EmployeeHierarchy   \n";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, managerId);
            ResultSet rs = stm.executeQuery();
            
            while(rs.next())
            {
                    Employee e = new Employee();
                    e.setEid(rs.getInt("employee_id"));
                    e.setEname(rs.getString("employee_name"));
                    e.setManager_id(rs.getInt("manager_id")); 
                    e.setLevel(rs.getInt("Level"));
                    employees.add(e);
            }
            
        } catch (SQLException e) {
        e.printStackTrace();
    }
        return employees;
    }



}

    

