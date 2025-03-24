package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import model.Request;

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

    public List<Request> getPendingRequestsByManagerId(int managerId) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT r.* FROM Request r "
                + "JOIN Employee e ON r.employee_id = e.employee_id "
                + "WHERE e.manager_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, managerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Request req = new Request();
                req.setRid(rs.getInt("request_id"));
                req.setEid(rs.getInt("employee_id"));
                req.setCreated_date(rs.getDate("created_date"));
                req.setFrom(rs.getDate("from_date"));
                req.setTo(rs.getDate("to_date"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getInt("status"));
                req.setReject_reason(rs.getString("reject_reason"));
                requests.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }
    
    public List<Request> getOwnPendingRequests(int eid) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT r.* FROM Request r "
                + "WHERE r.employee_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, eid);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Request req = new Request();
                req.setRid(rs.getInt("request_id"));
                req.setEid(rs.getInt("employee_id"));
                req.setCreated_date(rs.getDate("created_date"));
                req.setFrom(rs.getDate("from_date"));
                req.setTo(rs.getDate("to_date"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getInt("status"));
                req.setReject_reason(rs.getString("reject_reason"));
                requests.add(req);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public Request getRequestById(int requestId) {
        String sql = "SELECT * FROM Request WHERE request_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, requestId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Request req = new Request();
                req.setRid(rs.getInt("request_id"));
                req.setEid(rs.getInt("employee_id"));
                req.setCreated_date(rs.getDate("created_date"));
                req.setFrom(rs.getDate("from_date"));
                req.setTo(rs.getDate("to_date"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getInt("status"));
                req.setReject_reason(rs.getString("reject_reason"));
                return req;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateRequestStatusById(int requestId, int status, String rejectReason) {
        String sql = "UPDATE Request SET status = ?, reject_reason = ? WHERE request_id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, status);
            st.setString(2, rejectReason);
            st.setInt(3, requestId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Request> getSubordinateRequestsByDateRange(int managerId, Date fromDate, Date toDate) {
    List<Request> requests = new ArrayList<>();
    String sql = "SELECT r.*, e.name FROM Request r "
               + "JOIN Employee e ON r.employee_id = e.employee_id "
               + "WHERE e.manager_id = ? "
               + "AND (\n" +
"    (r.from_date BETWEEN ? AND ?) OR\n" +
"    (r.to_date BETWEEN ? AND ?) OR\n" +
"    (? BETWEEN r.from_date AND r.to_date) OR\n" +
"    (? BETWEEN r.from_date AND r.to_date)\n" +
")";
    try (PreparedStatement st = connection.prepareStatement(sql)) {
        st.setInt(1, managerId);
        st.setDate(2, toDate);
        st.setDate(3, fromDate);
        ResultSet rs = st.executeQuery();
         List<Integer> subordinates = new ArrayList<>();
        while (rs.next()) {
            subordinates.add(rs.getInt("employee_id"));
            Request req = new Request();
            req.setRid(rs.getInt("request_id"));
            req.setEid(rs.getInt("employee_id"));
            req.setCreated_date(rs.getDate("created_date"));
            req.setFrom(rs.getDate("from_date"));
            req.setTo(rs.getDate("to_date"));
            req.setReason(rs.getString("reason"));
            req.setStatus(rs.getInt("status"));
            req.setReject_reason(rs.getString("reject_reason"));
            
            requests.add(req);
        } System.out.println("Danh sách cấp dưới: " + subordinates);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return requests;
}

}
