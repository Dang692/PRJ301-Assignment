package dal;

import model.Account;
import java.sql.*;

public class AccountDAO extends DBContext {

    public Account getAccount(String username, String password) {
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Account(rs.getString("username"), rs.getString("password"), rs.getInt("employee_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
