/* SelfCheckinFin.java
 * 셀프 체크인 방번호 배정 및 종료 화면
 * SelfCheckinInfo.java에서 정보 확인 후 방배정
 * 방번호는 데이터베이스에 저장된 값 반영
 * 10초 대기 후 호텔 키오스크 메인 화면 (HotelKioskMain.java)으로 돌아감
 */
package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

// 호텔 셀프 체크인 완료 화면
public class SelfCheckinFin extends JFrame {

    public SelfCheckinFin(String reservationNumber) {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new NorthPanel(), BorderLayout.NORTH);
        contentPane.add(new CPanel(reservationNumber), BorderLayout.CENTER);
        contentPane.add(new WPanel(), BorderLayout.WEST);
        contentPane.add(new EPanel(), BorderLayout.EAST);
        contentPane.add(new SPanel(), BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        String reservationNumber = "RES0722";  // 테스트용 예약번호, 실제 사용 시 다른 방식으로 처리
        new SelfCheckinFin(reservationNumber);
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
            leftBar.setPreferredSize(new Dimension(250, 3));

            JLabel rightBar = new JLabel();
            rightBar.setOpaque(true);
            rightBar.setBackground(new Color(255, 178, 165));
            rightBar.setPreferredSize(new Dimension(250, 3));

            // 텍스트 패널
            gbc.gridy = 2; // 세 번째 행
            gbc.weighty = 0.0; // 텍스트 패널의 세로 비율
            JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            textPanel.setBackground(new Color(74, 69, 66));
            JLabel textLabel = new JLabel("셀프 체크인");
            textLabel.setForeground(Color.WHITE);
            textLabel.setFont(new Font("Arial", Font.BOLD, 22)); // 글꼴 및 크기 설정
            textPanel.setPreferredSize(new Dimension(700,70));

            textPanel.add(leftBar, BorderLayout.WEST);
            textPanel.add(textLabel, BorderLayout.CENTER);
            textPanel.add(rightBar, BorderLayout.EAST);

            add(textPanel, gbc); // 세 번째 셀에 텍스트 패널 추가
        }
    }

    class CPanel extends JPanel {
        private String reservationNumber;
        public CPanel(String reservationNumber) {
            this.reservationNumber = reservationNumber;
            setBackground(new Color(104, 90, 90));
            setLayout(new GridBagLayout()); // GridBagLayout 사용

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10); // 컴포넌트 간 간격
            gbc.fill = GridBagConstraints.CENTER;
            gbc.anchor = GridBagConstraints.CENTER;

            // 방 번호 제목 ("방 번호")를 담을 패널
            JPanel titlePanel = new JPanel();
            titlePanel.setPreferredSize(new Dimension(80, 40)); // 직사각형 크기 설정
            titlePanel.setBackground(new Color(255, 178, 165)); // 직사각형 배경색
            titlePanel.setLayout(new GridBagLayout()); // 중앙 정렬

            // 방 번호 제목 ("방 번호")
            JLabel roomLabel = new JLabel("방 번호");
            roomLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            roomLabel.setForeground(Color.WHITE); // 텍스트 색상
            titlePanel.add(roomLabel); // 텍스트를 패널에 추가
            gbc.gridy = 0; // 첫 번째 행
            add(titlePanel, gbc);

            // 데이터베이스에서 방 번호 가져오기
            String roomNumber = getRoomNumber(reservationNumber);
            updateCheckInStatus(reservationNumber); // 체크인 상태 업데이트

            // 방 번호 레이블
            JLabel roomNumberLabel = new JLabel(roomNumber != null ? roomNumber : "정보 없음");
            roomNumberLabel.setFont(new Font("Elephant", Font.PLAIN, 36)); // 글씨 크기 조정
            roomNumberLabel.setForeground(Color.WHITE);
            gbc.gridy = 1; // 두 번째 행
            add(roomNumberLabel, gbc);

            // 체크인 완료 메시지 ("체크인이 완료되었습니다.")
            JLabel completeMessage = new JLabel("체크인이 완료되었습니다.");
            completeMessage.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            completeMessage.setForeground(new Color(255, 178, 165));
            gbc.gridy = 2; // 세 번째 행
            add(completeMessage, gbc);

            // 안내 메시지 ("프론트에서 룸 키를 받아가세요.")
            JLabel subMessage = new JLabel("프론트에서 룸 키를 받아가세요.");
            subMessage.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 16));
            subMessage.setForeground(Color.WHITE);
            gbc.gridy = 3; // 네 번째 행
            add(subMessage, gbc);

            // 중앙에 패널 배치 정렬
            setPreferredSize(new Dimension(700, 500));
        }

        public String getRoomNumber(String reservationNumber) {
            String roomNumber = null;
            String query = "SELECT room_number FROM SelfCheckinInfo WHERE reservation_num = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, reservationNumber);  // 예약 번호로 조회

                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    roomNumber = rs.getString("room_number");  // 방 번호를 가져옴
                }

            } catch (SQLException e) {
                System.out.println("데이터베이스 오류: " + e.getMessage());
            }

            return roomNumber;
        }

        private void updateCheckInStatus(String reservationNumber) {
            String updateQuery = "UPDATE SelfCheckinInfo SET check_in = 'true' WHERE reservation_num = ?";

            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(updateQuery)) {

                pstmt.setString(1, reservationNumber);  // 예약 번호로 체크인 상태 업데이트
                pstmt.executeUpdate();  // 업데이트 실행

            } catch (SQLException e) {
                System.out.println("체크인 상태 업데이트 오류: " + e.getMessage());
            }
        }
    }

    class WPanel extends JPanel {
        public WPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class EPanel extends JPanel {
        public EPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class SPanel extends JPanel {
        private int remainingTime = 10; // 초기 카운트다운 시간

        public SPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // 수직 정렬
            setPreferredSize(new Dimension(700, 300));

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
                        ((JFrame) SwingUtilities.getWindowAncestor(SPanel.this)).dispose();

                    }
                }
            });
            timer.start();
        }
    }
}

