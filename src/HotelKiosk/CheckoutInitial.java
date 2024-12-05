package HotelKiosk;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Flow;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


//호텔 키오스크 체크아웃
public class CheckoutInitial extends JFrame{
    public CheckoutInitial() {
        super("Hotel Kiosk");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 동작 설정
        Container contentPane = getContentPane(); // 프레임에서 컨텐트팬 받아오기
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new NorthPanel(), BorderLayout.NORTH); // 북쪽 패널 추가
        contentPane.add(new CenterPanel(), BorderLayout.CENTER); // 가운데 패널 추가
        contentPane.add(new WestPanel(), BorderLayout.WEST); // 왼쪽 패널 추가
        contentPane.add(new EastPanel(), BorderLayout.EAST); // 왼쪽 패널 추가
        contentPane.add(new SouthPanel(), BorderLayout.SOUTH); // 왼쪽 패널 추가

        setVisible(true); // 프레임을 화면에 표시
    }



    public static void main(String[] args) {
        new CheckoutInitial();
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

    class RoundedButton extends JButton {
        private int arcWidth;
        private int arcHeight;

        public RoundedButton(String text, int arcWidth, int arcHeight) {
            super(text);
            this.arcWidth = arcWidth;
            this.arcHeight = arcHeight;
            setOpaque(false); // 배경을 투명하게 설정
            setBorder(null); // 기본 테두리 제거

        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // 버튼 배경 색상
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
            JLabel textLabel = new JLabel("체크아웃");
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
        public CenterPanel() {
            setBackground(new Color(104, 90, 90));
            setLayout(new BorderLayout());

            // 내부 패널 생성
            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new GridBagLayout());
            innerPanel.setBackground(new Color(104, 90, 90));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // 방번호 레이블
            JLabel numLabel = new JLabel("방번호");
            numLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            numLabel.setForeground(new Color(255, 178, 165));
            gbc.gridx = 0;//첫번 째 열
            gbc.gridy = 0;//첫번 째 행
            gbc.gridwidth = 1;//한 열만 사용

            innerPanel.add(numLabel, gbc);

            // 방번호 둥근 텍스트 필드
            RoundedTextField numField = new RoundedTextField(" 방 번호를 입력해주세요.", 25, 25);
            numField.setBackground(Color.WHITE);
            numField.setForeground(Color.GRAY);
            numField.setPreferredSize(new Dimension(150, 40));
            gbc.gridx = 0;//두번째 열
            gbc.gridy = 1;
            gbc.gridwidth = 1;

            innerPanel.add(numField, gbc);

            // 예약자명 레이블
            JLabel nameLabel = new JLabel("예약자명");
            nameLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            nameLabel.setForeground(new Color(255, 178, 165));
            gbc.gridx = 0;//첫번 째 열
            gbc.gridy = 2;//두번 쨰 행
            gbc.gridwidth = 1;//한 열만 사용

            innerPanel.add(nameLabel, gbc);

            // 둥근 텍스트 필드
            RoundedTextField nameField = new RoundedTextField(" 예약하신 이름을 입력해 주세요.", 25, 25);
            nameField.setBackground(Color.WHITE);
            nameField.setForeground(Color.GRAY);
            nameField.setPreferredSize(new Dimension(150, 40));
            gbc.gridx = 0;//첫번 째 열
            gbc.gridy = 3;//두번 쨰 행
            gbc.gridwidth = 1;//한 열만 사용

            innerPanel.add(nameField, gbc);

            // 수정사항 1. 예약자 연락처 -> 예약번호
            JLabel reservationLabel = new JLabel("예약번호");
            reservationLabel.setFont(new Font("KoPubDotum Bold", Font.BOLD, 15));
            reservationLabel.setForeground(new Color(255, 178, 165));
            gbc.gridy = 5;
            innerPanel.add(reservationLabel, gbc);

            // 둥근 텍스트 필드
            RoundedTextField reservationNumField = new RoundedTextField(" 예약번호를 입력해 주세요.", 25, 25);
            reservationNumField.setBackground(Color.WHITE);
            reservationNumField.setForeground(Color.GRAY);
            reservationNumField.setPreferredSize(new Dimension(300, 40));
            gbc.gridy = 6;
            innerPanel.add(reservationNumField, gbc);

            // 둥근 버튼
            RoundedButton Checkout_btn = new RoundedButton("체크아웃", 20, 20);
            Checkout_btn.setBackground(new Color(255, 178, 165));
            Checkout_btn.setForeground(new Color(74, 69, 66));
            Checkout_btn.setFont(new Font("KoPubDotum Bold", Font.BOLD, 14));
            // 버튼 크기 조정
            Checkout_btn.setPreferredSize(new Dimension(20, 30));
            gbc.insets = new Insets(5, 80, 5, 80); // 위, 왼쪽, 아래, 오른쪽 여백을 설정 (왼쪽과 오른쪽 여백을 줄임)
            gbc.gridy = 8;
            gbc.gridwidth = 2; // 버튼이 두 열을 차지하도록 설정
            innerPanel.add(Checkout_btn, gbc);

            Checkout_btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText().trim();
                    String reservationNumber = reservationNumField.getText().trim();

                    // DatabaseConnection 클래스의 checkReservation 메서드 호출
                    boolean isReservationValid = DatabaseConnection.checkReservation(name, reservationNumber);

                    if (!isReservationValid) {
                        JOptionPane.showMessageDialog(CenterPanel.this, "예약된 정보가 없습니다. 다시 확인해주세요.", "오류", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    // 예약 정보가 유효한 경우 다음 화면으로 이동
                    new CheckoutFin(name, reservationNumber);
                    CheckoutInitial.this.dispose();
                }
            });


            add(innerPanel, BorderLayout.CENTER);
        }

    }

    class WestPanel extends JPanel {
        public WestPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class EastPanel extends JPanel {
        public EastPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(100, 300));
        }
    }

    class SouthPanel extends JPanel {
        public SouthPanel() {
            setBackground(new Color(74, 69, 66));
            add(new JLabel("       "));
            setPreferredSize(new Dimension(700, 150));
        }
    }
}