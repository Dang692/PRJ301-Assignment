package dal;

import java.sql.PreparedStatement;
import model.Request;
import java.sql.SQLException;

public class RequestDAO extends DBContext {
    public void insertRequest(Request r) throws SQLException {
        String sql = "INSERT INTO Request (employee_id, created_date, from_date, to_date, reason, status, reject_reason) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setInt(1, r.getEid());
        st.setDate(2, r.getCreated_date());
        st.setDate(3, r.getFrom());
        st.setDate(4, r.getTo());
        st.setString(5, r.getReason());
        st.setInt(6, r.getStatus());
        st.setString(7, r.getReject_reason());
        st.executeUpdate();
    }
}
