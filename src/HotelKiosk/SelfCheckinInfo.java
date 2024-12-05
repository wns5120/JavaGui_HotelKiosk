/* SelfCheckinInfo.java
 * 셀프 체크인 정보 확인 화면
 * SelfCheckinInitial.java에서 입력한 예약자명, 예약번호에 맞는 예약 정보를
 * Hotel 데이터베이스의 SelfCheckinInfo 테이블에서 받아와서 나타냄
 */
package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class SelfCheckinInfo extends JFrame {
    private String reservationNum;
    public SelfCheckinInfo(String name, String reservationNumber) {
        super("Hotel Kiosk");
        this.reservationNum = reservationNumber;
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        // 각 영역에 패널 추가
        contentPane.add(new NorthPanel(), BorderLayout.NORTH);
        contentPane.add(new CPanel(name, reservationNum), BorderLayout.CENTER);
        contentPane.add(new SPanel(), BorderLayout.SOUTH);
        contentPane.add(new WPanel(), BorderLayout.WEST);
        contentPane.add(new EPanel(), BorderLayout.EAST);

        setVisible(true);
    }

    public static void main(String[] args) {
        // 테스트용 코드 (임시 데이터 전달)
        new SelfCheckinInfo("이지원", "RES1122");
    }

    // 버튼 둥글게 만드는 클래스
    class RoundedButton extends JButton {
        private int arcWidth;
        private int arcHeight;

        public RoundedButton(String text, int arcWidth, int arcHeight) {
            super(text);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            setOpaque(false);
            setBorder(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2.dispose();
            super.paintComponent(g);
        }
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

    // 서쪽 패널
    class WPanel extends JPanel {
        public WPanel() {
            setBackground(new Color(74, 69, 66));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    // 동쪽 패널
    class EPanel extends JPanel {
        public EPanel() {
            setBackground(new Color(74, 69, 66));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    // 중앙 패널: 체크인 정보 표시
    class CPanel extends JPanel {
        private JCheckBox carCheckBox;
        public CPanel(String name, String reservationNum) {
            setPreferredSize(new Dimension(200, 100));
            setBackground(new Color(104, 90, 90));
            setLayout(new GridBagLayout());
            setBorder(BorderFactory.createLineBorder(new Color(255, 178, 165), 4));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            try (Connection con = DatabaseConnection.getConnection()) {
                String query = "SELECT room_type, people_num, phone_num, breakfast FROM SelfCheckinInfo WHERE name = ? AND reservation_num = ?";
                try (PreparedStatement pstmt = con.prepareStatement(query)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, reservationNum);

                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        addInfoLabel("예약 룸 타입", rs.getString("room_type"), gbc, 0);
                        addInfoLabel("예약 인원", rs.getString("people_num") + "인", gbc, 1);
                        addInfoLabel("예약자 연락처", rs.getString("phone_num"), gbc, 2);
                        addInfoLabel("조식 여부", rs.getString("breakfast"), gbc, 3);
                        JLabel carLabel = createLabel("차량 보유 여부");
                        gbc.gridx = 0;
                        gbc.gridy = 4;
                        add(carLabel, gbc);
                        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                        radioPanel.setBackground(new Color(104, 90, 90));
                        JRadioButton yesButton = new JRadioButton("예");
                        JRadioButton noButton = new JRadioButton("아니오");
                        yesButton.setBackground(new Color(104, 90, 90));
                        noButton.setBackground(new Color(104, 90, 90));
                        yesButton.setForeground(Color.WHITE);
                        yesButton.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
                        noButton.setForeground(Color.WHITE);
                        noButton.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
                        ButtonGroup group = new ButtonGroup();
                        group.add(yesButton);
                        group.add(noButton);
                        radioPanel.add(yesButton);
                        radioPanel.add(noButton);

                        gbc.gridx = 1;
                        add(radioPanel, gbc);

                    } else {
                        setEmptyLabels(gbc);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "데이터베이스 연결 오류: " + e.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
                setEmptyLabels(gbc);
            }
        }

        private void addInfoLabel(String label, String value, GridBagConstraints gbc, int row) {
            JLabel keyLabel = createLabel(label);
            JLabel valueLabel = createWhiteLabel(value);

            gbc.gridx = 0;
            gbc.gridy = row;
            add(keyLabel, gbc);

            gbc.gridx = 1;
            add(valueLabel, gbc);
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            label.setForeground(new Color(255, 178, 165));
            return label;
        }

        private JLabel createWhiteLabel(String text) {
            JLabel label = new JLabel(text);
            label.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 18));
            label.setForeground(Color.WHITE);
            return label;
        }

        private void setEmptyLabels(GridBagConstraints gbc) {
            for (int i = 0; i < 4; i++) {
                addInfoLabel("정보 없음", "", gbc, i);
            }
        }
    }

    // 하단 패널
    class SPanel extends JPanel {
        public SPanel() {
            setBackground(new Color(74, 69, 66));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(700, 200));

            JCheckBox confirmBox = new JCheckBox("예약하신 내역과 일치합니까?");
            confirmBox.setBackground(new Color(74, 69, 66));
            confirmBox.setForeground(Color.WHITE);
            confirmBox.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
            confirmBox.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬
            add(confirmBox);

            add(Box.createRigidArea(new Dimension(0, 20))); // 체크박스와 버튼 사이 여백

            RoundedButton confirmButton = new RoundedButton("확인", 20, 20);
            confirmButton.setBackground(new Color(255, 178, 165));
            confirmButton.setForeground(new Color(74, 69, 66));
            confirmButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 16));
            confirmButton.setPreferredSize(new Dimension(100, 50)); // 크기 확대
            confirmButton.setMinimumSize(new Dimension(100, 50));   // 최소 크기 설정
            confirmButton.setMaximumSize(new Dimension(100, 50));   // 최대 크기 설정
            confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT); // 중앙 정렬

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // "예약하신 내역과 일치합니까?" 체크박스 미체크
                    if (!confirmBox.isSelected()) {
                        JOptionPane.showMessageDialog(
                                SelfCheckinInfo.this,
                                "예약하신 내역과 일치하는지 확인하세요.",
                                "경고",
                                JOptionPane.WARNING_MESSAGE
                        );
                    } else {
                        SelfCheckinInfo.this.setVisible(false);
                        new SelfCheckinFin(reservationNum);
                    }
                }
            });

            add(confirmButton);

            add(Box.createRigidArea(new Dimension(0, 20)));
        }
    }

}
