package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// 호텔 예약 완료
public class ReservationFin extends JFrame {

    public ReservationFin(String reservationNumber) {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(reservationNumber), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 하단 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST);
        contentPane.add(new WestPanel(), BorderLayout.WEST);

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new ReservationFin("RES1122");
    }

    class NorthPanel extends JPanel {
        public NorthPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new GridBagLayout()); // GridBagLayout 사용

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0; // 가로 방향으로 확대

            // 빈 레이블 추가
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weighty = 0.1; // 빈 레이블의 세로 비율
            JLabel emptyLabel = new JLabel();
            emptyLabel.setOpaque(true);
            emptyLabel.setBackground(new Color(74, 69, 66));
            emptyLabel.setPreferredSize(new Dimension(700, 40)); // 세로 크기 조절
            add(emptyLabel, gbc); // 첫 번째 셀에 빈 레이블 추가

            // 로고 패널
            gbc.gridy = 1; // 두 번째 행
            gbc.weighty = 0.0; // 로고 패널의 세로 비율
            JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            logoPanel.setBackground(new Color(74, 69, 66));
            JLabel logo = new JLabel("");
            ImageIcon icon = new ImageIcon("images/logo.png");
            logo.setIcon(icon);
            logoPanel.add(logo);
            logoPanel.setPreferredSize(new Dimension(700,100));
            add(logoPanel, gbc); // 두 번째 셀에 로고 패널 추가

            logoPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new HotelKioskMain();
                    SwingUtilities.getWindowAncestor(NorthPanel.this).setVisible(false);
                }
            });

            JLabel leftBar = new JLabel();
            leftBar.setOpaque(true);
            leftBar.setBackground(new Color(255, 178, 165));
            leftBar.setPreferredSize(new Dimension(270, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(new Color(255, 178, 165));
            rightBar.setPreferredSize(new Dimension(270, 3));

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(74, 69, 66));
            JLabel textLabel = new JLabel("현장 예약");
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(new Font("Arial", Font.BOLD, 22)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700,70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }

    class CenterPanel extends JPanel {
        private String reservationNumber;
        private String roomNumber;
        private String roomType; // 방 타입을 저장할 필드

        public CenterPanel(String reservationNumber) {
            this.reservationNumber = reservationNumber; // 예약 번호 초기화
            this.roomNumber = getRoomNumber(reservationNumber); // 방 번호 가져오기
            this.roomType = getRoomType(reservationNumber); // 방 타입 가져오기

            setBackground(new Color(104, 90, 90));
            setLayout(new GridBagLayout()); // GridBagLayout 사용
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 간 간격
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.CENTER;

            // 예약 완료 메시지
            JLabel reservationFinLabel = new JLabel("예약이 완료되었습니다");
            reservationFinLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            reservationFinLabel.setForeground(Color.WHITE);
            gbc.gridy = 0;
            add(reservationFinLabel, gbc);

            // 방 타입에 맞는 이미지 표시
            JLabel roomImageLabel = getRoomImageLabel(roomType);
            gbc.gridy = 1;
            add(roomImageLabel, gbc);

            // 예약 번호 레이블
            JPanel numPanel = new JPanel();
            numPanel.setPreferredSize(new Dimension(80, 40));
            numPanel.setBackground(new Color(255, 178, 165));
            numPanel.setLayout(new GridBagLayout());
            JLabel resNumLabel = new JLabel("예약 번호");
            resNumLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            resNumLabel.setForeground(Color.WHITE);
            numPanel.add(resNumLabel);
            gbc.gridy = 2;
            add(numPanel, gbc);

            JLabel resNum = new JLabel(reservationNumber); // 예약 번호 표시
            resNum.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 20));
            resNum.setForeground(Color.WHITE);
            gbc.gridy = 3;
            add(resNum, gbc);

            // 방 번호 레이블
            JPanel titlePanel = new JPanel();
            titlePanel.setPreferredSize(new Dimension(80, 40));
            titlePanel.setBackground(new Color(255, 178, 165));
            titlePanel.setLayout(new GridBagLayout());
            JLabel roomLabel = new JLabel("방 번호");
            roomLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            roomLabel.setForeground(Color.WHITE);
            titlePanel.add(roomLabel);
            gbc.gridy = 4;
            add(titlePanel, gbc);

            // 방 번호 표시
            JLabel roomNumberLabel = new JLabel(roomNumber != null ? roomNumber : "정보 없음");
            roomNumberLabel.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 20));
            roomNumberLabel.setForeground(Color.WHITE);
            gbc.gridy = 5;
            add(roomNumberLabel, gbc);

        }

        // 방 번호를 이용해 방 타입 가져오기
        private String getRoomType(String reservationNumber) {
            String roomType = null;
            String query = "SELECT room_type FROM SelfCheckinInfo WHERE reservation_num = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, reservationNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        roomType = rs.getString("room_type");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return roomType;
        }

        // 방 타입에 맞는 이미지 경로 반환 및 크기 조정
        private JLabel getRoomImageLabel(String roomType) {
            String imagePath = "images/default.jpg"; // 기본 이미지 경로 (기본값)
            if (roomType != null) {
                switch (roomType) {
                    case "스탠다드 싱글":
                        imagePath = "images/room1.png"; // 스탠다드 싱글 이미지
                        break;
                    case "스탠다드 트윈":
                        imagePath = "images/room2.jpg"; // 스탠다드 트윈 이미지
                        break;
                    case "디럭스 더블":
                        imagePath = "images/room3.jpeg"; // 디럭스 더블 이미지
                        break;
                }
            }

            // 이미지 로드
            ImageIcon icon = new ImageIcon(imagePath);

            // 이미지 크기 조정 (예: 300x200)
            Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);

            // 조정된 이미지로 새로운 ImageIcon 생성
            ImageIcon scaledIcon = new ImageIcon(img);

            // JLabel 생성하여 리턴
            JLabel roomImageLabel = new JLabel(scaledIcon);

            return roomImageLabel;
        }


        // 방 번호를 가져오는 메서드
        private String getRoomNumber(String reservationNumber) {
            String roomNumber = null;
            String query = "SELECT room_number FROM SelfCheckinInfo WHERE reservation_num = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setString(1, reservationNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        roomNumber = rs.getString("room_number");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return roomNumber;
        }
    }




    class SouthPanel extends JPanel {
        private int remainingTime = 10; // 초기 카운트다운 시간

        public SouthPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 수직 정렬
            setPreferredSize(new Dimension(700, 100));

            // 상단 여백
            add(Box.createVerticalGlue());

            // 중앙에 배치할 JLabel
            JLabel infoLabel = new JLabel("10초 뒤 메인 화면으로 돌아갑니다.");
            infoLabel.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 16));
            infoLabel.setForeground(Color.GRAY);
            infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            add(infoLabel);

            // 하단 여백
            add(Box.createVerticalGlue());

            // Timer로 카운트다운 기능 구현
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (remainingTime > 0) {
                        remainingTime--;
                        infoLabel.setText(remainingTime + "초 뒤 메인 화면으로 돌아갑니다.");
                    } else {
                        ((Timer) e.getSource()).stop();
                        new HotelKioskMain();
                        ((JFrame) SwingUtilities.getWindowAncestor(SouthPanel.this)).dispose();

                    }
                }
            });
            timer.start();
        }
    }

    class WestPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public WestPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(60, 300));
        }
    }

    class EastPanel extends JPanel {
        //왼쪽 패널 빈공간 설정
        public EastPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(60, 300));
        }
    }
}