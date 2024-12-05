// 데이터베이스 연동 코드
package HotelKiosk;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    // 데이터베이스 연결 정보
    private static final String URL = "jdbc:mysql://localhost:3306/kiosk_DB";
    private static final String USER = "root";
    private static final String PASSWORD = "ghwns5120";

    public static boolean checkReservation(String name, String reservationNumber) {
        String query = "SELECT * FROM SelfCheckinInfo WHERE name = ? AND reservation_num = ?";

        try (Connection con = getConnection();  // getConnection()을 사용해 연결
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, reservationNumber);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<String[]> getReservations() {
        List<String[]> reservations = new ArrayList<>();
        String query = "SELECT * FROM SelfCheckinInfo";

        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String reservationNum = rs.getString("reservation_num");
                String roomType = rs.getString("room_type");
                String peopleNum = rs.getString("people_num");
                String phoneNum = rs.getString("phone_num");
                String breakfast = rs.getString("breakfast");
                String roomNumber = rs.getString("room_number");

                reservations.add(new String[]{id, name, reservationNum, roomType, peopleNum, phoneNum, breakfast, roomNumber});
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return reservations;
    }

    public static String getRoomNumberByReservationNumber(String reservationNum) {
        String roomNumber = null;
        String query = "SELECT room_number FROM SelfCheckinInfo WHERE reservation_num = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, reservationNum);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                roomNumber = rs.getString("room_number");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return roomNumber;
    }
    public static String getRandomAvailableRoomNumber(String roomType) {
        String query = "SELECT DISTINCT room_number FROM SelfCheckinInfo WHERE room_type = ? " +
                "AND room_number NOT IN (SELECT room_number FROM SelfCheckinInfo)";

        List<String> availableRooms = new ArrayList<>();

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, roomType);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                availableRooms.add(rs.getString("room_number"));
            }

            if (!availableRooms.isEmpty()) {
                int randomIndex = (int) (Math.random() * availableRooms.size());
                return availableRooms.get(randomIndex);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null; // 사용 가능한 방 번호가 없으면 null 반환
    }



    public static void insertReservation(String name, String reservationNum, String roomType, String phoneNum, String breakfast) {
        String query = "INSERT INTO SelfCheckinInfo (name, reservation_num, room_type, phone_num, breakfast, room_number) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String roomNumber = getRandomAvailableRoomNumber(roomType); // 랜덤 방 번호 가져오기

        if (roomNumber == null) {
            System.out.println("No available rooms for the selected room type.");
            return;
        }

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, reservationNum);
            pstmt.setString(3, roomType);
            pstmt.setString(4, phoneNum);
            pstmt.setString(5, breakfast);
            pstmt.setString(6, roomNumber); // String으로 처리된 방 번호 저장

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Reservation inserted successfully. Room number: " + roomNumber);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }



    public static void testConnection() {
        try (Connection con = getConnection()) {
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


