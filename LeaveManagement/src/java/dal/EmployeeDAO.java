/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO extends DBContext {
    
    public void getAllEmployees() {
        try {
            String sql = "SELECT employee_id FROM Employee";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int eid = rs.getInt("employee_id");
                
                System.out.println("Employee ID: " + eid );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL: " + e.getMessage());
        }
    }
}

