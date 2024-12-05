package RoomKiosk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlarmDatabase {
    Connection con = null;
    static Statement stmt = null;
    String url = "jdbc:mysql://localhost:3306/kiosk_DB";
    String user = "root";
    String passwd = "ghwns5120";


    public AlarmDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, passwd);
            stmt = con.createStatement();
            System.out.println("success");
        }catch (Exception e) {
            System.out.println("failed " + e.toString());
        }
    }

    public void insertalarmlist(String ampm, int hour, int minute, boolean everyday, String selected_days) {
        String sql = "INSERT INTO alarm (ampm, hour, minute, everyday, selected_days) VALUES ('"
                + ampm + "', " + hour + ", " + minute + ", '" + everyday+ "', '" + selected_days  + "')";

        try {
            stmt.executeUpdate(sql);
            System.out.println("Alarm inserted successfully.");
        }catch (SQLException e) {
            System.out.println("Insertion failed: " + e.toString());
        }
    }



    //데이터 조회
    public List<Alarm> getAlarms(){
        List<Alarm> alarms = new ArrayList<>();
        String sql = "SELECT * FROM alarm";
        try(ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()) {
                int id = rs.getInt("id");
                String ampm = rs.getString("ampm");
                int hour = rs.getInt("hour");
                int minute = rs.getInt("minute");
                boolean everyday = rs.getBoolean("everyday");
                String selectedDays = rs.getString("selected_days");

                Alarm alarm = new Alarm(id, ampm, hour, minute, everyday, selectedDays);
                alarms.add(alarm);

            }
        }
        catch(SQLException e) {
            System.out.println("Failed to fetch alarms: "+ e.getMessage());
        }
        return alarms;
    }

    public void deleteAlarm(int id) {
        String sql = "DELETE FROM alarm WHERE id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Alarm deleted successfully.");
            } else {
                System.out.println("No alarm found with the given ID.");
            }
        } catch (SQLException e) {
            System.out.println("Deletion failed: " + e.getMessage());
        }
    }



    public void close() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.toString());
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new AlarmDatabase() ;

    }

}