package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
public class ReservationInfo extends JFrame {
    public ReservationInfo() {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());
        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 하단 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST);
        contentPane.add(new WestPanel(), BorderLayout.WEST);

        setVisible(true); // 프레임을 화면에 표시
    }

    public static void main(String[] args) {
        new ReservationInfo();
    }

    class CenterPanel extends JPanel {
        public CenterPanel() {
            setBackground(new Color(104, 90, 90));
            setLayout(new GridBagLayout()); // GridBagLayout 사용

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(7, 7, 7, 7); // 여백 설정
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JLabel roomLabel = createLabel("선택한 방:");
            roomLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(roomLabel, gbc);

            JLabel roomTypeLabel = createLabel(ReservationInitial.selectedRoomType);
            roomTypeLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 1;
            gbc.gridy = 0;
            add(roomTypeLabel, gbc);

            // 예약자 성명 레이블
            JLabel nameLabel = createLabel("예약자 성명:");
            nameLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(nameLabel, gbc);

            // 예약자 성명 입력 필드
            RoundedTextField nameField = new RoundedTextField("   예약하시는 분의 성함을 입력해 주세요.", 25, 25);
            nameField.setBackground(Color.WHITE);
            nameField.setForeground(Color.GRAY);
            nameField.setPreferredSize(new Dimension(300, 25));
            nameField.setMinimumSize(new Dimension(300, 25));
            nameField.setSize(new Dimension(300, 25));
            gbc.gridx = 1;
            add(nameField, gbc);

            // 예약자 휴대폰 번호 레이블
            JLabel phoneLabel = createLabel("예약자 휴대폰 번호:");
            phoneLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 2;
            add(phoneLabel, gbc);

            // 예약자 휴대폰 번호 입력 필드
            RoundedTextField phoneField = new RoundedTextField("   휴대폰 번호를 입력해 주세요.", 25, 25);
            phoneField.setBackground(Color.WHITE);
            phoneField.setForeground(Color.GRAY);
            phoneField.setPreferredSize(new Dimension(300, 25));
            phoneField.setMinimumSize(new Dimension(300, 25));
            phoneField.setSize(new Dimension(300, 25));
            gbc.gridx = 1;
            add(phoneField, gbc);

            // 조식 여부 레이블
            JLabel breakfastLabel = createLabel("조식 여부:");
            breakfastLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 3;
            add(breakfastLabel, gbc);

            // 조식 여부 체크박스
            JCheckBox breakfastCheckBox = new JCheckBox("조식 포함");
            breakfastCheckBox.setBackground(new Color(104, 90, 90));
            breakfastCheckBox.setForeground(Color.WHITE);
            breakfastCheckBox.setFont(new Font("KoPubDotum Bold", Font.BOLD, 16)); // 글자 크기 조정
            gbc.gridx = 1;
            add(breakfastCheckBox, gbc);

            // 인원수
            JLabel reservationNumLabel = createLabel("예약 인원:");
            reservationNumLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 4;
            add(reservationNumLabel, gbc);

            RoundedTextField reservationNumField = new RoundedTextField("   숫자만 적어주세요", 25, 25);
            reservationNumField.setBackground(Color.WHITE);
            reservationNumField.setForeground(Color.GRAY);
            reservationNumField.setPreferredSize(new Dimension(100, 25));
            reservationNumField.setMinimumSize(new Dimension(100, 25));
            reservationNumField.setSize(new Dimension(100, 25));
            gbc.gridx = 1;
            add(reservationNumField, gbc);

            // 체크인 날짜 레이블
            JLabel checkInLabel = createLabel("체크인 날짜:");
            gbc.gridx = 0;
            gbc.gridy = 5;
            add(checkInLabel, gbc);

            // 체크인 날짜 입력 필드
            RoundedTextField checkInField = new RoundedTextField("   체크인 날짜를 입력해 주세요.", 25, 25);
            checkInField.setBackground(Color.WHITE);
            checkInField.setForeground(Color.GRAY);
            checkInField.setPreferredSize(new Dimension(300, 50)); // 크기 조정
            gbc.gridx = 1;
            add(checkInField, gbc);

            // 체크아웃 날짜 레이블
            JLabel checkOutLabel = createLabel("체크아웃 날짜:");
            gbc.gridx = 0;
            gbc.gridy = 6;
            add(checkOutLabel, gbc);

            // 체크아웃 날짜 입력 필드
            RoundedTextField checkOutField = new RoundedTextField("   체크아웃 날짜를 입력해 주세요.", 25, 25);
            checkOutField.setBackground(Color.WHITE);
            checkOutField.setForeground(Color.GRAY);
            checkOutField.setPreferredSize(new Dimension(300, 50)); // 크기 조정
            gbc.gridx = 1;
            add(checkOutField, gbc);

            JLabel carLabel = createLabel("차량 보유 여부");
            gbc.gridx = 0;
            gbc.gridy = 7;
            add(carLabel, gbc);
            JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            radioPanel.setBackground(new Color(104, 90, 90));
            JRadioButton yesButton = new JRadioButton("예");
            JRadioButton noButton = new JRadioButton("아니오");
            yesButton.setBackground(new Color(104, 90, 90));
            yesButton.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
            noButton.setBackground(new Color(104, 90, 90));
            yesButton.setForeground(Color.WHITE);
            noButton.setForeground(Color.WHITE);
            noButton.setFont(new Font("KoPubDotum Bold", Font.PLAIN, 14));
            ButtonGroup group = new ButtonGroup();
            group.add(yesButton);
            group.add(noButton);
            radioPanel.add(yesButton);
            radioPanel.add(noButton);

            gbc.gridx = 1;
            add(radioPanel, gbc);

            // 결제하기 버튼 추가
            RoundedButton paymentButton = new RoundedButton("결제하기");
            paymentButton.setPreferredSize(new Dimension(100, 40)); // 버튼 크기 조정
            paymentButton.setFont(new Font("KoPubDotum Bold", Font.BOLD, 20));
            paymentButton.setBackground(new Color(254, 190, 152)); // 버튼 배경색
            paymentButton.setForeground(Color.DARK_GRAY);

            paymentButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 입력 값 가져오기
                    String name = nameField.getText().trim();
                    String phoneNum = phoneField.getText().trim();
                    String roomType = ReservationInitial.selectedRoomType;
                    boolean breakfastIncluded = breakfastCheckBox.isSelected();
                    String breakfast = breakfastIncluded ? "O" : "X";

                    // 예약 번호 생성
                    String reservationNum = generateReservationNumber();

                    // 방 번호 생성 (101~2000 사이의 랜덤 값)
                    int roomNumber = (int) (Math.random() * (2000 - 101 + 1)) + 101;

                    // 인원 수 입력 확인
                    String peopleNum = reservationNumField.getText().trim();
                    if (peopleNum.isEmpty()) {
                        JOptionPane.showMessageDialog(paymentButton, "예약 인원을 입력해야 합니다.");
                        return;
                    }

                    // 데이터베이스에 저장
                    try (Connection con = DatabaseConnection.getConnection();
                         PreparedStatement pstmt = con.prepareStatement(
                                 "INSERT INTO SelfCheckinInfo (name, reservation_num, room_type, people_num, phone_num, breakfast, room_number) " +
                                         "VALUES (?, ?, ?, ?, ?, ?, ?)")) {

                        pstmt.setString(1, name);             // 예약자명
                        pstmt.setString(2, reservationNum);  // 예약 번호
                        pstmt.setString(3, roomType);        // 방 타입
                        pstmt.setString(4, peopleNum);       // 예약 인원
                        pstmt.setString(5, phoneNum);        // 휴대폰 번호
                        pstmt.setString(6, breakfast);       // 조식 여부
                        pstmt.setString(7, String.valueOf(roomNumber)); // 방 번호 (문자열로 변환)

                        int rowsInserted = pstmt.executeUpdate();
                        /*
                        if (rowsInserted > 0) {
                            JOptionPane.showMessageDialog(paymentButton, "예약 정보가 저장되었습니다.\n" +
                                    "예약 번호: " + reservationNum + "\n방 번호: " + roomNumber);
                        } else {
                            JOptionPane.showMessageDialog(paymentButton, "예약 정보를 저장하지 못했습니다.");
                        }*/
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(paymentButton, "데이터베이스 오류: " + ex.getMessage());
                    }
                    ReservationInfo.this.setVisible(false);
                    new ReservationFin(reservationNum);
                }
            });


            gbc.insets = new Insets(30, 150, 5, 150);
            gbc.gridx = 0;
            gbc.gridy = 8; // 버튼 위치 설정
            gbc.gridwidth = 2; // 버튼이 두 개의 열을 차지하도록 설정
            add(paymentButton, gbc);
        }

        private JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("KoPubDotum Bold", Font.BOLD, 18)); // 글자 크기 조정
            return label;
        }

        // 예약 번호 생성 메서드
        private String generateReservationNumber() {
            return "RES" + String.format("%04d", (int) (Math.random() * 10000));  // 4자리 랜덤 숫자
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

    class RoundedTextField extends JTextField {
        private int arcWidth;
        private int arcHeight;
        private String placeholderText;

        public RoundedTextField(String text, int arcWidth, int arcHeight) {
            super(text);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            this.placeholderText = text;

            setOpaque(false); // 배경을 투명하게 설정
            setBorder(null); // 기본 테두리 제거
            setForeground(Color.GRAY); // 기본 텍스트 색상 설정

            // 포커스 리스너 추가 (텍스트 필드를 클릭하면 텍스트가 사라지고, 포커스를 잃으면 다시 보이게 함)
            addFocusListener(new java.awt.event.FocusListener() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (getText().equals(placeholderText)) {
                        setText(""); // 텍스트를 비움
                        setForeground(Color.BLACK); // 텍스트 색상을 검정색으로 설정
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (getText().isEmpty()) {
                        setText(placeholderText); // 텍스트가 없으면 플레이스홀더 텍스트를 다시 설정
                        setForeground(Color.GRAY); // 텍스트 색상을 회색으로 설정
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 배경 색상
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);

            g2.dispose();
            super.paintComponent(g);
        }
    }



    class SouthPanel extends JPanel {
        public SouthPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(700, 100));
        }
    }

    class RoundedButton extends JButton {
        public RoundedButton(String label) {
            super(label);
            setBorderPainted(false);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 모서리를 둥글게
            super.paintComponent(g);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(270, 60); // 기본 크기 설정
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